package com.util;

import com.Constant;
import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.QueryConstructionException;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class ResultSettoJSON {
    public JSONArray ProductTable(String type, SelectQuery selectQuery) {
        RelationalAPI relApi = RelationalAPI.getInstance();
        Connection conn = null;
        DataSet ds = null;
        try
        {
            JSONArray productjson = new JSONArray();
            conn = relApi.getConnection();
            ds = relApi.executeQuery(selectQuery, conn);
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
                if(type.equals(Constant.VendorStage.ordered)) {
                    productdetails.put(Constant.OrderDetail.id,
                            ds.getAsString(9));
                }
                productjson.add(productdetails);
            }
            LOGGER.log(Level.INFO," productjson dataset " + productjson);
            return productjson;
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

//        JSONArray productjson = new JSONArray();
//        try {
//            Iterator itr = dataObject.getRows(tablename);
//            while(itr.hasNext()){
//                Row row = (Row) itr.next();
//                JSONObject productdetails =new JSONObject();
//                if(type.equals(Constant.VendorStage.ordered)) {
//                    productdetails.put(Constant.OrderDetail.id,
//                            row.get(Constant.OrderDetail.id));
//                }
//                productdetails.put(Constant.OrderDetail.productid,
//                        row.get(Constant.OrderDetail.productid));
//                productdetails.put(Constant.OrderDetail.vendorid,
//                        row.get(Constant.OrderDetail.vendorid));
//                productdetails.put(Constant.DataBase_Gobal_Products.product_name,
//                        row.get(Constant.DataBase_Gobal_Products.product_name));
//                productdetails.put(Constant.DataBase_Gobal_Products.brand_name,
//                        row.get(Constant.DataBase_Gobal_Products.brand_name));
//                productdetails.put(Constant.DataBase_Gobal_Products.color,
//                        row.get(Constant.DataBase_Gobal_Products.color));
//                productdetails.put(Constant.DataBase_Gobal_Products.size,
//                        row.get(Constant.DataBase_Gobal_Products.size));
//                productdetails.put(Constant.OrderDetail.quantity,
//                        row.get(Constant.OrderDetail.quantity));
//                productdetails.put(Constant.DataBase_Gobal_Products.price,
//                        row.get(Constant.DataBase_Gobal_Products.price));
//                productjson.add(productdetails);
//            }
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
//        return productjson;
    }
    public JSONArray TableListOfCategory(String tablename, DataObject dataObject)
            throws SQLException, DataAccessException {
        JSONArray categoryslist = new JSONArray();
        Iterator itr = dataObject.getRows(tablename);
        while(itr.hasNext()){
            Row row = (Row) itr.next();
            JSONObject categorydetails =new JSONObject();
            categorydetails.put(Constant.AllCategory.categoryid,
                    row.get(Constant.AllCategory.categoryid));
            categorydetails.put(Constant.AllCategory.categoryname,
                    row.get(Constant.AllCategory.categoryname));

            categoryslist.add(categorydetails);
        }
        return categoryslist;
    }
}
