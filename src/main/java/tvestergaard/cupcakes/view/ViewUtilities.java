package tvestergaard.cupcakes.view;

import tvestergaard.cupcakes.logic.Notifications;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

public class ViewUtilities
{

    /**
     * Attaches the provided {@link Notifications} to the attributes of the provided request using the key 'notifications'.
     *
     * @param request       The request to attach the provided {@link Notifications} to.
     * @param notifications The {@link Notifications} to attach.
     */
    public static void attach(HttpServletRequest request, Notifications notifications)
    {
        request.setAttribute("notifications", notifications);
    }

    /**
     * Returns the instance of {@link Notifications} of the user sending the provided request. Returns a new instance of
     * {@link Notifications} if the user has no {@link Notifications} instance in their session.
     *
     * @param request The request from which to return the {@link Notifications}.
     * @return The reused or new instance of {@link Notifications}.
     */
    public static Notifications getNotifications(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object      o       = session.getAttribute("notifications");

        if (o == null) {
            Notifications notifications = new Notifications();
            session.setAttribute("notifications", notifications);
            return notifications;
        }

        return (Notifications) o;
    }

    /**
     * Formats the provided price in cents to dollars using a period as the decimal separator.
     *
     * @param priceInCents The price in cents.
     * @return The price in dollars.
     */
    public static String formatPrice(Integer priceInCents)
    {
        int cents   = priceInCents % 100;
        int dollars = (priceInCents - cents) / 100;

        if (cents == 0)
            return Integer.toString(dollars);

        return dollars + "." + (cents < 9 ? "0" + cents : cents);
    }

    /**
     * Returns the referer location of the provided request. If the provided request has no referer, the parameter
     * {@code or} provided to the method is returned instead.
     *
     * @param request The request to find the referer from.
     * @param or      The default referer value.
     * @return The referer of the provided request, or the default value.
     */
    public static String referer(HttpServletRequest request, String or)
    {
        String referer = request.getHeader("referer");

        if (referer == null || referer.isEmpty())
            return or;

        return referer;
    }

    public static File path(HttpServletRequest request, String path)
    {
        return new File(request.getServletContext().getRealPath(path));
    }
}
