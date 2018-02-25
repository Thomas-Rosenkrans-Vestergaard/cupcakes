package tvestergaard.cupcakes.servlets;

import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.cupcakes.database.CupcakeMysqlDataSource;
import tvestergaard.cupcakes.database.users.MysqlUserDAO;
import tvestergaard.cupcakes.database.users.User;
import tvestergaard.cupcakes.database.users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RegistrationServlet",
			urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
																						   IOException
	{
		List<String> errors = validate(request);

		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
			return;
		}

		try {

			UserDAO userDAO = new MysqlUserDAO(new CupcakeMysqlDataSource());
			User user = userDAO.create(
					request.getParameter("username"),
					request.getParameter("email"),
					BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt())
			);

		} catch (IllegalStateException e) {
			errors.add(e.getMessage());
		}


	}

	private List<String> validate(HttpServletRequest request)
	{
		List<String> errors = new ArrayList<>();

		String email          = request.getParameter("email");
		String password       = request.getParameter("password");
		String passwordRepeat = request.getParameter("password-repeat");
		String username       = request.getParameter("username");

		if (!EmailValidator.getInstance().isValid(email))
			errors.add("Incorrectly formatted email address.");

		if (password.length() < 8)
			errors.add("Passwords must be 8 characters or longer.");

		if (!password.equals(passwordRepeat))
			errors.add("Password must match password repeat.");

		if (username.length() < 1)
			errors.add("Username cannot be empty.");

		return errors;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
	}
}
