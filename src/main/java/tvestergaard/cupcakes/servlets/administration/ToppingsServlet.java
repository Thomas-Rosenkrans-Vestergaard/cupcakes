package tvestergaard.cupcakes.servlets.administration;


import tvestergaard.cupcakes.*;
import tvestergaard.cupcakes.database.PrimaryDatabase;
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

@WebServlet(name = "ToppingsServlet",
        urlPatterns = "/administration/toppings")
@MultipartConfig
public class ToppingsServlet extends HttpServlet
{

    private static final String URL              = "administration/toppings";
    private static final String ACTION_CREATE    = "create";
    private static final String ACTION_UPDATE    = "update";
    private static final String ACTION_DELETE    = "delete";
    private static final String ACTION_PARAMETER = "action";

    private static final String PARAMETER_ID          = "id";
    private static final String PARAMETER_NAME        = "name";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_PRICE       = "price";
    private static final String PARAMETER_IMAGE       = "image";
    private static final String PAGE                  = "toppings";

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

            ToppingDAO toppingsDAO = new MysqlToppingDAO(new PrimaryDatabase());
            request.setAttribute(PAGE, toppingsDAO.get());
            request.getRequestDispatcher("/WEB-INF/administration/read_toppings.jsp").forward(request, response);
        } catch (SQLException e) {
            notifications.error("A database error occurred.");
            response.sendRedirect("index");
            return;
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response, Notifications notifications) throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/administration/create_topping.jsp").forward(request, response);
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

        ToppingDAO toppingDAO = new MysqlToppingDAO(new PrimaryDatabase());
        request.setAttribute("topping", toppingDAO.get(parameters.getInt(PARAMETER_ID)));
        request.getRequestDispatcher("/WEB-INF/administration/update_topping.jsp").forward(request, response);
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
            || parameters.isEmpty(PARAMETER_PRICE)
            || parameters.notInt(PARAMETER_PRICE)
            || parameters.isNegativeInt(PARAMETER_PRICE)
            || parameters.notPresent(PARAMETER_IMAGE)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, URL));
            return;
        }

        ToppingDAO toppingDAO = new MysqlToppingDAO(new PrimaryDatabase());
        Topping topping = toppingDAO.create(
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                parameters.getInt(PARAMETER_PRICE)
        );

        if (topping == null) {
            notifications.error(RECORD_CREATED_ERROR);
            response.sendRedirect(referer(request, URL));
            return;
        }

        ImageUpload upload = new ImageUpload(request);
        upload.setDestinationDirectory(new File(getServletContext().getRealPath("/images/toppings")));
        upload.saveAs("image", topping.getId() + ".jpg");

        notifications.success(RECORD_CREATED_SUCCESS);
        response.sendRedirect("toppings?action=update&id=" + topping.getId());
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        MultipartParameters parameters = new MultipartParameters(request);

        if (parameters.isEmpty(PARAMETER_ID)
            || parameters.notInt(PARAMETER_ID)
            || parameters.isEmpty(PARAMETER_NAME)
            || parameters.isEmpty(PARAMETER_DESCRIPTION)
            || parameters.isEmpty(PARAMETER_PRICE)
            || parameters.notInt(PARAMETER_PRICE)
            || parameters.isNegativeInt(PARAMETER_PRICE)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, URL));
            return;
        }

        ToppingDAO toppingDAO = new MysqlToppingDAO(new PrimaryDatabase());

        Topping topping = toppingDAO.update(
                parameters.getInt(PARAMETER_ID),
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                parameters.getInt(PARAMETER_PRICE)
        );

        if (topping == null) {
            notifications.error(RECORD_UPDATED_ERROR);
            response.sendRedirect(referer(request, URL));
            return;
        }

        if (parameters.isPresent(PARAMETER_IMAGE)) {
            ImageUpload upload = new ImageUpload(request);
            upload.setDestinationDirectory(new File(getServletContext().getRealPath("/images/toppings")));
            upload.saveAs("image", topping.getId() + ".jpg");
        }

        notifications.success(RECORD_UPDATED_SUCCESS);

        response.sendRedirect("toppings?action=" + ACTION_UPDATE + "&" + PARAMETER_ID + "=" + topping.getId());
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

        ToppingDAO toppingDAO = new MysqlToppingDAO(new PrimaryDatabase());
        boolean    deleted    = toppingDAO.delete(parameters.getInt(PARAMETER_ID));

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