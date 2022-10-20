package com.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RESTOperation extends Query{
    private static volatile RESTOperation rest = null;
    private RESTOperation(){
        super();
    }
    public static RESTOperation getInstance(){
        if(rest == null){
            synchronized(RESTOperation.class) {
                if(rest == null) {
                    rest = new RESTOperation();
                }
            }
        }
        return rest;
    }

    public void createTable(String query){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("table created");
    }
    public boolean checkTable(String tableName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) "
                    + "from information_schema.tables "
                    + "where table_name = ? "
                    + "limit 1;");
            preparedStatement.setString(1, tableName);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) == 0;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }
    public void createUserTable(String table_name){
       createTable(new Schema(table_name).createUserTable());
    }
    public void createProductTable(String table_name){
        createTable(new Schema(table_name).createGobalProduct());
    }

    public void executeUpdate(String Query) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(Query);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("Operation Success");
    }

    public ResultSet executeQuery(String Query){
        Statement statement = null;
        ResultSet resultdata = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            resultdata = statement.executeQuery(Query);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        return resultdata;
    }
}