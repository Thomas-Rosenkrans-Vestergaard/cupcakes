package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.NotificationHelper;
import tvestergaard.cupcakes.database.users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet",
			urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet
{

	private static final String SUCCESS_NOTIFICATION = "You were successfully logged out, have a nice day.";
	private static final String ERROR_NOTIFICATION   = "You could not be logged out.";
	private static final String SESSION_KEY          = "user";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logout(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logout(request, response);
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession        session       = request.getSession();
		User               user          = (User) session.getAttribute(SESSION_KEY);
		NotificationHelper notifications = new NotificationHelper(request);
		String             previous      = request.getHeader("referer");

		if (user == null) {
			notifications.notify(ERROR_NOTIFICATION);
			response.sendRedirect(previous);
			return;
		}

		session.setAttribute(SESSION_KEY, null);
		notifications.notify(SUCCESS_NOTIFICATION);
		response.sendRedirect(previous);
	}
}
