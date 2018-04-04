package tvestergaard.cupcakes.view.servlets.administration;

import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.users.MysqlUserDAO;
import tvestergaard.cupcakes.data.users.User;
import tvestergaard.cupcakes.logic.*;
import tvestergaard.cupcakes.view.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UsersServlet", urlPatterns = "/administration/users")
public class UsersServlet extends HttpServlet
{

    /**
     * Facade for performing various operations related to users.
     */
    private final UserFacade userFacade = new UserFacade(new MysqlUserDAO(ProductionDatabaseSource.singleton()));

    private static final String ACTION_CREATE    = "create";
    private static final String ACTION_PARAMETER = "action";
    private static final String ACTION_UPDATE    = "update";
    private static final String ACTION_DELETE    = "delete";

    /**
     * Handles GET requests to /administration/users.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAdministrator()) {
            notifications.error(Language.ERROR_ACCESS_ADMINISTRATOR);
            response.sendRedirect("../login?from=administration/users");
            return;
        }

        String action = request.getParameter(ACTION_PARAMETER);
        ViewUtilities.attach(request, notifications);

        try {

            if (ACTION_CREATE.equals(action)) {
                showCreate(request, response);
                return;
            }

            if (ACTION_UPDATE.equals(action)) {
                showUpdate(request, response, notifications);
                return;
            }

            request.setAttribute("users", userFacade.get());
            request.getRequestDispatcher("/WEB-INF/administration/read_users.jsp").forward(request, response);
        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect("../profile");
            return;
        }
    }

    /**
     * Shows the form where administrators can create new users.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/administration/create_user.jsp").forward(request, response);
    }

    /**
     * Shows the form where administrators can update users.
     *
     * @param request
     * @param response
     * @param notifications
     * @throws ServletException
     * @throws IOException
     */
    private void showUpdate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException
    {
        try {

            int  id   = Integer.parseInt(request.getParameter("id"));
            User user = userFacade.get(id);

            if (user == null) {
                notifications.error("Unknown user.");
                response.sendRedirect("users");
                return;
            }

            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/administration/update_user.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            notifications.error("Malformed parameter id.");
            response.sendRedirect("users");
        }
    }

    /**
     * Handles POST requests to /administration/users.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request);
        Notifications  notifications  = ViewUtilities.getNotifications(request);

        if (!authentication.isAdministrator()) {
            notifications.error(Language.ERROR_ACCESS_ADMINISTRATOR);
            response.sendRedirect("../login?from=administration/users");
            return;
        }

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

        notifications.warning(Language.UNKNOWN_ACTION_ERROR);
        request.getRequestDispatcher("/WEB-INF/administration/read_users.jsp").forward(request, response);
    }

    /**
     * Handles the POST requests from the form where administrators can create new users.
     *
     * @param request
     * @param response
     * @param notifications
     * @throws ServletException
     * @throws IOException
     */
    private void handleCreate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException
    {
        try {
            notifications.record();
            Parameters parameters = new Parameters(request);
            validateCreateParameters(notifications, parameters);
            if (notifications.hasNew()) {
                response.sendRedirect("users?action=new");
                return;
            }

            User user = userFacade.create(
                    parameters.getString("username"),
                    parameters.getString("email"),
                    parameters.getString("password"),
                    parameters.getInt("balance"),
                    User.Role.code(parameters.getInt("role")));

            notifications.success(Language.RECORD_CREATED_SUCCESS);
            response.sendRedirect("users?action=update&id=" + user.getId());

        } catch (UserCreationException e) {
            if (e.has(UserCreationException.Reason.EMAIL_TAKEN))
                notifications.error("That email is already in use.");
            if (e.has(UserCreationException.Reason.USERNAME_TAKEN))
                notifications.error("That username is already in use.");
            response.sendRedirect(ViewUtilities.referer(request, "administration/users"));
        } catch (Exception e) {
            notifications.error(Language.RECORD_CREATED_ERROR);
            response.sendRedirect("users?action=new");
            return;
        }
    }

