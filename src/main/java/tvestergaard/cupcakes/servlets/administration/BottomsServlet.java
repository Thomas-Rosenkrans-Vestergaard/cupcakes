package tvestergaard.cupcakes.servlets.administration;


import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.Parameters;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.bottoms.BottomDAO;
import tvestergaard.cupcakes.database.bottoms.MysqlBottomDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.cupcakes.Function.referer;
import static tvestergaard.cupcakes.Language.*;

@WebServlet(name = "BottomsServlet", urlPatterns = "/administration/bottoms")
public class BottomsServlet extends HttpServlet
{

    private static final String URL              = "administration/bottoms";
    private static final String ACTION_CREATE    = "create";
    private static final String ACTION_UPDATE    = "update";
    private static final String ACTION_DELETE    = "delete";
    private static final String ACTION_PARAMETER = "action";

    private static final String PARAMETER_ID          = "id";
    private static final String PARAMETER_NAME        = "name";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_PRICE       = "price";
    private static final String PAGE                  = "bottoms";

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);
        Authentication authentication = new Authentication(request, response, "../");

        if (!authentication.isAdministrator()) {
            authentication.redirect(getRedirectURL((request)));
            return;
        }

        String action = request.getParameter(ACTION_PARAMETER);

        if (ACTION_CREATE.equals(action)) {
            showCreateForm(request, response, notifications);
            return;
        }

        if (ACTION_UPDATE.equals(action)) {
            showUpdateForm(request, response, authentication, notifications);
            return;
        }

        BottomDAO bottomsDAO = new MysqlBottomDAO(new PrimaryDatabase());
        request.setAttribute(PAGE, bottomsDAO.get());
        request.getRequestDispatcher("/WEB-INF/administration/bottoms.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response, Notifications notifications) throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/administration/create_bottom.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response, Authentication authentication, Notifications notifications) throws ServletException, IOException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isNull(PARAMETER_ID) || !parameters.isInt(PARAMETER_ID)) {
            notifications.error(MISSING_ID_PARAMETER);
            authentication.redirect(referer(request, getRedirectURL(request)));
            return;
        }

        BottomDAO bottomDAO = new MysqlBottomDAO(new PrimaryDatabase());
        request.setAttribute("bottom", bottomDAO.get(parameters.getInt(PARAMETER_ID)));
        request.getRequestDispatcher("/WEB-INF/administration/update_bottom.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);
        Authentication authentication = new Authentication(request, response, "../");

        if (!authentication.isAdministrator()) {
            authentication.redirect(URL);
            return;
        }

        String action = request.getParameter(ACTION_PARAMETER);

        if (ACTION_CREATE.equals(action)) {
            handleCreate(request, response, notifications);
            return;
        }

        if (ACTION_UPDATE.equals(action)) {
            handleUpdate(request, response, authentication, notifications);
            return;
        }

        if (ACTION_DELETE.equals(action)) {
            handleDelete(request, response, authentication, notifications);
            return;
        }

        notifications.error(UNKNOWN_ACTION_ERROR);
        response.sendRedirect(URL);
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response, Notifications notifications) throws ServletException, IOException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isEmpty(PARAMETER_NAME)
                || parameters.isEmpty(PARAMETER_DESCRIPTION)
                || parameters.isEmpty(PARAMETER_PRICE)
                || parameters.notInt(PARAMETER_PRICE)
                || parameters.isNegativeInt(PARAMETER_PRICE)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, URL));
            return;
        }

        BottomDAO bottomDAO = new MysqlBottomDAO(new PrimaryDatabase());
        Bottom bottom = bottomDAO.create(
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                parameters.getInt(PARAMETER_PRICE));

        if (bottom == null) {
            notifications.error(RECORD_CREATED_ERROR);
            response.sendRedirect(referer(request, URL));
            return;
        }

        notifications.success(RECORD_CREATED_SUCCESS);
        response.sendRedirect("bottoms?action=update&id=" + bottom.getId());
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Authentication authentication, Notifications notifications) throws ServletException, IOException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isEmpty(PARAMETER_NAME)
                || parameters.isEmpty(PARAMETER_DESCRIPTION)
                || parameters.isEmpty(PARAMETER_PRICE)
                || parameters.notInt(PARAMETER_PRICE)
                || parameters.isNegativeInt(PARAMETER_PRICE)) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(referer(request, URL));
            return;
        }

        BottomDAO bottomDAO = new MysqlBottomDAO(new PrimaryDatabase());

        Bottom bottom = bottomDAO.update(
                parameters.getInt(PARAMETER_ID),
                parameters.getString(PARAMETER_NAME),
                parameters.getString(PARAMETER_DESCRIPTION),
                parameters.getInt(PARAMETER_PRICE)
        );

        if (bottom == null) {
            notifications.error(RECORD_UPDATED_ERROR);
            response.sendRedirect(referer(request, URL));
            return;
        }

        notifications.success(RECORD_UPDATED_SUCCESS);
        response.sendRedirect("bottoms?action=" + ACTION_UPDATE + "&" + PARAMETER_ID + "=" + bottom.getId());
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response, Authentication authentication, Notifications notifications) throws ServletException, IOException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isEmpty(PARAMETER_ID) || parameters.isNegativeInt(PARAMETER_ID)) {
            notifications.error(MISSING_ID_PARAMETER);
            response.sendRedirect(referer(request, URL));
            return;
        }

        BottomDAO bottomDAO = new MysqlBottomDAO(new PrimaryDatabase());
        boolean deleted = bottomDAO.delete(parameters.getInt(PARAMETER_ID));

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
