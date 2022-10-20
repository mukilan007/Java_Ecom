package com.util;

import com.Constant;
import com.adventnet.db.persistence.metadata.MetaDataException;
import com.base.DynamicTable;
import com.db.Query;
import com.db.RESTOperation;
import com.mickey.curdoperation.FindQuery;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class Validation {
    private static RESTOperation rest = null;
    private String categoryname;
    public Validation(){
        rest = RESTOperation.getInstance();
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public void checkCategory() throws SQLException, DataAccessException {
        String tablename =Constant.DataBase_UserTableName.Categorydata;
        Criteria criteria = new Criteria(new Column(tablename, Constant.AllCategory.categoryname),
                categoryname, QueryConstants.EQUAL);
        DataObject dataObject = FindQuery.find(tablename, criteria);
        LOGGER.log(Level.INFO,"MUKILAN :: criteria of checkCategory " + criteria);
        LOGGER.log(Level.INFO,"MUKILAN :: dataObject of checkCategory " + dataObject);
        if(dataObject.isEmpty()) {
            tablename = Constant.DataBase_UserTableName.Categorydata;
            LOGGER.log(Level.INFO,"MUKILAN :: Adding new category to ListOfCategory Table " + categoryname);
            FindQuery.queryAddCategory(tablename, categoryname.toLowerCase());
        }


//        String condition = " "+ Constant.AllCategory.categoryname +" = LOWER('"+ categoryname +"');";
//        ResultSet resultdata = rest.executeQuery(Query.find(Constant.DataBase_UserTableName.Categorydata, condition));
//        try {
//            while(resultdata.next()){
//                list.add(resultdata.getString(Constant.AllCategory.categoryname));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println(e);
//        }
//        if (list.size() == 0)
//            rest.executeUpdate(Query.queryAddCategory(Constant.DataBase_UserTableName.Categorydata, categoryname));
    }

    public void checkUserHistoryTable(String tablename) throws SQLException, MetaDataException, DataAccessException {
//        if(rest.checkTable(tablename)) {
//            rest.createTable(Query.CreateUserHistoryTable(tablename));
            DynamicTable.createOrderHistory(tablename);
//        }
    }
}
