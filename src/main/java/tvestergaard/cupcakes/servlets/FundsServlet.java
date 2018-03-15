package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.*;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.users.MysqlUserDAO;
import tvestergaard.cupcakes.database.users.User;
import tvestergaard.cupcakes.database.users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/funds")
public class FundsServlet extends HttpServlet
{

    public static String PARAMETER_AMOUNT = "amount";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = new Notifications(request);
        Authentication authentication = new Authentication(request, response);

        try {

            if (!authentication.isAuthenticated()) {
                authentication.redirect("funds");
                return;
            }

            Parameters parameters = new Parameters(request);

            if (parameters.notInt(PARAMETER_AMOUNT)) {
                notifications.error(Language.INCOMPLETE_FORM_POST);
                response.sendRedirect(Utility.referer(request, "funds"));
                return;
            }

            int amount = parameters.getInt(PARAMETER_AMOUNT);

            UserDAO userDAO = new MysqlUserDAO(new PrimaryDatabase());
            User    user    = userDAO.get(authentication.getUser().getId());
            user = userDAO.update(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getBalance() + amount * 100,
                    user.getRole()
            );

            HttpSession session = request.getSession();
            session.setAttribute(Config.USER_SESSION_KEY, user);
            notifications.success("You have added $" + amount + " in funds.");
            response.sendRedirect("funds");
        } catch (Exception e) {
            notifications.error("An error occurred that prevented the requested page from being rendered.");
            response.sendRedirect(Utility.referer(request, "shop"));
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request, response);

        if (!authentication.isAuthenticated()) {
            authentication.redirect("funds");
            return;
        }

        request.getRequestDispatcher("WEB-INF/funds.jsp").forward(request, response);
    }
}

