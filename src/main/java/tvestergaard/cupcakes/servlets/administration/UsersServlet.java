package tvestergaard.cupcakes.servlets.administration;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.Utility;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.users.MysqlUserDAO;
import tvestergaard.cupcakes.database.users.User;
import tvestergaard.cupcakes.database.users.UserDAO;
import tvestergaard.cupcakes.database.users.UserRequestInputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UsersServlet", urlPatterns = "/administration/users")
public class UsersServlet extends HttpServlet
{

    private static final String UNKNOWN_ACTION_MESSAGE = "The provided action cannot be performed.";
    private static final String REFERER                = "administration/users";
    private static final String ACTION_PARAMETER       = "action";
    private static final String ACTION_NEW             = "new";
    private static final String ACTION_EDIT            = "update";
    private static final String ACTION_DELETE          = "delete";
    private static final String USERS_JSP              = "/WEB-INF/administration/read_users.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request, response, "../");
        Notifications  notifications  = new Notifications(request);

        if (!authentication.isAdministrator()) {
            authentication.redirect(REFERER);
            return;
        }

        String action = request.getParameter(ACTION_PARAMETER);

        if (ACTION_NEW.equals(action)) {
            handleNew(request, response, notifications);
            return;
        }

        if (ACTION_EDIT.equals(action)) {
            handleUpdate(request, response, notifications);
            return;
        }

        if (ACTION_DELETE.equals(action)) {
            handleDelete(request, response, notifications);
            return;
        }

        notifications.warning(UNKNOWN_ACTION_MESSAGE);
        request.getRequestDispatcher(USERS_JSP).forward(request, response);
    }

    private void handleNew(
            HttpServletRequest request, HttpServletResponse response,
            Notifications notifications) throws ServletException, IOException
    {
        try {

            UserDAO                   userDAO   = new MysqlUserDAO(new PrimaryDatabase());
            UserRequestInputValidator validator = new UserRequestInputValidator(request);
            notifications.record();

            validator.username(userDAO, notifications, Language.USER_USERNAME_ERRORS);
            validator.email(userDAO, notifications, Language.USER_EMAIL_ERRORS);
            validator.password(notifications, Language.USER_PASSWORD_ERRORS);
            validator.balance(notifications, Language.USER_BALANCE_ERRORS);
            validator.role(notifications, Language.USER_TYPE_ERRORS);

            if (notifications.hasNew()) {
                response.sendRedirect("users?action=new");
                return;
            }

            User user = userDAO.create(
                    validator.getUsername(),
                    validator.getEmail(),
                    hash(validator.getPassword()),
                    validator.getBalance(),
                    validator.getRole()
            );

            notifications.success(Language.RECORD_CREATED_SUCCESS);
            response.sendRedirect("users?action=update&id=" + user.getId());

        } catch (Exception e) {
            notifications.error(Language.RECORD_CREATED_ERROR);
            response.sendRedirect("users?action=new");
            return;
        }
    }

    private String hash(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void handleUpdate(
            HttpServletRequest request, HttpServletResponse response,
            Notifications notifications) throws ServletException, IOException
    {
        int id = 0;

        if (request.getParameter("id") == null) {
            notifications.warning(Language.MISSING_ID_PARAMETER);
            response.sendRedirect(Utility.referer(request, "administrator/users"));
            return;
        }

        try {

            UserDAO                   userDAO   = new MysqlUserDAO(new PrimaryDatabase());
            UserRequestInputValidator validator = new UserRequestInputValidator(request);
            notifications.record();

            if (validator.usernameWasSent(notifications, Language.USER_USERNAME_ERRORS[0]))
                validator.usernameLength(notifications, Language.USER_USERNAME_ERRORS[1]);
            if (validator.emailWasSent(notifications, Language.USER_EMAIL_ERRORS[0]))
                validator.emailFormat(notifications, Language.USER_EMAIL_ERRORS[1]);
            if (validator.passwordWasSent())
                validator.password(notifications, Language.USER_PASSWORD_ERRORS);
            validator.balance(notifications, Language.USER_BALANCE_ERRORS);
            validator.role(notifications, Language.USER_TYPE_ERRORS);

            if (notifications.hasNew()) {
                request.getRequestDispatcher("/WEB-INF/administration/create_user.jsp").forward(request, response);
                return;
            }

            id = Integer.parseInt(request.getParameter("id"));

            User user = userDAO.update(
                    id,
                    validator.getUsername(),
                    validator.getEmail(),
                    hash(validator.getPassword()),
                    validator.getBalance(),
                    validator.getRole()
            );

            notifications.success(Language.RECORD_UPDATED_SUCCESS);
            response.sendRedirect("users?action=update&id=" + user.getId());

        } catch (NumberFormatException e) {
            notifications.error(Language.MALFORMED_ID_PARAMETER);
            response.sendRedirect("users");
            return;
        } catch (Exception e) {
            notifications.error(Language.RECORD_UPDATED_ERROR);
            response.sendRedirect("users?action=update&id=" + id);
            return;
        }
    }

    private void handleDelete(
            HttpServletRequest request, HttpServletResponse response,
            Notifications notifications) throws ServletException, IOException
    {
        if (request.getParameter("id") == null) {
            notifications.warning(Language.MISSING_ID_PARAMETER);
            response.sendRedirect(Utility.referer(request, "administrator/users"));
            return;
        }

        try {

            int     id      = Integer.parseInt(request.getParameter("id"));
            UserDAO userDAO = new MysqlUserDAO(new PrimaryDatabase());
            boolean result  = userDAO.delete(id);

            if (!result) {
                notifications.error(Language.RECORD_DELETED_ERROR);
                response.sendRedirect(Utility.referer(request, "administrator/users"));
                return;
            }

            notifications.success(Language.RECORD_DELETED_SUCCESS);
            response.sendRedirect("users");

        } catch (NumberFormatException e) {
            notifications.warning(Language.MALFORMED_ID_PARAMETER);
            response.sendRedirect(Utility.referer(request, "administrator/users"));
        } catch (Exception e) {
            notifications.warning(Language.GENERAL_ERROR);
            response.sendRedirect("users");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = new Notifications(request);
        Authentication authentication = new Authentication(request, response, "../");

        if (!authentication.isAdministrator()) {
            authentication.redirect(REFERER);
            return;
        }

        String action = request.getParameter(ACTION_PARAMETER);

        if (ACTION_NEW.equals(action)) {
            showNew(request, response);
            return;
        }

        if (ACTION_EDIT.equals(action)) {
            showUser(request, response, notifications);
            return;
        }

        try {

            UserDAO    userDAO = new MysqlUserDAO(new PrimaryDatabase());
            List<User> users   = userDAO.get();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/administration/read_users.jsp").forward(request, response);

        } catch (Exception e) {

        }
    }

    private void showNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/administration/create_user.jsp").forward(request, response);
    }

    private void showUser(
            HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException
    {
        try {

            int id = Integer.parseInt(request.getParameter("id"));

            UserDAO userDAO = new MysqlUserDAO(new PrimaryDatabase());
            User    user    = userDAO.get(id);

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
}
