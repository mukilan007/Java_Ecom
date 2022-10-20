package com.vendor;

import com.Constant;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.db.Query;
import com.db.RESTOperation;
import com.mickey.curdoperation.FindQuery;
import com.util.ResultSettoJSON;
import org.json.simple.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Delivered implements Strategy{
    private static RESTOperation rest = null;
    public Delivered(){
        rest = RESTOperation.getInstance();
    }
    public JSONArray getProduct(String product_tablename, String deliver_tablename, String stage, String value)
            throws SQLException, DataAccessException {
        Criteria criteria1 = new Criteria(new Column(deliver_tablename, Constant.UserHistory.stage ),
                value, QueryConstants.EQUAL);
        SelectQuery dataObject = FindQuery.findorder(product_tablename, deliver_tablename, criteria1);
        return new ResultSettoJSON().ProductTable(stage, dataObject);

//        String condition = " "+ stage +" = '"+ value +"';";
//        ResultSet resultdata = rest.executeQuery(Query.findcart(product_tablename, deliver_tablename, condition));
//        return new ResultSettoJSON().ProductTable(resultdata, value);
    }
}
