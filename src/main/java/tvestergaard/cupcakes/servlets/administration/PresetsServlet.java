package tvestergaard.cupcakes.servlets.administration;

import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.presets.MysqlPresetDAO;
import tvestergaard.cupcakes.database.presets.PresetDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PresetsServlet", urlPatterns = "/administration/presets")
public class PresetsServlet extends HttpServlet
{

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);
        Authentication authentication = new Authentication(request, response, "../");

        if (!authentication.isAdministrator()) {
            authentication.redirect("administration/presets");
            return;
        }

        PresetDAO presetDAO = new MysqlPresetDAO(new PrimaryDatabase());
        request.setAttribute("presets", presetDAO.get());
        request.getRequestDispatcher("/WEB-INF/administration/presets.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        super.doPost(request, response);
    }
}
