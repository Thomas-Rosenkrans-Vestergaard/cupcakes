package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.presets.MysqlPresetsDAO;
import tvestergaard.cupcakes.database.presets.Preset;
import tvestergaard.cupcakes.database.presets.PresetsDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PresetServlet", urlPatterns = "/preset")
public class PresetServlet extends HttpServlet
{

    /**
     * The name of the url parameter where the id of the preset to show is provided.
     */
    private static final String ID_PARAMETER = "id";

    /**
     * The warning message provided when a preset with the provided id doesn't exist.
     */
    private static final String NO_PRESET = "A preset with that id doesn't exist.";

    /**
     * The location of the preset jsp-file served to incoming GET requests.
     */
    private static final String PRESET_JSP = "WEB-INF/preset.jsp";

    /**
     * Serves the /preset page where users can see information about the preset with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);

        if (request.getParameter(ID_PARAMETER) == null) {
            notifications.error(Language.MISSING_ID_PARAMETER);
            response.sendRedirect(request.getHeader("referer"));
            return;
        }

        try {
            PresetsDAO presetsDAO = new MysqlPresetsDAO(new PrimaryDatabase());
            int        id         = Integer.parseInt(request.getParameter(ID_PARAMETER));
            Preset     preset     = presetsDAO.get(id);

            if (preset == null) {
                notifications.warning(NO_PRESET);
                response.sendRedirect(request.getHeader("referer"));
                return;
            }

            request.setAttribute("preset", preset);
            request.getRequestDispatcher(PRESET_JSP).forward(request, response);

        } catch (NumberFormatException e) {
            notifications.warning(Language.MALFORMED_ID_PARAMETER);
            response.sendRedirect(request.getHeader("referer"));
        }
    }

    /**
     * Serves the /preset page where users can see information about the preset with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
