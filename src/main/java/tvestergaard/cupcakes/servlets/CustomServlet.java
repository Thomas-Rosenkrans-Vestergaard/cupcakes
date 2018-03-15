package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.Parameters;
import tvestergaard.cupcakes.Utility;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.database.toppings.MysqlToppingDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CustomServlet", urlPatterns = "/custom")
public class CustomServlet extends HttpServlet
{

    /**
     * Serves the /custom page where users can create their own cupcake. The url parameters 'bottom' and 'topping' can
     * be use to fill the selection.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        Notifications notifications = new Notifications(request);

        try {
            PrimaryDatabase source     = new PrimaryDatabase();
            MysqlBottomDAO  bottomDAO  = new MysqlBottomDAO(source);
            MysqlToppingDAO toppingDAO = new MysqlToppingDAO(source);

            request.setAttribute("bottoms", bottomDAO.get());
            request.setAttribute("toppings", toppingDAO.get());

            Parameters parameters = new Parameters(request);
            if (parameters.isInt("bottom"))
                request.setAttribute("selectedBottom", parameters.getInt("bottom"));
            if (parameters.isInt("topping"))
                request.setAttribute("selectedTopping", parameters.getInt("topping"));

            request.getRequestDispatcher("WEB-INF/custom.jsp").forward(request, response);

        } catch (Exception e) {
            notifications.error("An error occurred that prevented the requested page from being rendered.");
            response.sendRedirect(Utility.referer(request, "shop"));
            return;
        }
    }

    /**
     * Serves the /custom page where users can create their own cupcake. The url parameters 'bottom' and 'topping' can
     * be use to fill the selection.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException
    {
        doGet(request, response);
    }
}
