package com.db;

import java.sql.Connection;

public class ProxyDBConnection implements DBConnection{
    private static RealDBConnection real = null;
    public Connection getcon() {
        return real.getcon();
    }

    public void connectToDatabase() {
        if(real == null){
            synchronized(RESTOperation.class) {
                if(real == null) {
                    real = new RealDBConnection();
                }
            }
        }
        real.connectToDatabase();
    }
}
