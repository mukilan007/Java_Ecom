package com.vendor;

import com.adventnet.persistence.DataAccessException;
import com.db.RESTOperation;
import org.json.simple.JSONArray;

import java.sql.SQLException;

public interface Strategy {
    JSONArray getProduct(String product_tablename, String deliver_tablename, String stage, String value)
            throws SQLException, DataAccessException;
}
