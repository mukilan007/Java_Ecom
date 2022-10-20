package com.Login;

import com.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/logout")
public class Logout extends HttpServlet {
    public Logout() {super();}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        if (session != null) {
            String user_id = (String) session.getAttribute(Constant.Usersdata.userid);
            String name = (String) session.getAttribute(Constant.Usersdata.name);
            String admin = (String) session.getAttribute(Constant.Usersdata.isadmin);
            session.removeAttribute("user_id");
            session.removeAttribute("name");
            session.removeAttribute("admin");
            session.invalidate();
            out.print(name);
        }
    }
}
