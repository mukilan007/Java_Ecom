package com.db;

import com.Constant;

import java.sql.Connection;
import java.sql.DriverManager;

public class RealDBConnection implements DBConnection{
    Connection con = null;
    public Connection getcon() {
        return con;
    }

    public void connectToDatabase() {
       String dbName = Constant.DataBaseName.Eco;
       try{
           Class.forName("org.postgresql.Driver");
           con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/" + dbName,"postgres","admin");
//           if(con != null){
//               System.out.println("Connected to DB");
//           }else {
//               System.out.println("Connection Failed");
//           }
       }
       catch (Exception e){
           e.getStackTrace();
       }
    }
}

