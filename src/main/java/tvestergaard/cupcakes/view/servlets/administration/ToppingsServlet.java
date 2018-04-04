package tvestergaard.cupcakes.view.servlets.administration;


import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.data.toppings.Topping;
import tvestergaard.cupcakes.logic.FileSaver;
import tvestergaard.cupcakes.view.Language;
import tvestergaard.cupcakes.view.Notifications;
import tvestergaard.cupcakes.logic.ToppingFacade;
import tvestergaard.cupcakes.view.Authentication;
import tvestergaard.cupcakes.view.MultipartParameters;
import tvestergaard.cupcakes.view.Parameters;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static tvestergaard.cupcakes.view.Language.*;
import static tvestergaard.cupcakes.view.ViewUtilities.referer;

@WebServlet(name = "ToppingsServlet", urlPatterns = "/administration/toppings")
@MultipartConfig
public class ToppingsServlet extends HttpServlet
{

    /**
     * Facade for performing various operations related to toppings.
     */
    private final ToppingFacade toppingFacade = new ToppingFacade(new MysqlToppingDAO(ProductionDatabaseSource.singleton()));

    private static final String ACTION_CREATE    = "create";
    private static final String ACTION_UPDATE    = "update";
    private static final String ACTION_DELETE    = "delete";
    private static final String ACTION_PARAMETER = "action";

    private static final String PARAMETER_ID          = "id";
    private static final String PARAMETER_NAME        = "name";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_PRICE       = "price";
    private static final String PARAMETER_ACTIVE      = "active";
    private static final String PARAMETER_IMAGE       = "image";

