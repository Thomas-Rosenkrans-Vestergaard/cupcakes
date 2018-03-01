package tvestergaard.cupcakes.servlets.administration;


import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.orders.MysqlOrderDAO;
import tvestergaard.cupcakes.database.orders.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrdersServlet", urlPatterns = "/administration/orders")
public class OrdersServlet extends HttpServlet
{

    private static final String URL = "administration/orders";

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);
        Authentication authentication = new Authentication(request, response, "../");

        if (!authentication.isAdministrator()) {
            authentication.redirect(getRedirectURL((request)));
            return;
        }


        OrderDAO ordersDAO = new MysqlOrderDAO(new PrimaryDatabase());
        request.setAttribute("orders", ordersDAO.get());
        request.getRequestDispatcher("/WEB-INF/administration/orders.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

    private String getRedirectURL(HttpServletRequest request)
    {
        String query = request.getQueryString();
        if (query == null) query = "";
        return query.isEmpty() ? URL : URL + '?' + query;
    }
}