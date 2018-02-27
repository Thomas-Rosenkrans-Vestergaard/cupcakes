package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.bottoms.BottomDAO;
import tvestergaard.cupcakes.database.bottoms.MysqlBottomDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BottomServlet", urlPatterns = "/bottom")
public class BottomServlet extends HttpServlet
{

    /**
     * The name of the url parameter where the id of the bottom to show is provided.
     */
    public static final String ID_PARAMETER = "id";

    /**
     * The warning message provided when a bottom with the provided id doesn't exist.
     */
    private static final String NO_BOTTOM_MESSAGE = "A bottom with that id doesn't exist.";

    /**
     * The location of the bottom jsp-file served to incoming GET requests.
     */
    private static final String BOTTOM_JSP = "WEB-INF/bottom.jsp";

    /**
     * Serves the /preset page where users can see information about the bottom with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notificationHelper = new Notifications(request);

        if (request.getParameter(ID_PARAMETER) == null) {
            notificationHelper.error(Language.MISSING_ID_PARAMETER);
            response.sendRedirect(request.getHeader("referer"));
            return;
        }

        try {
            BottomDAO bottomDAO = new MysqlBottomDAO(new PrimaryDatabase());
            int       id        = Integer.parseInt(request.getParameter(ID_PARAMETER));
            Bottom    bottom    = bottomDAO.get(id);

            if (bottom == null) {
                notificationHelper.error(NO_BOTTOM_MESSAGE);
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            request.setAttribute("bottom", bottom);
            request.getRequestDispatcher(BOTTOM_JSP).forward(request, response);

        } catch (NumberFormatException e) {
            notificationHelper.error(Language.MALFORMED_ID_PARAMETER);
            response.sendRedirect(request.getHeader("referer"));
            return;
        }
    }

    /**
     * Serves the /preset page where users can see information about the bottom with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
