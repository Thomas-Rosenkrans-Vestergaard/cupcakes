package tvestergaard.cupcakes.servlets;


import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.bottoms.BottomDAO;
import tvestergaard.cupcakes.database.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.database.orders.OrderRequestInputValidator;
import tvestergaard.cupcakes.database.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.database.toppings.Topping;
import tvestergaard.cupcakes.database.toppings.ToppingDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet
{

    /**
     * Serves the /order page where users can see information about and confirm their order.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);

        OrderRequestInputValidator validator = new OrderRequestInputValidator(request);
        notifications.record();
        validator.topping(notifications, Language.ORDER_TOPPING_ERRORS);
        validator.bottom(notifications, Language.ORDER_BOTTOM_ERRORS);

        if (notifications.hasNew()) {
            notifications.warning("Could not view order.");
            response.sendRedirect(request.getHeader("referer"));
            return;
        }

        MysqlDataSource source = new PrimaryDatabase();
        BottomDAO bottomDAO = new MysqlBottomDAO(source);
        Bottom bottom = bottomDAO.get(validator.getBottom());

        if (bottom == null) {
            notifications.error("Provided bottom doesn't exist.");
            response.sendRedirect(request.getHeader("referer"));
            return;
        }

        ToppingDAO toppingDAO = new MysqlToppingDAO(source);
        Topping topping = toppingDAO.get(validator.getTopping());

        if (topping == null) {
            notifications.error("Provided topping doesn't exist.");
            response.sendRedirect(request.getHeader("referer"));
            return;
        }

        request.setAttribute("bottom", bottom);
        request.setAttribute("topping", topping);
        request.getRequestDispatcher("WEB-INF/order.jsp").forward(request, response);
    }

    /**
     * Serves the /preset page where users can see information about the preset with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
