package tvestergaard.cupcakes.view.servlets.administration;

import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.data.presets.MysqlPresetDAO;
import tvestergaard.cupcakes.data.presets.Preset;
import tvestergaard.cupcakes.data.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.data.toppings.Topping;
import tvestergaard.cupcakes.logic.*;
import tvestergaard.cupcakes.view.*;

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

@WebServlet(name = "PresetsServlet", urlPatterns = "/administration/presets")
@MultipartConfig
public class PresetsServlet extends HttpServlet
{

    /**
     * Facade for performing various operations related to presets.
     */
    private final PresetFacade presetFacade = new PresetFacade(new MysqlPresetDAO(ProductionDatabaseSource.singleton()));

    /**
     * Facade for performing various operations related to bottoms.
     */
    private final BottomFacade bottomFacade = new BottomFacade(new MysqlBottomDAO(ProductionDatabaseSource.singleton()));

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
    private static final String PARAMETER_BOTTOM      = "bottom";
    private static final String PARAMETER_TOPPING     = "topping";
    private static final String PARAMETER_IMAGE       = "image";

    /**
     * Handles GET requests to administration/presets.
     *
     * @param request  The {@link HttpServletRequest}.
     * @param response The {@link HttpServletResponse}.
     * @throws ServletException on servlet exceptions.
     * @throws IOException      on io exceptions.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAdministrator()) {
            notifications.error(Language.ERROR_ACCESS_ADMINISTRATOR);
            response.sendRedirect("../login?from=administration/presets");
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

            request.setAttribute("presets", presetFacade.get());
            request.getRequestDispatcher("/WEB-INF/administration/read_presets.jsp").forward(request, response);

        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect("../profile");
        }
    }

    /**
     * Renders the form where administrators can create new presets.
     *
     * @param request  The {@link HttpServletRequest}.
     * @param response The {@link HttpServletResponse}.
     * @throws ServletException on servlet exceptions.
     * @throws IOException      on io exceptions.
     * @throws SQLException     on sql database exceptions.
     */
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException
    {
        request.setAttribute("bottoms", bottomFacade.get());
        request.setAttribute("toppings", toppingFacade.get());
        request.getRequestDispatcher("/WEB-INF/administration/create_preset.jsp").forward(request, response);
    }

    /**
     * Renders the form where administrators can update existing presets.
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
            response.sendRedirect("presets");
            return;
        }

        request.setAttribute("preset", presetFacade.get(parameters.getInt(PARAMETER_ID)));
        request.setAttribute("bottoms", bottomFacade.get());
        request.setAttribute("toppings", toppingFacade.get());
        request.getRequestDispatcher("/WEB-INF/administration/update_preset.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /administration/presets.
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
            response.sendRedirect("../login?from=administration/presets");
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
            response.sendRedirect("presets");
        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect("presets");
        }
    }


    /**
     * Handles POST requests from the form where administrators can create presets.
     *
     * @param request
     * @param response
     * @param notifications
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    private void handleCreate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        MultipartParameters parameters = new MultipartParameters(request);

        if (!validateCreateParameters(request)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        Bottom bottom = bottomFacade.get(parameters.getInt(PARAMETER_BOTTOM));
        if (bottom == null) {
            notifications.error("Unknown bottom.");
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        Topping topping = toppingFacade.get(parameters.getInt(PARAMETER_TOPPING));
        if (bottom == null) {
            notifications.error("Unknown topping.");
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        Preset preset = presetFacade.create(
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                bottom,
                topping);

        if (preset == null) {
            notifications.error(RECORD_CREATED_ERROR);
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        FileSaver upload = new FileSaver(ViewUtilities.path(request, "/images/presets"));
        upload.saveAs(request.getPart("image").getInputStream(), preset.getId() + ".jpg");

        notifications.success(RECORD_CREATED_SUCCESS);
        response.sendRedirect("presets?action=update&id=" + preset.getId());
    }

    /**
     * Handles the POST request from the form where administrators can update presets.
     *
     * @param request
     * @param response
     * @param notifications
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        MultipartParameters parameters = new MultipartParameters(request);

        if (!validateUpdateParameters(request)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        Bottom bottom = bottomFacade.get(parameters.getInt(PARAMETER_BOTTOM));
        if (bottom == null) {
            notifications.error("Unknown bottom.");
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        Topping topping = toppingFacade.get(parameters.getInt(PARAMETER_TOPPING));
        if (bottom == null) {
            notifications.error("Unknown topping.");
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        Preset preset = presetFacade.update(
                parameters.getInt(PARAMETER_ID),
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                bottom,
                topping);

        if (preset == null) {
            notifications.error(RECORD_UPDATED_ERROR);
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        if (parameters.isPresent(PARAMETER_IMAGE)) {
            FileSaver upload = new FileSaver(ViewUtilities.path(request, "/images/presets"));
            upload.saveAs(request.getPart("image").getInputStream(), preset.getId() + ".jpg");
        }

        notifications.success(RECORD_UPDATED_SUCCESS);
        response.sendRedirect("presets?action=" + ACTION_UPDATE + "&" + PARAMETER_ID + "=" + preset.getId());
    }

    /**
     * Handles the POST request from the form where administrators can delete presets.
     *
     * @param request
     * @param response
     * @param notifications
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isEmpty(PARAMETER_ID) || parameters.isNegativeInt(PARAMETER_ID)) {
            notifications.error(MISSING_ID_PARAMETER);
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        boolean deleted = presetFacade.delete(parameters.getInt(PARAMETER_ID));

        if (!deleted) {
            notifications.error(RECORD_DELETED_ERROR);
            response.sendRedirect(referer(request, "presets"));
            return;
        }

        notifications.success(RECORD_DELETED_SUCCESS);
        response.sendRedirect("presets");
    }

    /**
     * Validates the parameters provided from the form where administrators create new presets.
     *
     * @param request The request.
     * @return {@code true} if the parameters provided are considered valid.
     */
    private boolean validateCreateParameters(HttpServletRequest request)
    {
        MultipartParameters parameters = new MultipartParameters(request);

        return parameters.notEmpty(PARAMETER_NAME)
                && parameters.notEmpty(PARAMETER_DESCRIPTION)
                && parameters.notEmpty(PARAMETER_BOTTOM)
                && parameters.isInt(PARAMETER_BOTTOM)
                && parameters.notNegativeInt(PARAMETER_BOTTOM)
                && parameters.notEmpty(PARAMETER_TOPPING)
                && parameters.isInt(PARAMETER_TOPPING)
                && parameters.notNegativeInt(PARAMETER_TOPPING)
                && parameters.isPresent(PARAMETER_IMAGE);
    }

    /**
     * Validates the parameters provided from the form where administrators update presets.
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
                && parameters.notEmpty(PARAMETER_BOTTOM)
                && parameters.isInt(PARAMETER_BOTTOM)
                && parameters.notNegativeInt(PARAMETER_BOTTOM)
                && parameters.notEmpty(PARAMETER_TOPPING)
                && parameters.isInt(PARAMETER_TOPPING)
                && parameters.notNegativeInt(PARAMETER_TOPPING)
                && parameters.isPresent(PARAMETER_IMAGE);
    }
}
