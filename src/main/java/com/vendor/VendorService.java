package com.vendor;

import com.Constant;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.customer.SessionException;
import com.db.Query;
import com.db.RESTOperation;
import com.mickey.curdoperation.FindQuery;
import com.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class VendorService {
    private static RESTOperation rest = null;
    private VendorService(){}
    public VendorService(HttpSession session) throws SessionException {
        sessionvalidate(session);
        rest = RESTOperation.getInstance();
    }
    private void sessionvalidate(HttpSession session) throws SessionException {
        if(session != null) {
            String type = (String) session.getAttribute(Constant.Usersdata.isadmin);
            System.out.println(type);
            if (type.equals("false")) {
                throw new SessionException("Unauthorized");
            }
        }
    }

    public JSONArray getAllCategory(String userid) throws SQLException, DataAccessException {
        String tablename = Constant.DataBase_UserTableName.DBProductdata;

        Criteria criteria = new Criteria(new Column(tablename, Constant.DataBase_Gobal_Products.vendorid),
                userid, QueryConstants.EQUAL);
        DataObject dataObject = FindQuery.find(tablename, criteria);
        JSONArray categoryslist = new JSONArray();
        if (!dataObject.isEmpty()) {
            java.util.Iterator itr = dataObject.getRows(tablename);
            while (itr.hasNext()) {
                Row row = (Row) itr.next();
                JSONObject categorydetails = new JSONObject();
                categorydetails.put(Constant.DataBase_Gobal_Products.categoryname,
                        row.get(Constant.DataBase_Gobal_Products.categoryname));
                categoryslist.add(categorydetails);
            }
        }

//        String condition = " "+ Constant.DataBase_Gobal_Products.vendorid +" = '"+ userid +"';";
//        ResultSet resultdata = rest.executeQuery(Query.find(Constant.DataBase_UserTableName.DBProductdata, condition));
//        JSONArray categoryslist = new JSONArray();
//        while(resultdata.next()){
//            JSONObject categorydetails =new JSONObject();
//            categorydetails.put(Constant.DataBase_Gobal_Products.categoryname,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.categoryname));
//            categoryslist.add(categorydetails);
//        }
        return categoryslist;
    }
    public JSONArray getCategory(String userid) throws SQLException, DataAccessException {
        String tablename = Constant.DataBase_UserTableName.DBProductdata;

        Criteria criteria = new Criteria(new Column(tablename, Constant.DataBase_Gobal_Products.vendorid),
                userid, QueryConstants.EQUAL);
        DataObject dataObject = FindQuery.find(tablename, criteria);
        LOGGER.log(Level.INFO, "MUKILAN :: dataObject of getCategory " + dataObject);
        JSONArray productjson = new JSONArray();
        if (!dataObject.isEmpty()) {
            java.util.Iterator itr = dataObject.getRows(tablename);
            while(itr.hasNext()) {
                Row row = (Row) itr.next();
                LOGGER.log(Level.INFO, "MUKILAN :: Row of getCategory " + row);
                JSONObject productdetails =new JSONObject();
                productdetails.put(Constant.OrderDetail.productid,
                        row.get(Constant.OrderDetail.productid));
                productdetails.put(Constant.OrderDetail.vendorid,
                        row.get(Constant.OrderDetail.vendorid));
                productdetails.put(Constant.DataBase_Gobal_Products.product_name,
                        row.get(Constant.DataBase_Gobal_Products.product_name));
                productdetails.put(Constant.DataBase_Gobal_Products.brand_name,
                        row.get(Constant.DataBase_Gobal_Products.brand_name));
                productdetails.put(Constant.DataBase_Gobal_Products.color,
                        row.get(Constant.DataBase_Gobal_Products.color));
                productdetails.put(Constant.DataBase_Gobal_Products.size,
                        row.get(Constant.DataBase_Gobal_Products.size));
                productdetails.put(Constant.DataBase_Gobal_Products.quantity,
                        row.get(Constant.DataBase_Gobal_Products.quantity));
                productdetails.put(Constant.DataBase_Gobal_Products.price,
                        row.get(Constant.DataBase_Gobal_Products.price));
                productjson.add(productdetails);
            }
        }

//        String condition = " "+ Constant.DataBase_Gobal_Products.vendorid +" = '"+ userid +"';";
//        ResultSet resultdata = rest.executeQuery(Query.find(Constant.DataBase_UserTableName.DBProductdata, condition));
//        JSONArray productjson = new JSONArray();
//        while(resultdata.next()){
//            JSONObject productdetails =new JSONObject();
//            productdetails.put(Constant.OrderDetail.productid,
//                    resultdata.getString(Constant.OrderDetail.productid));
//            productdetails.put(Constant.OrderDetail.vendorid,
//                    resultdata.getString(Constant.OrderDetail.vendorid));
//            productdetails.put(Constant.DataBase_Gobal_Products.product_name,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.product_name));
//            productdetails.put(Constant.DataBase_Gobal_Products.brand_name,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.brand_name));
//            productdetails.put(Constant.DataBase_Gobal_Products.color,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.color));
//            productdetails.put(Constant.DataBase_Gobal_Products.size,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.size));
//            productdetails.put(Constant.DataBase_Gobal_Products.quantity,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.quantity));
//            productdetails.put(Constant.DataBase_Gobal_Products.price,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.price));
//            productjson.add(productdetails);
//        }

        return productjson;
    }
    public void addProduct(String userid, Map<String, String> payload) throws SQLException, DataAccessException {
        Validation validation = new Validation();
        validation.setCategoryname(payload.get("categoryname").toLowerCase());
        validation.checkCategory();

        String tablename = Constant.DataBase_UserTableName.DBProductdata;
        LOGGER.log(Level.INFO,"MUKILAN :: Result of added product " +
                FindQuery.queryAddProduct(tablename, userid, payload));

//        rest.executeUpdate(Query.queryAddProduct(Constant.DataBase_UserTableName.DBProductdata,
//                userid,payload));
    }

    public void deleteOrder(String orderid) throws SQLException, DataAccessException {
        String order_tablename = Constant.DataBase_UserTableName.OrderDetail;

        Criteria criteria = new Criteria(new Column(order_tablename,Constant.OrderDetail.id),
                orderid,QueryConstants.EQUAL);
        FindQuery.delete(order_tablename,criteria);

//        rest.executeUpdate(Query.delete(order_tablename,Constant.OrderDetail.id,orderid));
    }

    public void updateDelivery(String history_tablename, String value) throws SQLException, DataAccessException {
        Accountmanagement accountmanagement = new Accountmanagement();
        String completedAt = String.valueOf(accountmanagement.getTimeNow());
        String stage = Constant.VendorStage.delivered;
        String order_tablename = Constant.DataBase_UserTableName.OrderDetail;

        Criteria criteria = new Criteria(new Column(order_tablename, Constant.OrderDetail.id),
                value, QueryConstants.EQUAL);
        DataObject dataObject = FindQuery.find(order_tablename, criteria);
        if (!dataObject.isEmpty()) {
            FindQuery.queryAddOrder_history(history_tablename, order_tablename, dataObject, completedAt, stage);
            deleteOrder(value);
        }

//        String condition = " "+ Constant.OrderDetail.id +" = '"+ value +"';";
//        ResultSet resultdata = rest.executeQuery(Query.find(order_tablename, condition));
//        rest.executeUpdate(Query.queryAddOrder_history(history_tablename, resultdata, completedAt, stage));
//        deleteOrder(value);
    }
    public void deleteProduct(String orderid) throws SQLException, DataAccessException {
        String product_tablename = Constant.DataBase_UserTableName.DBProductdata;

        Criteria criteria = new Criteria(new Column(product_tablename,Constant.DataBase_Gobal_Products.productid),
                orderid,QueryConstants.EQUAL);
        FindQuery.delete(product_tablename,criteria);

//        rest.executeUpdate(Query.delete(product_tablename,Constant.DataBase_Gobal_Products.productid,orderid));
    }

//    public JSONArray getProduct(String product_tablename, String order_tablename, String stage, String userid) throws SQLException {
//        String condition = " "+ Constant.UserHistory.stage +" = '"+ stage +"' " +
//                " and "+ order_tablename+"."+ Constant.UserHistory.vendorid +" = '"+ userid +"';";
//        ResultSet resultdata = rest.executeQuery(Query.findcart(product_tablename, order_tablename, condition));
//        return new ResultSettoJSON().ProductTable(resultdata);
//    }

//    public JSONArray getDelivered(String product_tablename, String delivered_tablename, String stagename, String stage) throws SQLException {
//        String condition = " "+ stagename +" = '"+ stage +"';";
//        ResultSet resultdata = rest.executeQuery(Query.find(delivered_tablename, condition));
//        return new ResultSettoJSON().ProductTable(resultdata);
//    }
}
