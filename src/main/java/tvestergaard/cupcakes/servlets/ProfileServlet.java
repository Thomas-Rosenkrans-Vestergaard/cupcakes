package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.NotificationHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ProfileServlet",
			urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet
{

	private static final String SESSION_KEY        = "user";
	private static final String ERROR_NOTIFICATION = "You must be logged in to access this page.";
	private static final String ERROR_REDIRECT     = "login";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		NotificationHelper notificationHelper = new NotificationHelper(request);
		HttpSession        session            = request.getSession();

		if (session.getAttribute(SESSION_KEY) == null) {
			notificationHelper.notify(ERROR_NOTIFICATION);
			response.sendRedirect(ERROR_REDIRECT);
			return;
		}

		new NotificationHelper(request);
		request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
	}
}
