package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.orders.MysqlOrderDAO;
import tvestergaard.cupcakes.database.orders.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		Notifications  notifications  = new Notifications(request);
		Authentication authentication = new Authentication(request, response);

		if (!authentication.isAuthenticated()) {
			notifications.warning(Language.ERROR_ACCESS_USER);
			authentication.redirect("profile");
			return;
		}

		OrderDAO orderDAO = new MysqlOrderDAO(new PrimaryDatabase());
		request.setAttribute("user", authentication.getUser());
		request.setAttribute("orders", orderDAO.get(authentication.getUser()));

		request.getRequestDispatcher(PROFILE_JSP).forward(request, response);
	}

	/**
	 * Serves the /shop page where users can see the products.
	 *
	 * @param request  The request.
	 * @param response The response.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
																						   IOException
	{
		doGet(request, response);
	}
}
