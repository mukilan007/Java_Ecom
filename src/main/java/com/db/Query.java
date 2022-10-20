package com.db;

import com.Constant;

import java.sql.*;
import java.util.Map;

public class Query {
    protected static Connection connection = null;
    public Query() {
        DBConnection dbConnection = new ProxyDBConnection();
        dbConnection.connectToDatabase();
        connection = dbConnection.getcon();
    }

    private static PreparedStatement preparedStatement = null;
    public static String CreateCategoryTable(String table_name) {
        return new Schema(table_name).createListOfCategory();
    }
    public static String CreateUserHistoryTable(String table_name) {
        return new Schema(table_name).createUserHistory();
    }
    public static String CreateOrderDetail(String table_name) {
        return new Schema(table_name).createOrderDetail();
    }


    public static String find(String table_name,String condition) throws SQLException {
        String find  = "select * from "+ table_name +" where"+condition ;
        preparedStatement = connection.prepareStatement(find);
        System.out.println("find  " + String.valueOf(preparedStatement));
        return String.valueOf(preparedStatement);
    }

    public static String findall(String table_name){
        String find  = "select * from "+ table_name;
        try {
            preparedStatement = connection.prepareStatement(find);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(preparedStatement);
    }

    public static String findorder(String table_name1, String table_name2, String condition) {
        String find  = "SELECT "+
                table_name2 +"."+ Constant.OrderDetail.id+ "," +
                table_name2 + "."+ Constant.OrderDetail.productid+ "," +
                table_name2 + "."+ Constant.OrderDetail.vendorid+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.product_name+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.brand_name+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.color+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.size+ "," +
                table_name2 +"."+ Constant.OrderDetail.quantity+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.price+
                " FROM "+ table_name1 +" INNER JOIN "+ table_name2 +
                " ON "+ table_name1 +"."+ Constant.DataBase_Gobal_Products.productid +"=" +
                table_name2 +"."+ Constant.OrderDetail.productid +
                " WHERE"+ condition ;
        System.out.println("findorder  " + find);
        return find;
    }
    public static String findcart(String table_name1, String table_name2, String condition) {
        String find  = "SELECT "+
                table_name2 + "."+ Constant.OrderDetail.productid+ "," +
                table_name2 + "."+ Constant.OrderDetail.vendorid+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.product_name+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.brand_name+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.color+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.size+ "," +
                table_name2 +"."+ Constant.OrderDetail.quantity+ "," +
                table_name1 +"."+ Constant.DataBase_Gobal_Products.price+
                " FROM "+ table_name1 +" INNER JOIN "+ table_name2 +
                " ON "+ table_name1 +"."+ Constant.DataBase_Gobal_Products.productid +" = " +
                table_name2 +"."+ Constant.OrderDetail.productid +
                " WHERE"+ condition ;
        System.out.println("findcart  " + find);
        return find;
//        table_name2 +"."+ Constant.OrderDetail.productid +" AND " +
//                Constant.OrderDetail.customerid +" = " +user_id+
    }

    public static String queryAddUser(String table_name, Map<String, String> payload){
        try {
            String add = "insert into "+table_name+" (name,password,date_of_birth,e_mail,address,phone,is_admin," +
                    "created_at,last_check_in,is_deleted) values(?,?,?,?,?,?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(add);
            preparedStatement.setString(1, payload.get(Constant.Usersdata.name));
            preparedStatement.setString(2, payload.get("encrypt-password"));
            preparedStatement.setString(3, payload.get(Constant.Usersdata.dateofbirth));
            preparedStatement.setString(4, payload.get(Constant.Usersdata.email));
            preparedStatement.setString(5, payload.get(Constant.Usersdata.address));
            preparedStatement.setString(6, payload.get(Constant.Usersdata.phone));
            preparedStatement.setString(7, payload.get(Constant.Usersdata.isadmin));
            preparedStatement.setString(8, payload.get(Constant.Usersdata.createdat));
            preparedStatement.setString(9, payload.get(Constant.Usersdata.lastcheckin));
            preparedStatement.setString(10, payload.get(Constant.Usersdata.isdeleted));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return String.valueOf(preparedStatement);
    }


    public static String queryAddProduct(String table_name,String vendor_id, Map<String, String> payload) {
        try {
            String add = "insert into "+table_name+" (vendor_id,product_name,category_name,detail,quantity,size,color," +
                    "brand_name,price,type) values(?,?,?,?,?,?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(add);
            preparedStatement.setString(1, vendor_id);
            preparedStatement.setString(2, payload.get("productname"));
            preparedStatement.setString(3, payload.get("categoryname"));
            preparedStatement.setString(4, payload.get("detail"));
            preparedStatement.setString(5, payload.get("quantity"));
            preparedStatement.setString(6, payload.get("size"));
            preparedStatement.setString(7, payload.get("color"));
            preparedStatement.setString(8, payload.get("brandname"));
            preparedStatement.setString(9, payload.get("price"));
            preparedStatement.setString(10, payload.get("type"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("AddProduct => "+ String.valueOf(preparedStatement));
        return String.valueOf(preparedStatement);
    }

    public static String queryAddCategory(String table_name, String payload) {
        try {
            String add = "insert into "+table_name+" (cateory_name) values (lower(?));";
            preparedStatement = connection.prepareStatement(add);
            preparedStatement.setString(1, payload);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return String.valueOf(preparedStatement);
    }
    public static String queryAddCart(String table_name, Map<String, String> payload) {
        try {
            String add = "insert into "+table_name+
                    " (product_id, vendor_id, quantity, stage, created_at, completed_at) values (?,?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(add);
            preparedStatement.setString(1, payload.get(Constant.UserHistory.productid));
            preparedStatement.setString(2, payload.get(Constant.UserHistory.vendorid));
            preparedStatement.setString(3, payload.get(Constant.UserHistory.quantity));
            preparedStatement.setString(4, payload.get(Constant.UserHistory.stage));
            preparedStatement.setString(5, payload.get(Constant.UserHistory.createdAt));
            preparedStatement.setString(6, payload.get(Constant.UserHistory.completedAt));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return String.valueOf(preparedStatement);
    }
    public static void queryAddOrder(String table_name,String userid, String stage, ResultSet payload) throws SQLException {
        String add = "insert into "+table_name+
                " (product_id, vendor_id, customer_id, quantity, stage, created_at) values (?,?,?,?,?,?);";
        preparedStatement = connection.prepareStatement(add);
        while(payload.next()){
            preparedStatement.setInt(1, Integer.parseInt(payload.getString(Constant.OrderDetail.productid)));
            preparedStatement.setInt(2, Integer.parseInt(payload.getString(Constant.OrderDetail.vendorid)));
            preparedStatement.setInt(3, Integer.parseInt(userid));
            preparedStatement.setInt(4, Integer.parseInt(payload.getString(Constant.OrderDetail.quantity)));
            preparedStatement.setString(5, stage);
            preparedStatement.setTimestamp(6, Timestamp.valueOf(payload.getString(Constant.OrderDetail.createdAt)));
            preparedStatement.addBatch();
        }
        System.out.println("queryAddOrder  " + String.valueOf(preparedStatement));
        preparedStatement.executeBatch();
    }
    public static void updateorder(String cart_tablename, String order_tablename, ResultSet payload) throws SQLException {
        String update  = "UPDATE " +
                order_tablename +" SET " +
                Constant.DataBase_Gobal_Products.quantity +" = " +
                order_tablename +"."+ Constant.DataBase_Gobal_Products.quantity +" - ? FROM " +
                cart_tablename +" WHERE " +
                cart_tablename +"."+ Constant.UserHistory.productid +" = " +
                order_tablename +"."+ Constant.OrderDetail.productid +";";
        preparedStatement = connection.prepareStatement(update);
        while(payload.next()) {
            preparedStatement.setInt(1, Integer.parseInt(payload.getString(Constant.OrderDetail.quantity)));
            preparedStatement.addBatch();
        }
        System.out.println("updateorder  " + String.valueOf(preparedStatement));
        preparedStatement.executeBatch();
    }
    public static String queryAddOrder_history(String table_name, ResultSet payload, String completedAt, String stage)
            throws SQLException {
        String add = "insert into "+table_name+
                " (product_id, vendor_id, quantity, stage, created_at, completed_at) values (?,?,?,?,?,?);";
        while(payload.next()) {
            preparedStatement = connection.prepareStatement(add);
            preparedStatement.setString(1, payload.getString(Constant.OrderDetail.productid));
            preparedStatement.setString(2, payload.getString(Constant.OrderDetail.vendorid));
            preparedStatement.setString(3, payload.getString(Constant.OrderDetail.quantity));
            preparedStatement.setString(4, stage);
            preparedStatement.setString(5, payload.getString(Constant.OrderDetail.createdAt));
            preparedStatement.setString(6, completedAt);
        }
        System.out.println("queryAddOrder  " + String.valueOf(preparedStatement));
//        preparedStatement.executeBatch();
        return (String.valueOf(preparedStatement));
    }

    public static String update(String table_name,String set, String condition) {
        try {
            String update = "update "+ table_name +" set " +
                    set +"' where" +
                    condition;
            preparedStatement = connection.prepareStatement(update);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("update  " + String.valueOf(preparedStatement));
        return String.valueOf(preparedStatement);
    }
    public static String delete(String table_name,String Condition, String value) {
        try {
            String update = "delete from "+ table_name +" where "+ Condition +"='" + value + "';";
            preparedStatement = connection.prepareStatement(update);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("delete  " + String.valueOf(preparedStatement));
        return String.valueOf(preparedStatement);
    }
}
