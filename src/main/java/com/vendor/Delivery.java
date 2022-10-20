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
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class Delivery implements Strategy{
    private static RESTOperation rest = null;
    public Delivery(){
        rest = RESTOperation.getInstance();
    }
    public JSONArray getProduct(String product_tablename, String deliver_tablename, String stage, String value)
            throws DataAccessException {
        Criteria criteria1 = new Criteria(new Column(deliver_tablename, Constant.UserHistory.stage ),
                stage, QueryConstants.EQUAL);
        criteria1 = criteria1.and(new Criteria(Column.getColumn(deliver_tablename, Constant.UserHistory.vendorid)
                , value, QueryConstants.EQUAL));
        SelectQuery dataObject = FindQuery.findorder(product_tablename, deliver_tablename, criteria1);
        LOGGER.log(Level.INFO, "MUKILAN :: Result of delivery" + dataObject);
        return new ResultSettoJSON().ProductTable(stage, dataObject);

//        String condition = " "+ Constant.UserHistory.stage +" = '"+ stage +"'" +
//                " and "+ deliver_tablename+"."+ Constant.UserHistory.vendorid +" = '"+ value +"';";
//        ResultSet resultdata = rest.executeQuery(Query.findorder(product_tablename, deliver_tablename, condition));
//        return new ResultSettoJSON().ProductTable(resultdata, stage);
    }
}
