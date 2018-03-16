package tvestergaard.cupcakes.servlets;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.cupcakes.Config;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.users.MysqlUserDAO;
import tvestergaard.cupcakes.database.users.User;
import tvestergaard.cupcakes.database.users.UserRequestInputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet
{

    /**
     * Serves the /register page where the user can create a new user.
     *
     * @param request  The request.
     * @param response The response.
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        new Notifications(request);
        request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
    }

    /**
     * Handles the POST from the registration page.
     *
     * @param request  The request.
     * @param response The response.
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);
        MysqlUserDAO userDAO = new MysqlUserDAO(new PrimaryDatabase());
        UserRequestInputValidator validator = new UserRequestInputValidator(request);

        notifications.record();

        validator.username(userDAO, notifications, Language.USER_USERNAME_ERRORS);
        validator.email(userDAO, notifications, Language.USER_EMAIL_ERRORS);
        validator.password(notifications, Language.USER_PASSWORD_ERRORS);

        if (notifications.hasNew()) {
            response.sendRedirect("registration");
            return;
        }

        try {

            User user = userDAO.create(
                    validator.getUsername(),
                    validator.getEmail(),
                    hash(validator.getPassword())
            );

            notifications.success(Language.REGISTRATION_SUCCESS);
            HttpSession session = request.getSession();
            session.setAttribute(Config.USER_SESSION_KEY, user);
            response.sendRedirect("profile");

        } catch (Exception e) {
            notifications.info(Language.GENERAL_ERROR);
            response.sendRedirect("shop");
        }
    }

    /**
     * Hashes the provided password using the b-crypt hashing function.
     *
     * @param password The password to hash.
     * @return The resulting digest.
     */
    private String hash(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
