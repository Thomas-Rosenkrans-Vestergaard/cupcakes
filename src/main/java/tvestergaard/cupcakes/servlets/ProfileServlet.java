package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Config;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;

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

	/**
	 * The page to redirect to on errors.
	 */
	private static final String ERROR_REDIRECT = "login";

	/**
	 * The location of the profile.jsp file displayed on GET requests.
	 */
	private static final String PROFILE_JSP = "WEB-INF/profile.jsp";

	/**
	 * Serves the /shop page where users can see the products.
	 *
	 * @param request  The request.
	 * @param response The response.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Notifications notificationHelper = new Notifications(request);
		HttpSession   session            = request.getSession();

		if (session.getAttribute(Config.USER_SESSION_KEY) == null) {
			notificationHelper.error(Language.ERROR_ACCESS_USER);
			response.sendRedirect(ERROR_REDIRECT);
			return;
		}

		request.setAttribute("user", session.getAttribute(Config.USER_SESSION_KEY));
		request.getRequestDispatcher(PROFILE_JSP).forward(request, response);
	}

	/**
	 * Serves the /shop page where users can see the products.
	 *
	 * @param request  The request.
	 * @param response The response.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
