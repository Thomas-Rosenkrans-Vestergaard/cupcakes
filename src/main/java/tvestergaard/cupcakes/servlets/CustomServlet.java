package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Notifications;
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
     * The location of the custom jsp-file served to incoming GET requests.
     */
    private static final String BOTTOM_JSP = "WEB-INF/custom.jsp";

    /**
     * Serves the /custom page where users can create their own cupcake.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        new Notifications(request);
        PrimaryDatabase source = new PrimaryDatabase();
        MysqlBottomDAO bottomDAO = new MysqlBottomDAO(source);
        MysqlToppingDAO toppingDAO = new MysqlToppingDAO(source);

        request.setAttribute("bottoms", bottomDAO.get());
        request.setAttribute("toppings", toppingDAO.get());

        request.getRequestDispatcher("WEB-INF/custom.jsp").forward(request, response);
    }

    /**
     * Serves the /preset page where users can see information about the bottom with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request);

        if(!authentication.isAuthenticated()){

        }
    }
}