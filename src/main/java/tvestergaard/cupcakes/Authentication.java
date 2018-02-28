package tvestergaard.cupcakes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Authentication
{

    private HttpServletRequest request;
    private HttpSession        session;

    public Authentication(HttpServletRequest request)
    {
        this.request = request;
        this.session = request.getSession();
    }

    public boolean isAuthenticated()
    {
        return session.getAttribute(Config.USER_SESSION_KEY) != null;
    }
}
