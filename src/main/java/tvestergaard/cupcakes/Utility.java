package tvestergaard.cupcakes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Utility
{

    /**
     * Formats the provided price in cents to dollars.
     *
     * @param priceInCents
     * @return
     */
    public static String formatPrice(Integer priceInCents)
    {
        int cents   = priceInCents % 100;
        int dollars = (priceInCents - cents) / 100;

        return dollars + "." + (cents < 9 ? "0" + cents : cents);
    }

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
