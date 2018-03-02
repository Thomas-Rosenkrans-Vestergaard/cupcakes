package tvestergaard.cupcakes;

import tvestergaard.cupcakes.database.users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

public class Authentication
{

	private final String              path;
	private final HttpServletRequest  request;
	private final HttpServletResponse response;
	private final HttpSession         session;

	public Authentication(HttpServletRequest request, HttpServletResponse response)
	{
		this(request, response, "");
	}

	public Authentication(HttpServletRequest request, HttpServletResponse response, String path)
	{
		this.request = request;
		this.session = request.getSession();
		this.response = response;
		this.path = path;
	}

	public void updateUser(User user)
	{
		session.setAttribute(Config.USER_SESSION_KEY, user);
	}

	public void logout()
	{
		session.setAttribute(Config.USER_SESSION_KEY, null);
	}

	/**
	 * Redirects the user to the login page.
	 */
	public void redirect(String referer) throws ServletException, IOException
	{
		response.sendRedirect(path + "login?referer=" + URLEncoder.encode(referer, "UTF-8"));
	}

	public User getUser()
	{
		return (User) session.getAttribute(Config.USER_SESSION_KEY);
	}

	public boolean isAuthenticated()
	{
		return session.getAttribute(Config.USER_SESSION_KEY) != null;
	}

	public boolean is(User.Role role)
	{
		User user = getUser();

		return user != null && user.getRole().code >= role.code;
	}

	public boolean isAdministrator()
	{
		return is(User.Role.ADMINISTRATOR);
	}

	public boolean isUser()
	{
		return is(User.Role.USER);
	}

	public boolean isOwner()
	{
		return is(User.Role.OWNER);
	}
}
