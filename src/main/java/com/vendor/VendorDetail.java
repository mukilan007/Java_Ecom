package com.vendor;

import com.Constant;
import com.adventnet.persistence.DataAccessException;
import com.base.BaseClass;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownServiceException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/vendor/detail")
public class VendorDetail extends HttpServlet {
    private String getUserID(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return String.valueOf(session.getAttribute(Constant.Usersdata.userid));
    }
    private String getTableName(HttpServletRequest req){
        HttpSession session = req.getSession();
        String userid = String.valueOf(session.getAttribute(Constant.Usersdata.userid));
        return Constant.DataBase_UserTableName.OrderHistory + userid;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> payload = new BaseClass().getPayload(request);
        String userid = getUserID(request);

        String delivered_tablename = getTableName(request);

        JSONArray products = new JSONArray();
        String order_tablename = Constant.DataBase_UserTableName.OrderDetail;
        String product_tablename = Constant.DataBase_UserTableName.DBProductdata;
        String stage = payload.get(Constant.UserHistory.stage);
        try {
            Strategy strategy = null;
            if(payload.get(Constant.UserHistory.stage).equalsIgnoreCase(Constant.VendorStage.ordered)) {
                strategy = new Delivery();
                products = strategy.getProduct(product_tablename, order_tablename,
                        payload.get(Constant.UserHistory.stage), userid);
            } else if (stage.equalsIgnoreCase(Constant.VendorStage.delivered)) {
                strategy = new Delivered();
                products = strategy.getProduct(product_tablename, delivered_tablename,
                        payload.get(Constant.UserHistory.stage),stage);
            }
            else {
                throw new SQLException("does not match");
            }
        } catch (SQLException e) {
            response.sendError(401, "Unauthorized");
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        PrintWriter out = response.getWriter();
        System.out.println(products.toString());
        out.print(products);
    }
}