    public void validateCreateParameters(Notifications notifications, Parameters parameters)
    {
        if (parameters.notPresent("username")) {
            notifications.error("No username provided.");
        }

        if (parameters.notPresent("email")) {
            notifications.error("No email provided.");
        }

        if (parameters.notPresent("password")) {
            notifications.error("No password provided.");
        }

        if (parameters.notPresent("balance") || parameters.notInt("balance")) {
            notifications.error("No balance provided.");
        }

        if (parameters.notPresent("role") || parameters.notInt("role")) {
            notifications.error("No role provided.");
        }
    }

    /**
     * Handles the POST request from the form where administrators update users.
     *
     * @param request
     * @param response
     * @param notifications
     * @throws ServletException
     * @throws IOException
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException
    {
        int id = 0;

        if (request.getParameter("id") == null) {
            notifications.warning(Language.MISSING_ID_PARAMETER);
            response.sendRedirect(ViewUtilities.referer(request, "administrator/users"));
            return;
        }

        try {
            notifications.record();
            Parameters parameters = new Parameters(request);
            validateUpdateParameters(notifications, parameters);
            if (notifications.hasNew()) {
                response.sendRedirect(request.getRequestURL().toString());
                return;
            }

            id = Integer.parseInt(request.getParameter("id"));

            String    username = parameters.getString("username");
            String    email    = parameters.getString("email");
            String    password = parameters.getString("password");
            int       balance  = parameters.getInt("balance");
            User.Role role     = User.Role.code(parameters.getInt("role"));

            if (password.equals(""))
                password = userFacade.get(id).getPassword();

            User user = userFacade.update(id, username, email, password, balance, role);
            notifications.success(Language.RECORD_UPDATED_SUCCESS);
            response.sendRedirect("users?action=update&id=" + user.getId());

        } catch (NumberFormatException e) {
            notifications.error(Language.MALFORMED_ID_PARAMETER);
            response.sendRedirect("users");
        } catch (UserUpdateException e) {
            if (e.has(UserUpdateException.Reason.EMAIL_TAKEN))
                notifications.error("That email is already in use.");
            if (e.has(UserUpdateException.Reason.USERNAME_TAKEN))
                notifications.error("That username is already in use.");
            response.sendRedirect("users?action=update&id=" + id);
        } catch (Exception e) {
            notifications.error(Language.RECORD_UPDATED_ERROR);
            response.sendRedirect("users?action=update&id=" + id);
        }
    }

    public void validateUpdateParameters(Notifications notifications, Parameters parameters)
    {
        if (parameters.notPresent("username")) {
            notifications.error("No username provided.");
        }

        if (parameters.notPresent("email")) {
            notifications.error("No email provided.");
        }

        if (parameters.notPresent("balance") || parameters.notInt("balance")) {
            notifications.error("No balance provided.");
        }

        if (parameters.notPresent("role") || parameters.notInt("role")) {
            notifications.error("No role provided.");
        }
    }

    /**
     * Handles the POST request from the form where administrators can delete users.
     *
     * @param request
     * @param response
     * @param notifications
     * @throws ServletException
     * @throws IOException
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException
    {
        if (request.getParameter("id") == null) {
            notifications.warning(Language.MISSING_ID_PARAMETER);
            response.sendRedirect(ViewUtilities.referer(request, "administrator/users"));
            return;
        }

        try {

            int     id     = Integer.parseInt(request.getParameter("id"));
            boolean result = userFacade.delete(id);

            if (!result) {
                notifications.error(Language.RECORD_DELETED_ERROR);
                response.sendRedirect(ViewUtilities.referer(request, "administrator/users"));
                return;
            }

            notifications.success(Language.RECORD_DELETED_SUCCESS);
            response.sendRedirect("users");

        } catch (NumberFormatException e) {
            notifications.warning(Language.MALFORMED_ID_PARAMETER);
            response.sendRedirect(ViewUtilities.referer(request, "administrator/users"));
        } catch (Exception e) {
            notifications.warning(Language.GENERAL_ERROR);
            response.sendRedirect("users");
        }
    }
}
