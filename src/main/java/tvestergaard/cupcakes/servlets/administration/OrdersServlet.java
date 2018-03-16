package tvestergaard.cupcakes.servlets.administration;


import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.Parameters;
import tvestergaard.cupcakes.Utility;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.orders.MysqlOrderDAO;
import tvestergaard.cupcakes.database.orders.Order;
import tvestergaard.cupcakes.database.orders.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "OrdersServlet", urlPatterns = "/administration/orders")
public class OrdersServlet extends HttpServlet
{

    private static final String ACTION_UPDATE = "update";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = new Notifications(request);
        Authentication authentication = new Authentication(request, response, "../");

        if (!authentication.isAdministrator()) {
            response.sendRedirect("../login?from=administration/orders");
            return;
        }

        try {

            String action = request.getParameter("action");

            if (ACTION_UPDATE.equals(action)) {
                showUpdate(request, response, notifications);
                return;
            }

            OrderDAO ordersDAO = new MysqlOrderDAO(new PrimaryDatabase());
            request.setAttribute("orders", ordersDAO.get());
            request.getRequestDispatcher("/WEB-INF/administration/read_orders.jsp").forward(request, response);

        } catch (SQLException e) {
            notifications.error("A database error occurred.");
            response.sendRedirect("index");
            return;
        }
    }

    private void showUpdate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        try {
            int             id       = Integer.parseInt(request.getParameter("id"));
            MysqlDataSource source   = new PrimaryDatabase();
            OrderDAO        orderDAO = new MysqlOrderDAO(source);
            request.setAttribute("order", orderDAO.get(id));
            request.getRequestDispatcher("/WEB-INF/administration/update_order.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            notifications.warning("Bad id parameter.");
            response.sendRedirect("/orders");
            return;
        } catch (Exception e) {
            notifications.error("An error occurred that prevented the page from being displayed.");
            response.sendRedirect("/orders");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = new Notifications(request);
        Authentication authentication = new Authentication(request, response);
        String         action         = request.getParameter("action");

        if (!authentication.isAdministrator()) {
            response.sendRedirect("../login?from=administration/orders");
            return;
        }

        try {

            if (ACTION_UPDATE.equals(action)) {
                handleUpdate(request, response, authentication, notifications);
                return;
            }

        } catch (SQLException e) {

        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Authentication authentication, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isEmpty("id")
            || parameters.notInt("id")
            || parameters.isNull("comment")
            || parameters.isEmpty("comment")
            || parameters.isNull("status")
            || parameters.isEmpty("status")
            || parameters.notInt("status")) {
            notifications.error("Incomplete form data.");
            response.sendRedirect(Utility.referer(request, "orders"));
            return;
        }

        OrderDAO orderDAO = new MysqlOrderDAO(new PrimaryDatabase());
        orderDAO.update(parameters.getInt("id"),
                        authentication.getUser(),
                        parameters.getString("comment"),
                        Order.Status.fromCode(parameters.getInt("status")));
    }
}