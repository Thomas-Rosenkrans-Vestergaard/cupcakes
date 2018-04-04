package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.data.presets.MysqlPresetDAO;
import tvestergaard.cupcakes.data.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.logic.*;
import tvestergaard.cupcakes.view.Language;
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

    /**
     * Facade for performing various operations related to presets.
     */
    private final PresetFacade presetFacade = new PresetFacade(new MysqlPresetDAO(ProductionDatabaseSource.singleton()));

    /**
     * Facade for performing various operations related to bottoms.
     */
    private final BottomFacade bottomFacade = new BottomFacade(new MysqlBottomDAO(ProductionDatabaseSource.singleton()));

    /**
     * Facade for performing various operations related to toppings.
     */
    private final ToppingFacade toppingFacade = new ToppingFacade(new MysqlToppingDAO(ProductionDatabaseSource.singleton()));

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

            request.setAttribute("presets", presetFacade.get());
            request.setAttribute("bottoms", bottomFacade.get());
            request.setAttribute("toppings", toppingFacade.get());
            ViewUtilities.attach(request, notifications);
            request.getRequestDispatcher("WEB-INF/shop.jsp").forward(request, response);

        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }
}
