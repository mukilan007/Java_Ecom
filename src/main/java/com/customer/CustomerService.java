package com.customer;

import com.Constant;
import com.adventnet.db.api.RelationalAPI;
import com.adventnet.db.persistence.metadata.MetaDataException;
import com.adventnet.ds.query.*;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.db.Query;
import com.db.RESTOperation;
import com.Iterator;
import com.mickey.curdoperation.FindQuery;
import com.notification.Emailnotification;
import com.notification.Notification;
import com.notification.SMSnotification;
import com.util.Accountmanagement;
import com.util.ResultSettoJSON;
import com.util.Validation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class CustomerService{
    private RESTOperation rest = null;
    private CustomerService(){}
    public CustomerService(HttpSession session) throws SessionException {
        sessionvalidate(session);
        rest = RESTOperation.getInstance();
    }

    private void sessionvalidate(HttpSession session) throws SessionException {
        if(session != null) {
            String type = (String) session.getAttribute(Constant.Usersdata.isadmin);
            if (type.equals("true")) {
                throw new SessionException("Unauthorized");
            }
        }
    }

    //    public JSONArray getAllCategory(String table_name, getSpecificTable strInstance){
//        ResultSet resultdata = rest.find(Query.findall(table_name));
//        JSONArray categoryslist = strInstance.ResultSettoJSON(resultdata);
////        System.out.println(categoryslist.toString());
//        return categoryslist;
//      }
    public JSONArray getAllCategory() throws SQLException, DataAccessException {
        String tablename = Constant.DataBase_UserTableName.Categorydata;
        DataObject dataObject = FindQuery.findall(tablename);
        LOGGER.log(Level.INFO, "MUKILAN :: dataObject of getAllCategory" + dataObject);
        return new ResultSettoJSON().TableListOfCategory(tablename, dataObject);

//        ResultSet resultdata = rest.executeQuery(Query.findall(Constant.DataBase_UserTableName.Categorydata));
//        return new ResultSettoJSON().TableListOfCategory(resultdata);
    }

    public void deleteOrder(String orderid) throws SQLException, DataAccessException {
        String order_tablename = Constant.DataBase_UserTableName.OrderDetail;

        Criteria criteria = new Criteria(new Column(order_tablename,Constant.OrderDetail.id),
                orderid,QueryConstants.EQUAL);
        FindQuery.delete(order_tablename,criteria);

//        rest.executeUpdate(Query.delete(order_tablename,Constant.OrderDetail.id,orderid));
    }

    public JSONArray findproduct(Map<String, String> payload) throws SQLException, DataAccessException {
        String tablename = Constant.DataBase_UserTableName.DBProductdata;

        Criteria criteria = new Criteria(new Column(tablename, Constant.DataBase_Gobal_Products.categoryname),
                payload.get(Constant.AllCategory.categoryname), QueryConstants.EQUAL);
        DataObject dataObject = FindQuery.find(tablename, criteria);
        LOGGER.log(Level.INFO,"MUKILAN :: dataObject of findproduct " + dataObject);
        JSONArray categoryslist = new JSONArray();
        if (!dataObject.isEmpty())
        {
            java.util.Iterator itr = dataObject.getRows(tablename);
            while (itr.hasNext()) {
                Row row = (Row) itr.next();
                LOGGER.log(Level.INFO,"MUKILAN :: row of findproduct " + row);
                JSONObject categorydetails =new JSONObject();
                categorydetails.put(Constant.DataBase_Gobal_Products.productid,
                        row.get(Constant.DataBase_Gobal_Products.productid));
                categorydetails.put(Constant.DataBase_Gobal_Products.vendorid,
                        row.get(Constant.DataBase_Gobal_Products.vendorid));
                categorydetails.put(Constant.DataBase_Gobal_Products.product_name,
                        row.get(Constant.DataBase_Gobal_Products.product_name));
                categorydetails.put(Constant.DataBase_Gobal_Products.categoryname,
                         row.get(Constant.DataBase_Gobal_Products.categoryname));
                categorydetails.put(Constant.DataBase_Gobal_Products.detail,
                         row.get(Constant.DataBase_Gobal_Products.detail));
                categorydetails.put(Constant.DataBase_Gobal_Products.quantity,
                         row.get(Constant.DataBase_Gobal_Products.quantity));
                categorydetails.put(Constant.DataBase_Gobal_Products.size,
                         row.get(Constant.DataBase_Gobal_Products.size));
                categorydetails.put(Constant.DataBase_Gobal_Products.color,
                         row.get(Constant.DataBase_Gobal_Products.color));
                categorydetails.put(Constant.DataBase_Gobal_Products.brand_name,
                         row.get(Constant.DataBase_Gobal_Products.brand_name));
                categorydetails.put(Constant.DataBase_Gobal_Products.price,
                         row.get(Constant.DataBase_Gobal_Products.price));
                categorydetails.put(Constant.DataBase_Gobal_Products.type,
                         row.get(Constant.DataBase_Gobal_Products.type));

                categoryslist.add(categorydetails);
            }
        }
        LOGGER.log(Level.INFO,"MUKILAN :: Result value of findproduct " + categoryslist);

//        String condition = " "+ Constant.DataBase_Gobal_Products.categoryname +" = '"+
//                payload.get(Constant.AllCategory.categoryname) +"';";
//        ResultSet resultdata = rest.executeQuery(Query.find(Constant.DataBase_UserTableName.DBProductdata, condition));
//        JSONArray categoryslist = new JSONArray();
//        while(resultdata.next()){
//            JSONObject categorydetails =new JSONObject();
//            categorydetails.put(Constant.DataBase_Gobal_Products.productid,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.productid));
//            categorydetails.put(Constant.DataBase_Gobal_Products.vendorid,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.vendorid));
//            categorydetails.put(Constant.DataBase_Gobal_Products.product_name,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.product_name));
//            categorydetails.put(Constant.DataBase_Gobal_Products.categoryname,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.categoryname));
//            categorydetails.put(Constant.DataBase_Gobal_Products.detail,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.detail));
//            categorydetails.put(Constant.DataBase_Gobal_Products.quantity,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.quantity));
//            categorydetails.put(Constant.DataBase_Gobal_Products.size,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.size));
//            categorydetails.put(Constant.DataBase_Gobal_Products.color,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.color));
//            categorydetails.put(Constant.DataBase_Gobal_Products.brand_name,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.brand_name));
//            categorydetails.put(Constant.DataBase_Gobal_Products.price,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.price));
//            categorydetails.put(Constant.DataBase_Gobal_Products.type,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.type));
//
//            categoryslist.add(categorydetails);
//        }

        return categoryslist;
    }
    public void addcart(String tablename, Map<String, String> payload)
            throws SQLException, CardExistException, DataAccessException, MetaDataException {
        Criteria criteria = new Criteria(new Column(tablename, Constant.UserHistory.productid ),
                payload.get(Constant.UserHistory.productid), QueryConstants.EQUAL);
        DataObject dataObject = FindQuery.find(tablename, criteria);
        LOGGER.log(Level.INFO,"MUKILAN :: dataObject of addcart " + dataObject);
        if (dataObject.isEmpty()) {
//            new Validation().checkUserHistoryTable(tablename);
            Accountmanagement accountmanagement = new Accountmanagement();
            payload.put(Constant.UserHistory.quantity, "1");
            payload.put(Constant.UserHistory.stage, Constant.CustomerStage.cart);
            payload.put(Constant.UserHistory.createdAt, String.valueOf(accountmanagement.getCreatedAt()));
            payload.put(Constant.UserHistory.completedAt, null);

            LOGGER.log(Level.INFO,"MUKILAN :: Result value of addcart " +
                    FindQuery.queryAddCart(tablename, payload));
        }
        else {
            throw new CardExistException("cart already exist");
        }

//        String condition = " "+ Constant.UserHistory.productid +" = '"+ payload.get(Constant.UserHistory.productid) +"';";
//        ResultSet resultdata = rest.executeQuery(Query.find(tablename, condition));
//        if (!resultdata.next()) {
//            new Validation().checkUserHistoryTable(tablename);
//            Accountmanagement accountmanagement = new Accountmanagement();
//            payload.put(Constant.UserHistory.quantity, "1");
//            payload.put(Constant.UserHistory.stage, Constant.CustomerStage.cart);
//            payload.put(Constant.UserHistory.createdAt, String.valueOf(accountmanagement.getCreatedAt()));
//            payload.put(Constant.UserHistory.completedAt, null);

//            rest.executeUpdate(Query.queryAddCart(tablename, payload));
//        }
//        else {
//            throw new CardExistException("cart already exist");
//        }
    }
    public JSONArray findorder(String table_name1, String table_name2, String stage, String userid)
            throws SQLException, DataAccessException, MetaDataException {
//        new Validation().checkUserHistoryTable(table_name2);

        Criteria criteria1 = new Criteria(new Column(table_name2, Constant.OrderDetail.stage ),
                stage, QueryConstants.EQUAL);
        criteria1 = criteria1.and(new Criteria(Column.getColumn(table_name2, Constant.OrderDetail.customerid)
                , userid, QueryConstants.EQUAL));
        SelectQuery dataObject = FindQuery.findorder(table_name1, table_name2, criteria1);
        LOGGER.log(Level.INFO, "MUKILAN :: criteria in findorder" + criteria1);
        LOGGER.log(Level.INFO, "MUKILAN :: dataObject in findorder" + dataObject);
        return new ResultSettoJSON().ProductTable(stage, dataObject);

//        String condition =  " "+ Constant.OrderDetail.stage +" = '"+ stage +"' and "+
//                Constant.OrderDetail.customerid+" = '"+ userid +"';";
//        ResultSet resultdata = rest.executeQuery(Query.findorder(table_name1, table_name2, condition));
//        return new ResultSettoJSON().ProductTable(resultdata, stage);
    }
    public JSONArray findcard(String table_name1, String table_name2, String stage)
            throws SQLException, DataAccessException, MetaDataException {
//        new Validation().checkUserHistoryTable(table_name2);
        return getcard(table_name1, table_name2,stage);
    }
    private JSONArray getcard(String table_name1, String table_name2, String stage) throws SQLException, DataAccessException {

        Criteria criteria1 = new Criteria(new Column(table_name2, Constant.UserHistory.stage ),
                stage, QueryConstants.EQUAL);
        SelectQuery dataObject = FindQuery.findcart(table_name1, table_name2, criteria1);

//        String condition = " "+ Constant.UserHistory.stage +" = '"+ stage +"';";
//        ResultSet resultdata = rest.executeQuery(Query.findcart(table_name1, table_name2, condition));
//        int size = 0;
//        if (resultdata.last()) {
//            size = resultdata.getRow();
//            resultdata.beforeFirst();
//        }

        LOGGER.log(Level.INFO, "MUKILAN :: SelectQuery in getcard " + dataObject);
        RelationalAPI relApi = RelationalAPI.getInstance();
        Connection conn = null;
        DataSet ds = null;
        int value = 0;
        Iterator<Integer> iterator = new Iterator<>();
        JSONArray productjson = new JSONArray();
        try
        {
            conn = relApi.getConnection();
            ds = relApi.executeQuery(dataObject, conn);
            while(ds.next())
            {
                JSONObject productdetails =new JSONObject();
                productdetails.put(Constant.OrderDetail.productid,
                        ds.getAsString(1));
                productdetails.put(Constant.OrderDetail.vendorid,
                        ds.getAsString(2));
                productdetails.put(Constant.DataBase_Gobal_Products.product_name,
                        ds.getAsString(3));
                productdetails.put(Constant.DataBase_Gobal_Products.brand_name,
                        ds.getAsString(4));
                productdetails.put(Constant.DataBase_Gobal_Products.color,
                        ds.getAsString(5));
                productdetails.put(Constant.DataBase_Gobal_Products.size,
                        ds.getAsString(6));
                productdetails.put(Constant.OrderDetail.quantity,
                        ds.getAsString(7));
                productdetails.put(Constant.DataBase_Gobal_Products.price,
                        ds.getAsString(8));
                value = Integer.parseInt((String)productdetails.get(Constant.DataBase_Gobal_Products.quantity))*
                        Integer.parseInt((String)productdetails.get(Constant.DataBase_Gobal_Products.price));
                productdetails.put("totalprice", String.valueOf(value));
                iterator.add(value);
                productjson.add(productdetails);
            }
        } catch (SQLException | QueryConstructionException e) {
            throw new RuntimeException(e);
        }finally{
            try {
                ds.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

//        Iterator<Integer> iterator = new Iterator<>();
//        JSONArray productjson = new JSONArray();
//        try {
//            int value = 0;
//            java.util.Iterator itr2 = dataObject.getRows(table_name2);
//            java.util.Iterator itr1 = dataObject.getRows(table_name1);
//            while(itr2.hasNext() && itr1.hasNext()){
//                Row row1 = (Row) itr2.next();
//                Row row2 = (Row) itr1.next();
//                LOGGER.log(Level.INFO, "MUKILAN :: row1 in getcard " + row1);
//                LOGGER.log(Level.INFO, "MUKILAN :: row2 in getcard " + row2);
//                JSONObject productdetails =new JSONObject();
//                productdetails.put(Constant.UserHistory.productid,
//                         row1.get(Constant.UserHistory.productid));
//                productdetails.put(Constant.UserHistory.vendorid,
//                         row1.get(Constant.UserHistory.vendorid));
//                productdetails.put(Constant.DataBase_Gobal_Products.product_name,
//                         row2.get(Constant.DataBase_Gobal_Products.product_name));
//                productdetails.put(Constant.DataBase_Gobal_Products.brand_name,
//                         row2.get(Constant.DataBase_Gobal_Products.brand_name));
//                productdetails.put(Constant.DataBase_Gobal_Products.color,
//                         row2.get(Constant.DataBase_Gobal_Products.color));
//                productdetails.put(Constant.DataBase_Gobal_Products.size,
//                         row2.get(Constant.DataBase_Gobal_Products.size));
//                productdetails.put(Constant.UserHistory.quantity,
//                         row1.get(Constant.UserHistory.quantity));
//                productdetails.put(Constant.DataBase_Gobal_Products.price,
//                         row2.get(Constant.DataBase_Gobal_Products.price));
//                value = ((Long) productdetails.get(Constant.DataBase_Gobal_Products.quantity)).intValue() *
//                        ((Long)productdetails.get(Constant.DataBase_Gobal_Products.price)).intValue();
//                productdetails.put("totalprice", String.valueOf(value));
//                iterator.add(value);
//                productjson.add(productdetails);
//            }
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }


//        while(resultdata.next()){
//            JSONObject productdetails =new JSONObject();
//            productdetails.put(Constant.UserHistory.productid,
//                    resultdata.getString(Constant.UserHistory.productid));
//            productdetails.put(Constant.UserHistory.vendorid,
//                    resultdata.getString(Constant.UserHistory.vendorid));
//            productdetails.put(Constant.DataBase_Gobal_Products.product_name,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.product_name));
//            productdetails.put(Constant.DataBase_Gobal_Products.brand_name,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.brand_name));
//            productdetails.put(Constant.DataBase_Gobal_Products.color,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.color));
//            productdetails.put(Constant.DataBase_Gobal_Products.size,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.size));
//            productdetails.put(Constant.UserHistory.quantity,
//                    resultdata.getString(Constant.UserHistory.quantity));
//            productdetails.put(Constant.DataBase_Gobal_Products.price,
//                    resultdata.getString(Constant.DataBase_Gobal_Products.price));
//            value = Integer.parseInt((String) productdetails.get(Constant.DataBase_Gobal_Products.quantity)) *
//                    Integer.parseInt((String) productdetails.get(Constant.DataBase_Gobal_Products.price));
//            productdetails.put("totalprice", String.valueOf(value));
//            iterator.add(value);
//            productjson.add(productdetails);
//        }
//        System.out.println(productjson.toString());
        Iterator<Integer>.InnerIterator iterator1 = iterator.getInstance();
        int totalamount = 0;
        while(iterator1.hasNext()) {
            totalamount = totalamount + iterator1.next();
        }
        productjson.add(totalamount);
        LOGGER.log(Level.INFO,"MUKILAN :: Result value of addcart " + productjson);
        return productjson;
    }
    public void sendNotifcation(java.util.Iterator itr) {
        Emailnotification emailnotification = new Emailnotification();
        SMSnotification smsnotification = new SMSnotification();

        Notification notification = new Notification(emailnotification, smsnotification);

        while(itr.hasNext()) {
            Row row1 = (Row) itr.next();
            notification.add((String) row1.get(Constant.OrderDetail.productid),
                    (String) row1.get(Constant.OrderDetail.vendorid));
        }
        notification.dataChange();
    }
    public void decreaseProduct(String cart_tablename, DataObject dataObject) throws DataAccessException {
        String product_tablename = Constant.DataBase_UserTableName.DBProductdata;
        LOGGER.log(Level.INFO,"MUKILAN :: Result of decreaseProduct " +
                FindQuery.updateProductQuantity(cart_tablename, product_tablename, dataObject));
    }
    public void updatecart(String cart_tablename, String order_tablename, String stage, String userid)
            throws SQLException, DataAccessException {
        Criteria criteria = new Criteria(new Column(cart_tablename, Constant.UserHistory.stage),
                Constant.CustomerStage.cart, QueryConstants.EQUAL);
        DataObject dataObject = FindQuery.find(cart_tablename, criteria);
        LOGGER.log(Level.INFO,"MUKILAN :: dataObject of updatecart " + dataObject);
        if(!dataObject.isEmpty()) {
            FindQuery.queryAddOrder(order_tablename, cart_tablename,userid, stage, dataObject);

            decreaseProduct(cart_tablename, dataObject);

//            java.util.Iterator itr = dataObject.getRows(cart_tablename);
            criteria = new Criteria(new Column(cart_tablename,Constant.UserHistory.stage)
                    ,Constant.CustomerStage.cart,QueryConstants.EQUAL);
            FindQuery.delete(cart_tablename,criteria);
//            sendNotifcation(itr);
        }

//        String condition = " "+ Constant.UserHistory.stage +" = '"+ Constant.CustomerStage.cart +"';";
//        ResultSet resultdata = rest.executeQuery(Query.find(cart_tablename, condition));
//        Query.queryAddOrder(order_tablename, userid, stage, resultdata);
//        resultdata.beforeFirst();
//        decreaseProduct(cart_tablename, resultdata);
//        resultdata.beforeFirst();
//        rest.executeUpdate(Query.delete(cart_tablename,
//                Constant.UserHistory.stage,Constant.CustomerStage.cart));
//        sendNotifcation(resultdata);
    }
    public void updatecartquantity(String tablename, Map<String, String> payload) throws SQLException, DataAccessException {
        Criteria criteria = new Criteria(new Column(tablename, Constant.UserHistory.productid ),
                payload.get(Constant.UserHistory.productid), QueryConstants.EQUAL);
        FindQuery.update(tablename,Constant.UserHistory.quantity,
                payload.get(Constant.UserHistory.quantity),criteria);

//        String condition = " " + Constant.UserHistory.productid + " = '" + payload.get(Constant.UserHistory.productid) + "';";
//        String set = Constant.UserHistory.quantity + " = '" + payload.get(Constant.UserHistory.quantity);
//        rest.executeUpdate(Query.update(tablename, set, condition));
    }
    public void deleteProduct(String tablename, String orderid) throws SQLException, DataAccessException {
        Criteria criteria = new Criteria(new Column(tablename,Constant.UserHistory.productid),
                orderid,QueryConstants.EQUAL);
        FindQuery.delete(tablename,criteria);

//        rest.executeUpdate(Query.delete(tablename,Constant.UserHistory.productid,orderid));
    }
}
