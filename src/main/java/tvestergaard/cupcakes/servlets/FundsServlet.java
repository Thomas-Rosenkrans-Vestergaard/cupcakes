package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Authentication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/funds")
public class FundsServlet extends HttpServlet
{


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

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

