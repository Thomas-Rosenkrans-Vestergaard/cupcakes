package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.logic.UserFacade;
import tvestergaard.cupcakes.view.Authentication;
import tvestergaard.cupcakes.view.Language;
import tvestergaard.cupcakes.view.Notifications;
import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.users.MysqlUserDAO;
import tvestergaard.cupcakes.data.users.User;
import tvestergaard.cupcakes.view.Parameters;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.cupcakes.view.Language.GENERAL_ERROR_RENDER;
import static tvestergaard.cupcakes.view.Language.INCOMPLETE_FORM_POST;

@WebServlet(name = "LogoutServlet", urlPatterns = "/funds")
public class FundsServlet extends HttpServlet
{

    /**
     * Facade for performing various operations related to users.
     */
    private final UserFacade userFacade = new UserFacade(new MysqlUserDAO(ProductionDatabaseSource.singleton()));

    /**
     * Renders the /funds page where users can see their balance, and add more funds.
     *
     * @param request  The request.
     * @param response The response.
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request);
        Notifications  notifications  = ViewUtilities.getNotifications(request);

        if (!authentication.isAuthenticated()) {
            notifications.warning(Language.ERROR_ACCESS_USER);
            response.sendRedirect("login?from=funds");
            return;
        }

        ViewUtilities.attach(request, notifications);
        request.getRequestDispatcher("WEB-INF/funds.jsp").forward(request, response);
    }

    /**
     * Handles POST requests when users use the "add funds" form.
     *
     * @param request  The request.
     * @param response The response.
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);
        Parameters     parameters     = new Parameters(request);

        if (!authentication.isAuthenticated()) {
            response.sendRedirect("login?from=funds");
            return;
        }

        if (parameters.notInt("amount")) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect("funds");
            return;
        }

        try {

            int  amount = parameters.getInt("amount");
            User user   = userFacade.get(authentication.getUser().getId());
            user = userFacade.update(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getBalance() + amount * 100,
                    user.getRole()
                                    );

            authentication.updateUser(user);
            notifications.success("You have added $" + amount + " in funds to your wallet.");
            response.sendRedirect("funds");
        } catch (Exception e) {
            notifications.error(GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }
}