    /**
     * Handles GET requests to /administration/bottoms.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAdministrator()) {
            notifications.error(Language.ERROR_ACCESS_ADMINISTRATOR);
            response.sendRedirect("../login?from=administration/toppings");
            return;
        }

        try {

            String action = request.getParameter(ACTION_PARAMETER);
            ViewUtilities.attach(request, notifications);

            if (ACTION_CREATE.equals(action)) {
                showCreateForm(request, response);
                return;
            }

            if (ACTION_UPDATE.equals(action)) {
                showUpdateForm(request, response, notifications);
                return;
            }

            request.setAttribute("toppings", toppingFacade.get());
            request.getRequestDispatcher("/WEB-INF/administration/read_toppings.jsp").forward(request, response);
        } catch (SQLException e) {
            notifications.error("A database error occurred.");
            response.sendRedirect("index");
            return;
        }
    }

    /**
     * Shows the form where administrators can create new toppings.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/administration/create_topping.jsp").forward(request, response);
    }

    /**
     * Shows the form where administrators can update toppings.
     *
     * @param request
     * @param response
     * @param notifications
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.notPresent(PARAMETER_ID) || !parameters.isInt(PARAMETER_ID)) {
            notifications.error(MISSING_ID_PARAMETER);
            response.sendRedirect("toppings");
            return;
        }

        request.setAttribute("topping", toppingFacade.get(parameters.getInt(PARAMETER_ID)));
        request.getRequestDispatcher("/WEB-INF/administration/update_topping.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /administration/toppings.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAdministrator()) {
            notifications.error(Language.ERROR_ACCESS_ADMINISTRATOR);
            response.sendRedirect("../login?from=administration/toppings");
            return;
        }

        try {

            String action = request.getParameter(ACTION_PARAMETER);

            if (ACTION_CREATE.equals(action)) {
                handleCreate(request, response, notifications);
                return;
            }

            if (ACTION_UPDATE.equals(action)) {
                handleUpdate(request, response, notifications);
                return;
            }

            if (ACTION_DELETE.equals(action)) {
                handleDelete(request, response, notifications);
                return;
            }

            notifications.error(UNKNOWN_ACTION_ERROR);
            response.sendRedirect("toppings");

        } catch (SQLException e) {
            notifications.error("A database error occurred.");
            response.sendRedirect("index");
            return;
        }
    }

    /**
     * Handles POST requests from the form where administrators can create new toppings.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleCreate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {

        if (!validateCreateParameters(request)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, "toppings"));
            return;
        }

        MultipartParameters parameters = new MultipartParameters(request);
        Topping topping = toppingFacade.create(
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                parameters.getInt(PARAMETER_PRICE),
                parameters.getBoolean(PARAMETER_ACTIVE));

        if (topping == null) {
            notifications.error(RECORD_CREATED_ERROR);
            response.sendRedirect(referer(request, "toppings"));
            return;
        }

        FileSaver upload = new FileSaver(ViewUtilities.path(request, "/images/toppings"));
        upload.saveAs(request.getPart("image").getInputStream(), topping.getId() + ".jpg");

        notifications.success(RECORD_CREATED_SUCCESS);
        response.sendRedirect("toppings?action=update&id=" + topping.getId());
    }

    /**
     * Handles POST requests from the form where administrators can update toppings.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        MultipartParameters parameters = new MultipartParameters(request);

        if (!validateUpdateParameters(request)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, "toppings"));
            return;
        }

        Topping topping = toppingFacade.update(
                parameters.getInt(PARAMETER_ID),
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                parameters.getInt(PARAMETER_PRICE),
                parameters.getBoolean(PARAMETER_ACTIVE));

        if (topping == null) {
            notifications.error(RECORD_UPDATED_ERROR);
            response.sendRedirect(referer(request, "toppings"));
            return;
        }

        if (parameters.isPresent(PARAMETER_IMAGE)) {
            FileSaver upload = new FileSaver(ViewUtilities.path(request, "/images/toppings"));
            upload.saveAs(request.getPart("image").getInputStream(), topping.getId() + ".jpg");
        }

        notifications.success(RECORD_UPDATED_SUCCESS);

        response.sendRedirect("toppings?action=" + ACTION_UPDATE + "&" + PARAMETER_ID + "=" + topping.getId());
    }

    /**
     * Handles POST requests from the form where administrators can delete toppings.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isEmpty(PARAMETER_ID) || parameters.isNegativeInt(PARAMETER_ID)) {
            notifications.error(MISSING_ID_PARAMETER);
            response.sendRedirect(referer(request, "toppings"));
            return;
        }

        boolean deleted = toppingFacade.delete(parameters.getInt(PARAMETER_ID));

        if (!deleted) {
            notifications.error(RECORD_DELETED_ERROR);
            response.sendRedirect(referer(request, "toppings"));
            return;
        }

        notifications.success(RECORD_DELETED_SUCCESS);
        response.sendRedirect("toppings");
    }

    /**
     * Validates the parameters provided from the form where administrators create new toppings.
     *
     * @param request The request.
     * @return {@code true} if the parameters provided are considered valid.
     */
    private boolean validateCreateParameters(HttpServletRequest request)
    {
        MultipartParameters parameters = new MultipartParameters(request);

        return parameters.notEmpty(PARAMETER_NAME)
                && parameters.notEmpty(PARAMETER_DESCRIPTION)
                && parameters.notEmpty(PARAMETER_PRICE)
                && parameters.isInt(PARAMETER_PRICE)
                && parameters.notNegativeInt(PARAMETER_PRICE)
                && parameters.notEmpty(PARAMETER_ACTIVE)
                && parameters.isPresent(PARAMETER_ACTIVE)
                && parameters.isBoolean(PARAMETER_ACTIVE)
                && parameters.isPresent(PARAMETER_IMAGE);
    }

    /**
     * Validates the parameters provided from the form where administrators update toppings.
     *
     * @param request The request.
     * @return {@code true} if the parameters provided are considered valid.
     */
    private boolean validateUpdateParameters(HttpServletRequest request)
    {
        MultipartParameters parameters = new MultipartParameters(request);

        return parameters.notEmpty(PARAMETER_ID)
                && parameters.isInt(PARAMETER_ID)
                && parameters.notEmpty(PARAMETER_NAME)
                && parameters.notEmpty(PARAMETER_DESCRIPTION)
                && parameters.notEmpty(PARAMETER_PRICE)
                && parameters.isInt(PARAMETER_PRICE)
                && parameters.notNegativeInt(PARAMETER_PRICE)
                && parameters.notEmpty(PARAMETER_ACTIVE)
                && parameters.isPresent(PARAMETER_ACTIVE)
                && parameters.isBoolean(PARAMETER_ACTIVE);
    }
}