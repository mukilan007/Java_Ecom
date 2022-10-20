package com.db;

import com.Constant;

public class Schema {
    private String table_name = null;

    private Schema(){}
    public Schema(String table_name){
        this.table_name = table_name;
    }

    public String createUserTable(){
        return "create table "+table_name+" ("+
                Constant.Usersdata.userid+" serial PRIMARY KEY," +
                Constant.Usersdata.name+" VARCHAR(50) NOT NULL," +
                Constant.Usersdata.password+" VARCHAR(50) NOT NULL," +
                Constant.Usersdata.dateofbirth+" DATE NOT NULL," +
                Constant.Usersdata.email+" VARCHAR(255) UNIQUE NOT NULL," +
                Constant.Usersdata.address+" VARCHAR(200) NOT NULL," +
                Constant.Usersdata.phone+" BIGINT NOT NULL," +
                Constant.Usersdata.isadmin+" BOOLEAN NOT NULL," +
                Constant.Usersdata.createdat+" TIMESTAMP," +
                Constant.Usersdata.lastcheckin+" TIMESTAMP," +
                Constant.Usersdata.isdeleted+" BOOLEAN NOT NULL" +
                ");";
    }

    public String createListOfCategory() {
        return "create table "+table_name+" (" +
                Constant.AllCategory.categoryid +" serial NOT NULL, " +
                Constant.AllCategory.categoryname +" VARCHAR(100) NOT NULL PRIMARY KEY" +
                ");";
    }

    public String createGobalProduct() {
        return "create table "+table_name+" (" +
                Constant.DataBase_Gobal_Products.productid+" serial PRIMARY KEY," +
                Constant.DataBase_Gobal_Products.vendorid+" BIGINT NOT NULL," +
                Constant.DataBase_Gobal_Products.product_name+" VARCHAR(100) NOT NULL," +
                Constant.DataBase_Gobal_Products.categoryname+" VARCHAR(100) NOT NULL," +
                Constant.DataBase_Gobal_Products.detail+" VARCHAR(250) NOT NULL," +
                Constant.DataBase_Gobal_Products.quantity+" SMALLINT NOT NULL," +
                Constant.DataBase_Gobal_Products.size+" VARCHAR(50) NOT NULL," +
                Constant.DataBase_Gobal_Products.color+" VARCHAR(50) NOT NULL," +
                Constant.DataBase_Gobal_Products.brand_name+" VARCHAR(50) NOT NULL," +
                Constant.DataBase_Gobal_Products.price+" BIGINT NOT NULL," +
                Constant.DataBase_Gobal_Products.type+" VARCHAR(100) NOT NULL" +
                ");";
    }

    public String createUserHistory() {
        return "create table "+table_name+" (" +
                Constant.UserHistory.productid+" BIGINT NOT NULL," +
                Constant.UserHistory.vendorid+" BIGINT NOT NULL," +
                Constant.UserHistory.quantity+" BIGINT NOT NULL," +
                Constant.UserHistory.stage+" VARCHAR(100) NOT NULL," +
                Constant.UserHistory.createdAt+" TIMESTAMP default NULL," +
                Constant.UserHistory.completedAt+" TIMESTAMP default NULL" +
                ");";
    }
    public String createOrderDetail() {
        return "create table "+table_name+" (" +
                Constant.OrderDetail.id+" serial PRIMARY KEY," +
                Constant.OrderDetail.productid+" BIGINT NOT NULL," +
                Constant.OrderDetail.vendorid+" BIGINT NOT NULL," +
                Constant.OrderDetail.customerid+" BIGINT NOT NULL," +
                Constant.OrderDetail.quantity+" BIGINT NOT NULL," +
                Constant.OrderDetail.stage+" VARCHAR(100) NOT NULL," +
                Constant.OrderDetail.createdAt+" TIMESTAMP default NULL," +
                Constant.OrderDetail.completedAt+" TIMESTAMP default NULL" +
                ");";
    }
}
