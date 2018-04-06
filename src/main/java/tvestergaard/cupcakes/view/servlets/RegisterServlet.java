package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.data.users.User;
import tvestergaard.cupcakes.logic.UserCreationException;
import tvestergaard.cupcakes.logic.UserFacade;
import tvestergaard.cupcakes.view.Language;
import tvestergaard.cupcakes.view.Notifications;
import tvestergaard.cupcakes.view.Parameters;
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
    private final UserFacade userFacade = new UserFacade();

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
        ViewUtilities.attach(request, notifications);
        request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
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
        Notifications notifications = ViewUtilities.getNotifications(request);
        ViewUtilities.attach(request, notifications);
        Parameters parameters = new Parameters(request);

        notifications.record();
        validateParameters(notifications, parameters);

        if (notifications.hasNew()) {
            response.sendRedirect("registration");
            return;
        }

        String username = parameters.getString("username");
        String email    = parameters.getString("email");
        String password = parameters.getString("password");

        try {

            User user = userFacade.create(username, email, password);
            notifications.success(Language.REGISTRATION_SUCCESS);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("profile");

        } catch (UserCreationException e) {
            if (e.has(UserCreationException.Reason.USERNAME_SHORTER_THAN_3))
                notifications.error("Username is too short.");
            if (e.has(UserCreationException.Reason.EMAIL_FORMAT))
                notifications.error("That email is invalid.");
            if (e.has(UserCreationException.Reason.EMAIL_TAKEN))
                notifications.error("That email is already in use.");
            if (e.has(UserCreationException.Reason.USERNAME_TAKEN))
                notifications.error("That username is already in use.");
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }

    private void validateParameters(Notifications notifications, Parameters parameters)
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
    }
}
