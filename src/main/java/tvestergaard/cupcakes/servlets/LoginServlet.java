package tvestergaard.cupcakes.servlets;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.cupcakes.NotificationHelper;
import tvestergaard.cupcakes.database.CupcakeMysqlDataSource;
import tvestergaard.cupcakes.database.users.MysqlUserDAO;
import tvestergaard.cupcakes.database.users.User;
import tvestergaard.cupcakes.database.users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet",
			urlPatterns = "/login")
public class LoginServlet extends HttpServlet
{

	private static String SESSION_KEY    = "user";
	private static String ON_SUCCESS     = "profile";
	private static String ON_ERROR       = "login";
	private static String USERNAME_FIELD = "username";
	private static String PASSWORD_FIELD = "password";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		NotificationHelper notifications = new NotificationHelper(request);
		notifications.record();
		validate(notifications, request);
		if (notifications.hasNew()) {
			response.sendRedirect(ON_ERROR);
			return;
		}

		try {
			UserDAO userDAO = new MysqlUserDAO(new CupcakeMysqlDataSource());
			User    user    = userDAO.findFromUsername(request.getParameter(USERNAME_FIELD));

			if (user == null) {
				notifications.notify("Incorrect username or password.");
				response.sendRedirect(ON_ERROR);
				return;
			}

			if (!BCrypt.checkpw(request.getParameter(PASSWORD_FIELD), user.getPassword())) {
				notifications.notify("Incorrect username or password.");
				response.sendRedirect(ON_ERROR);
				return;
			}

			HttpSession session = request.getSession();
			session.setAttribute(SESSION_KEY, user);
			response.sendRedirect(ON_SUCCESS);

		} catch (Exception e) {
			notifications.notify("An error occurred.");
			response.sendRedirect(ON_ERROR);
			return;
		}
	}

	private void validate(NotificationHelper notifications, HttpServletRequest request)
	{
		if (request.getParameter(USERNAME_FIELD) == null)
			notifications.notify("Missing username field.");

		if (request.getParameter(PASSWORD_FIELD) == null)
			notifications.notify("Missing password field");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		new NotificationHelper(request);
		request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
	}
}
