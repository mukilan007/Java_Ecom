package com.customer;

import com.Constant;
import com.adventnet.db.persistence.metadata.MetaDataException;
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
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@WebServlet("/cart")
public class Cart extends HttpServlet {
    private String getUserID(HttpServletRequest req) throws SessionException{
        HttpSession session = req.getSession(false);
        try {
            return String.valueOf(session.getAttribute(Constant.Usersdata.userid));
        } catch (Exception e) {
            throw new SessionException("Unauthorized");
        }
    }
    private String getTableName(HttpServletRequest req) throws SessionException{
        return Constant.DataBase_UserTableName.OrderHistory + getUserID(req);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String stage = Constant.CustomerStage.cart;
        JSONArray jsoncart = null;
        HttpSession session = request.getSession(false);
        try {
            String tablename = getTableName(request);
            jsoncart = new CustomerService(session).findcard(Constant.DataBase_UserTableName.DBProductdata, tablename,
                    stage);
            LOGGER.log(Level.INFO, "MUKILAN :: List of Product in user cart" + jsoncart);
        } catch (SQLException e) {
            response.sendError(401, "SQL Error");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SessionException e) {
//            response.sendError(401, "Unauthorized");
            response.setStatus(400);
            out.print(e.getMessage());
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
        out.print(jsoncart);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> payload = new BaseClass().getPayload(request);
        HttpSession session = request.getSession(false);
        try {
            String tablename = getTableName(request);
            new CustomerService(session).addcart(tablename,payload);
        } catch (SQLException e) {
            response.sendError(401, "SQL Error");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (CardExistException e) {
            response.sendError(409, "cart already exist");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SessionException e) {
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
