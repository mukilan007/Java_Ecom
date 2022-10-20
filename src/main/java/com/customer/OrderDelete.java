package com.customer;

import com.Constant;
import com.adventnet.persistence.DataAccessException;
import com.base.BaseClass;
import com.customer.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/customer/delete")
public class OrderDelete extends HttpServlet {
    private String getUserID(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return String.valueOf(session.getAttribute(Constant.Usersdata.userid));
    }
    private String getTableName(HttpServletRequest req){
        return Constant.DataBase_UserTableName.OrderHistory + getUserID(req);
    }
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> payload = new BaseClass().getPayload(request);
        HttpSession session = request.getSession(false);
        String cart_tablename = getTableName(request);

        try {
            new CustomerService(session).deleteProduct(cart_tablename, payload.get(Constant.UserHistory.productid));
        } catch (SQLException e) {
            response.sendError(401, "SQLError");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SessionException e) {
            response.sendError(401, "Unauthorized");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
