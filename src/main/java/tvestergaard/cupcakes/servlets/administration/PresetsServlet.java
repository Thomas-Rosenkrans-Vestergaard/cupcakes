package tvestergaard.cupcakes.servlets.administration;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.*;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.bottoms.BottomDAO;
import tvestergaard.cupcakes.database.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.database.presets.MysqlPresetDAO;
import tvestergaard.cupcakes.database.presets.Preset;
import tvestergaard.cupcakes.database.presets.PresetDAO;
import tvestergaard.cupcakes.database.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.database.toppings.Topping;
import tvestergaard.cupcakes.database.toppings.ToppingDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static tvestergaard.cupcakes.Language.*;
import static tvestergaard.cupcakes.Utility.referer;

@WebServlet(name = "PresetsServlet",
        urlPatterns = "/administration/presets")
@MultipartConfig
public class PresetsServlet extends HttpServlet
{

    private static final String URL              = "administration/presets";
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
    private static final String PAGE                  = "presets";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = new Notifications(request);
        Authentication authentication = new Authentication(request, response, "../");

        if (!authentication.isAdministrator()) {
            authentication.redirect(getRedirectURL((request)));
            return;
        }

        try {

            String action = request.getParameter(ACTION_PARAMETER);

            if (ACTION_CREATE.equals(action)) {
                showCreateForm(request, response, notifications);
                return;
            }

            if (ACTION_UPDATE.equals(action)) {
                showUpdateForm(request, response, authentication, notifications);
                return;
            }

            PresetDAO presetsDAO = new MysqlPresetDAO(new PrimaryDatabase());
            request.setAttribute(PAGE, presetsDAO.get());
            request.getRequestDispatcher("/WEB-INF/administration/read_presets.jsp").forward(request, response);
        } catch (SQLException e) {
            notifications.error("A database error occurred.");
            response.sendRedirect("index");
            return;
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        MysqlDataSource source     = new PrimaryDatabase();
        BottomDAO       bottomDAO  = new MysqlBottomDAO(source);
        ToppingDAO      toppingDAO = new MysqlToppingDAO(source);
        request.setAttribute("bottoms", bottomDAO.get());
        request.setAttribute("toppings", toppingDAO.get());
        request.getRequestDispatcher("/WEB-INF/administration/create_preset.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response, Authentication authentication, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isNull(PARAMETER_ID) || !parameters.isInt(PARAMETER_ID)) {
            notifications.error(MISSING_ID_PARAMETER);
            authentication.redirect(referer(request, getRedirectURL(request)));
            return;
        }

        MysqlDataSource source     = new PrimaryDatabase();
        PresetDAO       presetDAO  = new MysqlPresetDAO(source);
        BottomDAO       bottomDAO  = new MysqlBottomDAO(source);
        ToppingDAO      toppingDAO = new MysqlToppingDAO(source);
        request.setAttribute("preset", presetDAO.get(parameters.getInt(PARAMETER_ID)));
        request.setAttribute("bottoms", bottomDAO.get());
        request.setAttribute("toppings", toppingDAO.get());
        request.getRequestDispatcher("/WEB-INF/administration/update_preset.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = new Notifications(request);
        Authentication authentication = new Authentication(request, response, "../");

        if (!authentication.isAdministrator()) {
            authentication.redirect(URL);
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
            response.sendRedirect(URL);
        } catch (SQLException e) {
            notifications.error("A database error occurred.");
            response.sendRedirect("index");
            return;
        }
    }


    private void handleCreate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        MultipartParameters parameters = new MultipartParameters(request);

        if (parameters.isEmpty(PARAMETER_NAME)
            || parameters.isEmpty(PARAMETER_DESCRIPTION)
            || parameters.isEmpty(PARAMETER_BOTTOM)
            || parameters.notInt(PARAMETER_BOTTOM)
            || parameters.isNegativeInt(PARAMETER_BOTTOM)
            || parameters.isEmpty(PARAMETER_TOPPING)
            || parameters.notInt(PARAMETER_TOPPING)
            || parameters.isNegativeInt(PARAMETER_TOPPING)
            || parameters.notPresent(PARAMETER_IMAGE)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, URL));
            return;
        }

        MysqlDataSource source     = new PrimaryDatabase();
        BottomDAO       bottomDAO  = new MysqlBottomDAO(source);
        ToppingDAO      toppingDAO = new MysqlToppingDAO(source);

        Bottom bottom = bottomDAO.get(parameters.getInt(PARAMETER_BOTTOM));

        if (bottom == null) {
            notifications.error("Unknown bottom.");
            response.sendRedirect(referer(request, URL));
            return;
        }

        Topping topping = toppingDAO.get(parameters.getInt(PARAMETER_TOPPING));

        if (bottom == null) {
            notifications.error("Unknown topping.");
            response.sendRedirect(referer(request, URL));
            return;
        }

        PresetDAO presetDAO = new MysqlPresetDAO(new PrimaryDatabase());
        Preset preset = presetDAO.create(
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                bottom,
                topping
        );

        if (preset == null) {
            notifications.error(RECORD_CREATED_ERROR);
            response.sendRedirect(referer(request, URL));
            return;
        }

        ImageUpload upload = new ImageUpload(request);
        upload.setDestinationDirectory(new File(getServletContext().getRealPath("/images/presets")));
        upload.saveAs("image", preset.getId() + ".jpg");

        notifications.success(RECORD_CREATED_SUCCESS);
        response.sendRedirect("presets?action=update&id=" + preset.getId());
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        MultipartParameters parameters = new MultipartParameters(request);

        if (parameters.isEmpty(PARAMETER_ID)
            || parameters.notInt(PARAMETER_ID)
            || parameters.isEmpty(PARAMETER_NAME)
            || parameters.isEmpty(PARAMETER_DESCRIPTION)
            || parameters.isEmpty(PARAMETER_BOTTOM)
            || parameters.notInt(PARAMETER_BOTTOM)
            || parameters.isNegativeInt(PARAMETER_BOTTOM)
            || parameters.isEmpty(PARAMETER_TOPPING)
            || parameters.notInt(PARAMETER_TOPPING)
            || parameters.isNegativeInt(PARAMETER_TOPPING)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, URL));
            return;
        }

        MysqlDataSource source     = new PrimaryDatabase();
        BottomDAO       bottomDAO  = new MysqlBottomDAO(source);
        ToppingDAO      toppingDAO = new MysqlToppingDAO(source);

        Bottom bottom = bottomDAO.get(parameters.getInt(PARAMETER_BOTTOM));

        if (bottom == null) {
            notifications.error("Unknown bottom.");
            response.sendRedirect(referer(request, URL));
            return;
        }

        Topping topping = toppingDAO.get(parameters.getInt(PARAMETER_TOPPING));

        if (bottom == null) {
            notifications.error("Unknown topping.");
            response.sendRedirect(referer(request, URL));
            return;
        }

        PresetDAO presetDAO = new MysqlPresetDAO(new PrimaryDatabase());

        Preset preset = presetDAO.update(
                parameters.getInt(PARAMETER_ID),
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                bottom,
                topping
        );

        if (preset == null) {
            notifications.error(RECORD_UPDATED_ERROR);
            response.sendRedirect(referer(request, URL));
            return;
        }

        if (parameters.isPresent(PARAMETER_IMAGE)) {
            ImageUpload upload = new ImageUpload(request);
            upload.setDestinationDirectory(new File(getServletContext().getRealPath("/images/presets")));
            upload.saveAs("image", preset.getId() + ".jpg");
        }

        notifications.success(RECORD_UPDATED_SUCCESS);
        response.sendRedirect("presets?action=" + ACTION_UPDATE + "&" + PARAMETER_ID + "=" + preset.getId());
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isEmpty(PARAMETER_ID) || parameters.isNegativeInt(PARAMETER_ID)) {
            notifications.error(MISSING_ID_PARAMETER);
            response.sendRedirect(referer(request, URL));
            return;
        }

        PresetDAO presetDAO = new MysqlPresetDAO(new PrimaryDatabase());
        boolean   deleted   = presetDAO.delete(parameters.getInt(PARAMETER_ID));

        if (!deleted) {
            notifications.error(RECORD_DELETED_ERROR);
            response.sendRedirect(referer(request, URL));
            return;
        }

        notifications.success(RECORD_DELETED_SUCCESS);
        response.sendRedirect(PAGE);
    }

    private String getRedirectURL(HttpServletRequest request)
    {
        String query = request.getQueryString();
        if (query == null) query = "";
        return query.isEmpty() ? URL : URL + '?' + query;
    }
}
