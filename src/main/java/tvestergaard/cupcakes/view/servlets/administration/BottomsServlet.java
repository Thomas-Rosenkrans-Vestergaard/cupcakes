package tvestergaard.cupcakes.view.servlets.administration;


import tvestergaard.cupcakes.view.Authentication;
import tvestergaard.cupcakes.FileSaver;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.bottoms.BottomDAO;
import tvestergaard.cupcakes.database.bottoms.MysqlBottomDAO;
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

import static tvestergaard.cupcakes.Language.*;
import static tvestergaard.cupcakes.view.ViewUtilities.referer;

@WebServlet(name = "BottomsServlet", urlPatterns = "/administration/bottoms")
@MultipartConfig
public class BottomsServlet extends HttpServlet
{

    private final BottomDAO bottomDAO = new MysqlBottomDAO(new PrimaryDatabase());

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
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAdministrator()) {
            notifications.error(Language.ERROR_ACCESS_ADMINISTRATOR);
            response.sendRedirect("../login?from=administration/bottoms");
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

            request.setAttribute("bottoms", bottomDAO.get());
            request.getRequestDispatcher("/WEB-INF/administration/read_bottoms.jsp").forward(request, response);
        } catch (SQLException e) {
            notifications.error("A database error occurred.");
            response.sendRedirect("index");
            return;
        }
    }

    /**
     * Shows a form where administrators can create new bottoms.
     */
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/administration/create_bottom.jsp").forward(request, response);
    }

    /**
     * Shows the form where administrators can update bottoms.
     */
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.notPresent(PARAMETER_ID) || !parameters.isInt(PARAMETER_ID)) {
            notifications.error(MISSING_ID_PARAMETER);
            response.sendRedirect("bottoms");
            return;
        }

        request.setAttribute("bottom", bottomDAO.get(parameters.getInt(PARAMETER_ID)));
        ViewUtilities.attach(request, notifications);
        request.getRequestDispatcher("/WEB-INF/administration/update_bottom.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to administration/bottoms.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAdministrator()) {
            notifications.error(Language.ERROR_ACCESS_ADMINISTRATOR);
            response.sendRedirect("../login?from=administration/bottoms");
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
            response.sendRedirect("bottoms");
        } catch (SQLException e) {
            notifications.error("A database error occurred.");
            response.sendRedirect("index");
            return;
        }
    }

    /**
     * Handles POST requests from the form where administrators create new bottoms.
     */
    private void handleCreate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        MultipartParameters parameters = new MultipartParameters(request);

        if (!validateCreateParameters(request)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, "bottoms"));
            return;
        }

        Bottom bottom = bottomDAO.create(
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                parameters.getInt(PARAMETER_PRICE),
                parameters.getBoolean(PARAMETER_ACTIVE));

        if (bottom == null) {
            notifications.error(RECORD_CREATED_ERROR);
            response.sendRedirect(referer(request, "bottoms"));
            return;
        }

        FileSaver upload = new FileSaver(ViewUtilities.path(request, "/images/bottoms"));
        upload.saveAs(request.getPart("image").getInputStream(), bottom.getId() + ".jpg");

        notifications.success(RECORD_CREATED_SUCCESS);
        response.sendRedirect("bottoms?action=update&id=" + bottom.getId());
    }

    /**
     * Handles POST requests from the form where administrators update bottoms.
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        MultipartParameters parameters = new MultipartParameters(request);

        if (!validateUpdateParameters(request)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, "bottoms"));
            return;
        }

        Bottom bottom = bottomDAO.update(
                parameters.getInt(PARAMETER_ID),
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                parameters.getInt(PARAMETER_PRICE),
                parameters.getBoolean(PARAMETER_ACTIVE));

        if (bottom == null) {
            notifications.error(RECORD_UPDATED_ERROR);
            response.sendRedirect(referer(request, "bottoms"));
            return;
        }

        if (parameters.isPresent(PARAMETER_IMAGE)) {
            FileSaver upload = new FileSaver(ViewUtilities.path(request, "/images/bottoms"));
            upload.saveAs(request.getPart("image").getInputStream(), bottom.getId() + ".jpg");
        }

        notifications.success(RECORD_UPDATED_SUCCESS);
        response.sendRedirect("bottoms?action=" + ACTION_UPDATE + "&" + PARAMETER_ID + "=" + bottom.getId());
    }

    /**
     * Handles POST requests from the form where administrators delete bottoms.
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isEmpty(PARAMETER_ID) || parameters.isNegativeInt(PARAMETER_ID)) {
            notifications.error(MISSING_ID_PARAMETER);
            response.sendRedirect(referer(request, "bottoms"));
            return;
        }

        boolean deleted = bottomDAO.delete(parameters.getInt(PARAMETER_ID));

        if (!deleted) {
            notifications.error(RECORD_DELETED_ERROR);
            response.sendRedirect(referer(request, "bottoms"));
            return;
        }

        notifications.success(RECORD_DELETED_SUCCESS);
        response.sendRedirect("bottoms");
    }

    /**
     * Validates the parameters provided from the form where administrators create new bottoms.
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
                && parameters.isBoolean(PARAMETER_ACTIVE)
                && parameters.isPresent(PARAMETER_IMAGE);
    }

    /**
     * Validates the parameters provided from the form where administrators update bottoms.
     *
     * @param request The request.
     * @return {@code true} if the parameters provided are considered valid.
     */
    private boolean validateUpdateParameters(HttpServletRequest request)
    {
        MultipartParameters parameters = new MultipartParameters(request);

        return parameters.isPresent(PARAMETER_ID)
                && parameters.isInt(PARAMETER_ID)
                && parameters.notEmpty(PARAMETER_NAME)
                && parameters.notEmpty(PARAMETER_DESCRIPTION)
                && parameters.notEmpty(PARAMETER_PRICE)
                && parameters.isInt(PARAMETER_PRICE)
                && parameters.notNegativeInt(PARAMETER_PRICE)
                && parameters.notEmpty(PARAMETER_ACTIVE)
                && parameters.isBoolean(PARAMETER_ACTIVE);
    }
}
