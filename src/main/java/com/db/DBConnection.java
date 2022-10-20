package com.db;

import java.sql.Connection;

interface DBConnection {
    Connection getcon();
    void connectToDatabase();
}
