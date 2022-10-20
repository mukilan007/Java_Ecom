package com.vendor;

import com.Constant;
import com.adventnet.persistence.DataAccessException;
import com.base.BaseClass;
import com.customer.SessionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/vendor/edit")
public class VendorUpdate extends HttpServlet {
    public VendorUpdate(){
        super();
    }
    private String getUserID(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return String.valueOf(session.getAttribute(Constant.Usersdata.userid));
    }
    private String getTableName(HttpServletRequest req){
        return Constant.DataBase_UserTableName.OrderHistory + getUserID(req);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> payload = new BaseClass().getPayload(request);
        HttpSession session = request.getSession(false);

        String history_tablename = getTableName(request);
        try {
        new VendorService(session).updateDelivery(history_tablename, payload.get(Constant.OrderDetail.id));
        } catch (SQLException e) {
            response.sendError(401, "SQL Error");
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
