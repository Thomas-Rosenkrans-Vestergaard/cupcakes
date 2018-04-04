package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.logic.Language;
import tvestergaard.cupcakes.logic.Notifications;
import tvestergaard.cupcakes.data.PrimaryDatabase;
import tvestergaard.cupcakes.data.bottoms.BottomDAO;
import tvestergaard.cupcakes.data.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.data.presets.MysqlPresetDAO;
import tvestergaard.cupcakes.data.presets.PresetDAO;
import tvestergaard.cupcakes.data.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.data.toppings.ToppingDAO;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Serves the /shop page where visitors can see the presets, bottoms and toppings that can be purchased.
 *
 * @author Thomas
 */
@WebServlet(name = "ShopServlet", urlPatterns = {"/shop", ""})
public class ShopServlet extends HttpServlet
{

    private final PrimaryDatabase source = new PrimaryDatabase();

    /**
     * The {@link PresetDAO} used to retrieve presets from the database, to be shown on the /shop page.
     */
    private PresetDAO presetDAO = new MysqlPresetDAO(source);

    /**
     * The {@link BottomDAO} used to retrieve bottoms from the database, to be shown on the /shop page.
     */
    private BottomDAO bottomDAO = new MysqlBottomDAO(source);

    /**
     * The {@link ToppingDAO} used to retrieve toppings from the database, to be shown on the /shop page.
     */
    private final ToppingDAO toppingDAO = new MysqlToppingDAO(source);

    /**
     * Serves the /shop page where users can see the products of the shop.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = ViewUtilities.getNotifications(request);

        try {

            request.setAttribute("presets", presetDAO.get());
            request.setAttribute("bottoms", bottomDAO.get());
            request.setAttribute("toppings", toppingDAO.get());
            ViewUtilities.attach(request, notifications);
            request.getRequestDispatcher("WEB-INF/shop.jsp").forward(request, response);

        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }
}
