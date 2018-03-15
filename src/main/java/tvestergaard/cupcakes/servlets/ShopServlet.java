package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.Utility;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.database.presets.MysqlPresetDAO;
import tvestergaard.cupcakes.database.toppings.MysqlToppingDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ShopServlet", urlPatterns = "/shop")
public class ShopServlet extends HttpServlet
{

    /**
     * Serves the /shop page where users can see the products of the shop.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);

        try {

            PrimaryDatabase source     = new PrimaryDatabase();
            MysqlPresetDAO  presetsDAO = new MysqlPresetDAO(source);
            MysqlBottomDAO  bottomDAO  = new MysqlBottomDAO(source);
            MysqlToppingDAO toppingDAO = new MysqlToppingDAO(source);

            request.setAttribute("presets", presetsDAO.get());
            request.setAttribute("bottoms", bottomDAO.get());
            request.setAttribute("toppings", toppingDAO.get());

            request.getRequestDispatcher("WEB-INF/shop.jsp").forward(request, response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
//            notifications.error("An error occurred that prevented the requested page from being rendered.");
//            response.sendRedirect(Utility.referer(request, "shop"));
//            return;
        }
    }

    /**
     * Serves the /shop page where users can see the products of the shop.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
