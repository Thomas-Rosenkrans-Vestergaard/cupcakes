package tvestergaard.cupcakes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Function
{

    public static void redirect(HttpServletResponse response, String location) throws IOException
    {
        response.sendRedirect(location);
    }

    public static String referer(HttpServletRequest request, String or)
    {
        String referer = request.getParameter("referer");

        if (referer == null || referer.isEmpty())
            return or;

        return referer;
    }
}
