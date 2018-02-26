package tvestergaard.cupcakes.servlets;

import org.apache.commons.validator.routines.EmailValidator;
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

@WebServlet(name = "RegisterServlet",
			urlPatterns = "/register")
public class RegisterServlet extends HttpServlet
{

	private static final String ON_ERROR              = "register";
	private static final String ON_SUCCESS            = "shop";
	private static final String USERNAME_FIELD        = "username";
	private static final String EMAIL_FIELD           = "email";
	private static final String PASSWORD_FIELD        = "password";
	private static final String PASSWORD_REPEAT_FIELD = "password-repeat";
	private static final String SESSION_KEY           = "user";

	/**
	 * Handles the POST from the registration page.
	 *
	 * @param request  The request.
	 * @param response The response.
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
																						   IOException
	{
		NotificationHelper notifications = new NotificationHelper(request);
		MysqlUserDAO       userDAO       = new MysqlUserDAO(new CupcakeMysqlDataSource());

		notifications.record();
		validate(notifications, request, userDAO);
		if (notifications.hasNew()) {
			response.sendRedirect(ON_ERROR);
			return;
		}

		try {

			User user = userDAO.create(
					request.getParameter(USERNAME_FIELD),
					request.getParameter(EMAIL_FIELD),
					BCrypt.hashpw(request.getParameter(PASSWORD_FIELD), BCrypt.gensalt())
			);

			HttpSession session = request.getSession();
			session.setAttribute(SESSION_KEY, user);
			response.sendRedirect(ON_SUCCESS);

		} catch (Exception e) {
			notifications.notify("An error occurred.");
			response.sendRedirect(ON_ERROR);
			return;
		}
	}

	/**
	 * Validates the parameters to the provided request.
	 *
	 * @param notifications The {@link NotificationHelper}.
	 * @param request       The request holding the parameters to validate.
	 * @param userDAO       The {@link UserDAO} to use to access the database.
	 */
	private void validate(NotificationHelper notifications, HttpServletRequest request, UserDAO userDAO)
	{
		String email          = request.getParameter(EMAIL_FIELD);
		String password       = request.getParameter(PASSWORD_FIELD);
		String passwordRepeat = request.getParameter(PASSWORD_REPEAT_FIELD);
		String username       = request.getParameter(USERNAME_FIELD);

		if (email == null || password == null || passwordRepeat == null || username == null) {
			notifications.notify("Missing parameter.");
			return;
		}

		// Validate email
		if (!EmailValidator.getInstance().isValid(email))
			notifications.notify("Incorrectly formatted email address.");
		else if (userDAO.findFromEmail(email) != null)
			notifications.notify("Email is already taken.");

		// Validate password
		if (password.length() < 4)
			notifications.notify("Passwords must be 4 characters or longer.");
		if (!password.equals(passwordRepeat))
			notifications.notify("Password must match password repeat.");

		// Validate username
		if (username.length() < 1)
			notifications.notify("Username cannot be empty.");
		else if (userDAO.findFromUsername(username) != null)
			notifications.notify("Username already taken.");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		new NotificationHelper(request);
		request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
	}
}
