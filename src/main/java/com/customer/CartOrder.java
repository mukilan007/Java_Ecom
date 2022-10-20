package com.customer;

import com.Constant;
import com.adventnet.db.persistence.metadata.MetaDataException;
import com.adventnet.persistence.DataAccessException;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@WebServlet("/cart/order")
public class CartOrder extends HttpServlet {
    private String getUserID(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return String.valueOf(session.getAttribute(Constant.Usersdata.userid));
    }
    private String getTableName(HttpServletRequest req){
        return Constant.DataBase_UserTableName.OrderHistory + getUserID(req);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONArray jsoncart = null;
        String stage = Constant.VendorStage.ordered;
        HttpSession session = request.getSession(false);
        String userid = getUserID(request);
        String table_name1 = Constant.DataBase_UserTableName.DBProductdata;
        String table_name2 = Constant.DataBase_UserTableName.OrderDetail;
        try {
            jsoncart = new CustomerService(session).findorder(table_name1, table_name2, stage, userid);
            LOGGER.log(Level.INFO, "MUKILAN :: List of Product in user cart" + jsoncart);
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
        }  catch (MetaDataException e) {
            response.sendError(401, "MetaDataException");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        PrintWriter out = response.getWriter();
        out.print(jsoncart);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cart_tablename = getTableName(request);
        String userid = getUserID(request);
        HttpSession session = request.getSession(false);
        try {
            new CustomerService(session).updatecart(cart_tablename, Constant.DataBase_UserTableName.OrderDetail,
                    Constant.VendorStage.ordered, userid);
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
