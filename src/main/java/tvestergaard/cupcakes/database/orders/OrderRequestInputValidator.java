package tvestergaard.cupcakes.database.orders;

import tvestergaard.cupcakes.Notification;
import tvestergaard.cupcakes.Notifications;

import javax.servlet.http.HttpServletRequest;

public class OrderRequestInputValidator
{
    private String bottomParameter = "bottom";
    private String toppingParameter = "topping";
    private HttpServletRequest request;
    private Notification.Level errorLevel = Notification.Level.ERROR;

    public OrderRequestInputValidator(HttpServletRequest request)
    {
        this.request = request;
    }

    public boolean bottom()
    {
        return bottomWasSent() && bottomFormat();
    }

    public boolean bottom(Notifications notifications, String[] errors)
    {
        return bottomWasSent(notifications, errors[0]) &&
                bottomFormat(notifications, errors[1]);
    }

    public boolean bottomWasSent()
    {
        String bottom = request.getParameter(bottomParameter);

        return bottom != null && bottom.length() > 0;
    }

    public boolean bottomWasSent(Notifications notifications, String error)
    {
        if (!bottomWasSent()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean bottomFormat()
    {
        String bottom = request.getParameter(bottomParameter);
        if (bottom == null)
            return false;

        try {
            Integer.parseInt(bottom);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean bottomFormat(Notifications notifications, String error)
    {
        if (!bottomFormat()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean topping()
    {
        return toppingWasSent() && toppingFormat();
    }


    public boolean topping(Notifications notifications, String[] errors)
    {
        return toppingWasSent(notifications, errors[0]) &&
                toppingWasSent(notifications, errors[1]);
    }

    public boolean toppingWasSent()
    {
        String topping = request.getParameter(toppingParameter);

        return topping != null && topping.length() > 0;
    }

    public boolean toppingWasSent(Notifications notifications, String error)
    {
        if (!toppingWasSent()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean toppingFormat()
    {
        String topping = request.getParameter(toppingParameter);

        if (topping == null)
            return false;

        try {
            Integer.parseInt(topping);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean toppingFormat(Notifications notifications, String error)
    {
        if (!toppingFormat()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public void setBottomParameter(String bottomParameter)
    {
        this.bottomParameter = bottomParameter;
    }

    public void setToppingParameter(String toppingParameter)
    {
        this.toppingParameter = toppingParameter;
    }

    public void setErrorLevel(Notification.Level errorLevel)
    {
        this.errorLevel = errorLevel;
    }

    public int getBottom()
    {
        return Integer.parseInt(request.getParameter(bottomParameter));
    }

    public int getTopping()
    {
        return Integer.parseInt(request.getParameter(toppingParameter));
    }
}
