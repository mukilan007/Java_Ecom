package com.Login;

import com.Constant;
import com.adventnet.db.persistence.metadata.MetaDataException;
import com.adventnet.persistence.DataAccessException;
import com.base.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({"/login"})
public class SignIn extends HttpServlet {
    public SignIn() {super();}
    protected LoginService loginService = new LoginService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> payload = new BaseClass().getPayload(request);

        try {
            if(payload == null || !(loginService.SignIn(request, payload)))
                response.sendError(401, "Unauthorized");
            else {
                HttpSession session = request.getSession(false);
                String name = (String) session.getAttribute(Constant.Usersdata.name);
                String type = (String) session.getAttribute(Constant.Usersdata.isadmin);
                PrintWriter out = response.getWriter();
                if(type.equals("true"))
                    out.printf("isadmin", name);        //TODO: need to need two parameter to js
                else
                    out.printf("notadmin", name);
            }
        } catch (SQLException e) {
            response.sendError(401, "Unauthorized");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> payload = new BaseClass().getPayload(request);
        String admin = request.getParameter("is_admin");

        try {
            if(!loginService.SignUp(request, payload, admin)) {
                response.sendError(401, "Unauthorized");
            }
            else{
                HttpSession session = request.getSession(false);
                String name = (String) session.getAttribute(Constant.Usersdata.name);
                String type = (String) session.getAttribute(Constant.Usersdata.isadmin);
                PrintWriter out = response.getWriter();
                if (type.equals("true"))
                    out.printf("isadmin", name);
                else
                    out.printf("notadmin", name);
            }
        } catch (SQLException e) {
            response.sendError(401, "Unauthorized");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            response.sendError(401, "DataAccessException");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (MetaDataException e) {
            response.sendError(401, "MetaDataException");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
