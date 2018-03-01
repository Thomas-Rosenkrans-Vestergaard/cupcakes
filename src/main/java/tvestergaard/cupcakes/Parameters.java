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

    public boolean isEmpty(String parameter)
    {
        String value = request.getParameter(parameter);

        return value == null || value.length() < 1;
    }

    public boolean notEmpty(String parameter)
    {
        return !isEmpty(parameter);
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

    public boolean notInt(String parameter)
    {
        return !isInt(parameter);
    }

    public int getInt(String parameter)
    {
        return Integer.parseInt(request.getParameter(parameter));
    }

    public String getString(String parameter)
    {
        String value = request.getParameter(parameter);

        return value == null ? "" : value;
    }

    public boolean isPositiveInt(String parameter)
    {
        return isInt(parameter) && getInt(parameter) > 0;
    }

    public boolean notPositiveInt(String parameter)
    {
        return !isPositiveInt(parameter);
    }

    public boolean isNegativeInt(String parameter)
    {
        return isInt(parameter) && getInt(parameter) < 0;
    }

    public boolean notNegativeInt(String parameter)
    {
        return isInt(parameter) && getInt(parameter) > -1;
    }
}
