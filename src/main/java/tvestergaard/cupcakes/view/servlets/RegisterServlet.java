package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.users.MysqlUserDAO;
import tvestergaard.cupcakes.data.users.User;
import tvestergaard.cupcakes.logic.Language;
import tvestergaard.cupcakes.logic.Notifications;
import tvestergaard.cupcakes.logic.UserFacade;
import tvestergaard.cupcakes.view.UserRequestInputValidator;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Serves the /register page the users can create a new user account. Handles the form data sent from the registration
 * form.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet
{

    /**
     * Facade for performing various operations related to users.
     */
    private final UserFacade userFacade = new UserFacade(new MysqlUserDAO(ProductionDatabaseSource.singleton()));

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
        Notifications notifications = ViewUtilities.getNotifications(request);

        try {
            ViewUtilities.attach(request, notifications);
            request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }

    /**
     * Handles the form data POST'ed from the /registration page.
     *
     * @param request  The request.
     * @param response The response.
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications             notifications = ViewUtilities.getNotifications(request);
        UserRequestInputValidator validator     = new UserRequestInputValidator(request);

        notifications.record();

        validator.username(userFacade, notifications, Language.USER_USERNAME_ERRORS);
        validator.email(userFacade, notifications, Language.USER_EMAIL_ERRORS);
        validator.password(notifications, Language.USER_PASSWORD_ERRORS);

        if (notifications.hasNew()) {
            response.sendRedirect("registration");
            return;
        }

        try {

            User user = userFacade.create(validator.getUsername(), validator.getEmail(), validator.getPassword());
            notifications.success(Language.REGISTRATION_SUCCESS);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("profile");

        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }
}
