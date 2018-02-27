package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.database.toppings.Topping;
import tvestergaard.cupcakes.database.toppings.ToppingDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ToppingServlet", urlPatterns = "/topping")
public class ToppingServlet extends HttpServlet
{

    /**
     * The name of the url parameter where the id of the topping to show is provided.
     */
    public static final String ID_PARAMETER = "id";

    /**
     * The warning message provided when a topping with the provided id doesn't exist.
     */
    private static final String NO_TOPPING_MESSAGE = "A topping with that id doesn't exist.";

    /**
     * The location of the topping jsp-file served to incoming GET requests.
     */
    private static final String TOPPING_JSP = "WEB-INF/topping.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notificationHelper = new Notifications(request);

        if (request.getParameter(ID_PARAMETER) == null) {
            notificationHelper.warning(Language.MISSING_ID_PARAMETER);
            response.sendRedirect(request.getHeader("referer"));
            return;
        }

        try {
            ToppingDAO toppingDAO = new MysqlToppingDAO(new PrimaryDatabase());
            int        id         = Integer.parseInt(request.getParameter(ID_PARAMETER));
            Topping    topping    = toppingDAO.get(id);

            if (topping == null) {
                notificationHelper.warning(NO_TOPPING_MESSAGE);
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            request.setAttribute("topping", topping);
            request.getRequestDispatcher(TOPPING_JSP).forward(request, response);

        } catch (NumberFormatException e) {
            notificationHelper.warning(Language.MALFORMED_ID_PARAMETER);
            response.sendRedirect(request.getHeader("referer"));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
