package tvestergaard.cupcakes;

import javax.servlet.http.HttpServletRequest;

public class Parameters
{

    private HttpServletRequest request;

    public Parameters(HttpServletRequest request)
    {
        this.request = request;
    }

    public boolean isNull(String parameter)
    {
        return request.getParameter(parameter) == null;
    }

    public boolean notNull(String parameter)
    {
        return request.getParameter(parameter) != null;
    }

    public boolean isInt(String parameter)
    {
        try {
            Integer.parseInt(request.getParameter(parameter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getInt(String parameter)
    {
        return Integer.parseInt(request.getParameter(parameter));
    }
}
