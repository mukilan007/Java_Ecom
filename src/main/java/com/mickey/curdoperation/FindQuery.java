package com.mickey.curdoperation;

import com.Constant;
import com.adventnet.ds.query.*;

import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


public class FindQuery {
    public static void createUser() throws SQLException, DataAccessException {
        try {
            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
            Row row = new Row (Constant.DataBase_UserTableName.DBUserdata);
            row.set(Constant.Usersdata.name, "xxx");
            row.set(Constant.Usersdata.password, "xxx");
            row.set(Constant.Usersdata.dateofbirth, "xxx");
            row.set(Constant.Usersdata.email, "xxx@gmail.com");
            row.set(Constant.Usersdata.address, "xxx");
            row.set(Constant.Usersdata.phone, "123456789");
            row.set(Constant.Usersdata.isadmin, "true");
            row.set(Constant.Usersdata.createdat, "2022-10-01 00:19:12.705");
            row.set(Constant.Usersdata.lastcheckin, "2022-10-01 00:19:12.705");
            row.set(Constant.Usersdata.isdeleted, "false");

            DataObject dataObject = persistence.constructDataObject();

//        persistence.add(dataObject);

            dataObject.addRow(row);
            DataAccess.add(dataObject);

//        DataObject d=new WritableDataObject();
//        d.addRow(row);
//        DataAccess.add(d);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static DataObject find(String table_name, Criteria condition) throws DataAccessException {
        LOGGER.log(Level.INFO, "MUKILAN :: Called find");
        return DataAccess.get(table_name, condition);
    }
    public static DataObject findall(String tablename) throws DataAccessException {
        LOGGER.log(Level.INFO, "MUKILAN :: Called findall");
//        SelectQuery selectQuery = QueryConstructor.get(table_name, Column.getColumn(CLASS.TABLE,"*"));
        SelectQuery selectQuery = QueryConstructor.get(tablename,"*");
        return DataAccess.get(selectQuery);
//        if (!dataObject.isEmpty()) {
//            Iterator itr = dataObject.getRows(table_name);
//            while (itr.hasNext()) {
//                Row row = (Row) itr.next();
//                String tabName = (String) row.get(Constant.AllCategory.categoryname);
//                LOGGER.info("Existing row taken from DB : "+row);
//                LOGGER.info("Existing row category name taken from DB : "+tabName);
//            }
//        }

//        SelectQuery query = new SelectQueryImpl(Table.getTable(table_name));
//        query.addSelectColumn(Column.getColumn(table_name, "*"));
    }
    public static SelectQuery findorder(String tablename1, String tablename2, Criteria condition)
            throws DataAccessException {
        LOGGER.log(Level.INFO, "MUKILAN :: Called findorder");
        SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(tablename1));

        Column c1 = new Column(tablename2,Constant.OrderDetail.productid);
        Column c2 = new Column(tablename2,Constant.OrderDetail.vendorid);
        Column c3 = new Column(tablename1,Constant.DataBase_Gobal_Products.product_name);
        Column c4 = new Column(tablename1,Constant.DataBase_Gobal_Products.brand_name);
        Column c5 = new Column(tablename1,Constant.DataBase_Gobal_Products.color);
        Column c6 = new Column(tablename1,Constant.DataBase_Gobal_Products.size);
        Column c7 = new Column(tablename2,Constant.UserHistory.quantity);
        Column c8 = new Column(tablename1,Constant.DataBase_Gobal_Products.price);
        Column c9 = new Column(tablename2,Constant.OrderDetail.id);
        selectQuery.addSelectColumn(c1);
        selectQuery.addSelectColumn(c2);
        selectQuery.addSelectColumn(c3);
        selectQuery.addSelectColumn(c4);
        selectQuery.addSelectColumn(c5);
        selectQuery.addSelectColumn(c6);
        selectQuery.addSelectColumn(c7);
        selectQuery.addSelectColumn(c8);
        selectQuery.addSelectColumn(c9);

//        ArrayList<Column> colList = new ArrayList<>();
//        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.product_name));
//        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.brand_name));
//        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.color));
//        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.size));
//        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.price));
//        colList.add(new Column(tablename2,Constant.OrderDetail.id));
//        colList.add(new Column(tablename2,Constant.OrderDetail.productid));
//        colList.add(new Column(tablename2,Constant.OrderDetail.vendorid));
//        colList.add(new Column(tablename2,Constant.OrderDetail.quantity));
//        selectQuery.addSelectColumns(colList);
//        selectQuery.addSelectColumn(Column.getColumn(null, "*"));
        Join join = new Join(tablename1, tablename2, new  String[]{Constant.DataBase_Gobal_Products.productid}
                , new String[]{Constant.OrderDetail.productid}, Join.INNER_JOIN);
        selectQuery.addJoin(join);
        selectQuery.setCriteria(condition);

//        return DataAccess.get(selectQuery);
        return selectQuery;
    }
    public static SelectQuery findcart(String tablename1, String tablename2, Criteria condition)
            throws DataAccessException {
        LOGGER.log(Level.INFO, "MUKILAN :: Called findcart");
        SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(tablename1));
        Join  join = new Join(Table.getTable(tablename1), Table.getTable(tablename2), new  String[]{Constant.DataBase_Gobal_Products.productid}
                , new String[]{Constant.OrderDetail.productid}, Join.INNER_JOIN);

//        Criteria criteria = new Criteria(new Column(tablename1,Constant.DataBase_Gobal_Products.productid),
//                new Column(tablename2,Constant.OrderDetail.productid),QueryConstants.EQUAL);
//        Join  join = new Join(tablename1, tablename2, criteria, Join.INNER_JOIN);

        selectQuery.addJoin(join);

        selectQuery.setCriteria(condition);

//        Column c1 = new Column(tablename2,Constant.UserHistory.productid);
//        Column c2 = new Column(tablename2,Constant.UserHistory.vendorid);
//        Column c3 = new Column(tablename1,Constant.DataBase_Gobal_Products.product_name);
//        Column c4 = new Column(tablename1,Constant.DataBase_Gobal_Products.brand_name);
//        Column c5 = new Column(tablename1,Constant.DataBase_Gobal_Products.color);
//        Column c6 = new Column(tablename1,Constant.DataBase_Gobal_Products.size);
//        Column c7 = new Column(tablename2,Constant.UserHistory.quantity);
//        Column c8 = new Column(tablename1,Constant.DataBase_Gobal_Products.price);
//        selectQuery.addSelectColumn(c1);
//        selectQuery.addSelectColumn(c2);
//        selectQuery.addSelectColumn(c3);
//        selectQuery.addSelectColumn(c4);
//        selectQuery.addSelectColumn(c5);
//        selectQuery.addSelectColumn(c6);
//        selectQuery.addSelectColumn(c7);
//        selectQuery.addSelectColumn(c8);

        ArrayList<Column> colList = new ArrayList<>();
        colList.add(new Column(tablename2,Constant.OrderDetail.productid));
        colList.add(new Column(tablename2,Constant.OrderDetail.vendorid));
        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.product_name));
        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.brand_name));
        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.color));
        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.size));
        colList.add(new Column(tablename2,Constant.OrderDetail.quantity));
        colList.add(new Column(tablename1,Constant.DataBase_Gobal_Products.price));
        selectQuery.addSelectColumns(colList);
        return selectQuery;

//        Persistence persistence = null;
//        try {
//            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
//            DataObject dataObject = persistence.get(selectQuery);
////            return dataObject;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//        selectQuery.addSelectColumn(Column.getColumn(null, "*"));
//        return DataAccess.get(selectQuery);
    }
    public static DataObject queryAddUser(String tablename, Map<String, String> payload) {
        LOGGER.log(Level.INFO, "MUKILAN :: Called queryAddUser");
        try {
            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
            DataObject dataObject = persistence.constructDataObject();

            Row row = new Row (tablename);
            row.set(Constant.Usersdata.name, payload.get(Constant.Usersdata.name));
            row.set(Constant.Usersdata.password, payload.get("encrypt-password"));
            row.set(Constant.Usersdata.dateofbirth, payload.get(Constant.Usersdata.dateofbirth));
            row.set(Constant.Usersdata.email, payload.get(Constant.Usersdata.email));
            row.set(Constant.Usersdata.address, payload.get(Constant.Usersdata.address));
            row.set(Constant.Usersdata.phone, payload.get(Constant.Usersdata.phone));
            row.set(Constant.Usersdata.isadmin, payload.get(Constant.Usersdata.isadmin));
            row.set(Constant.Usersdata.createdat, payload.get(Constant.Usersdata.createdat));
            row.set(Constant.Usersdata.lastcheckin, payload.get(Constant.Usersdata.lastcheckin));
            row.set(Constant.Usersdata.isdeleted, payload.get(Constant.Usersdata.isdeleted));
            dataObject.addRow(row);

            return DataAccess.add(dataObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static DataObject queryAddProduct(String tablename,String vendor_id, Map<String, String> payload) {
        LOGGER.log(Level.INFO, "MUKILAN :: Called queryAddProduct");
        try {
            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
            DataObject dataObject = persistence.constructDataObject();

            Row row = new Row (tablename);
            row.set(Constant.DataBase_Gobal_Products.vendorid, vendor_id);
            row.set(Constant.DataBase_Gobal_Products.product_name, payload.get("productname"));
            row.set(Constant.DataBase_Gobal_Products.categoryname, payload.get("categoryname").toLowerCase());
            row.set(Constant.DataBase_Gobal_Products.detail, payload.get("detail"));
            row.set(Constant.DataBase_Gobal_Products.quantity, payload.get("quantity"));
            row.set(Constant.DataBase_Gobal_Products.size, payload.get("size"));
            row.set(Constant.DataBase_Gobal_Products.color, payload.get("color"));
            row.set(Constant.DataBase_Gobal_Products.brand_name, payload.get("brandname"));
            row.set(Constant.DataBase_Gobal_Products.price, payload.get("price"));
            row.set(Constant.DataBase_Gobal_Products.type, payload.get("type"));
            dataObject.addRow(row);

            return DataAccess.add(dataObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static void queryAddCategory(String tablename, String payload) {
        LOGGER.log(Level.INFO, "MUKILAN :: Called queryAddCategory");
        try {
            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
            DataObject dataObject = persistence.constructDataObject();

            Row row = new Row(tablename);
            row.set(Constant.AllCategory.categoryname, payload);
            dataObject.addRow(row);

            DataAccess.add(dataObject);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    public static DataObject queryAddCart(String tablename, Map<String, String> payload) {
        LOGGER.log(Level.INFO, "MUKILAN :: Called queryAddCart");
        try {
            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
            DataObject dataObject = persistence.constructDataObject();

            Row row = new Row(tablename);
            row.set(Constant.UserHistory.productid, payload.get(Constant.UserHistory.productid));
            row.set(Constant.UserHistory.vendorid, payload.get(Constant.UserHistory.vendorid));
            row.set(Constant.UserHistory.quantity, payload.get(Constant.UserHistory.quantity));
            row.set(Constant.UserHistory.stage, payload.get(Constant.UserHistory.stage));
            row.set(Constant.UserHistory.createdAt, payload.get(Constant.UserHistory.createdAt));
            row.set(Constant.UserHistory.completedAt, payload.get(Constant.UserHistory.completedAt));
            dataObject.addRow(row);

            return DataAccess.add(dataObject);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public static void queryAddOrder_history(String tablename1,String tablename2, DataObject payload,
                                             String completedAt, String stage){
        LOGGER.log(Level.INFO, "MUKILAN :: Called queryAddOrder_history");
        try {
            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
            DataObject dataObject = persistence.constructDataObject();

            Row row = new Row(tablename1);
            if (!payload.isEmpty()) {
                java.util.Iterator itr = payload.getRows(tablename2);
                if(itr.hasNext()) {
                    Row row1 = (Row) itr.next();
                    row.set(Constant.UserHistory.productid, row1.get(Constant.UserHistory.productid));
                    row.set(Constant.UserHistory.vendorid, row1.get(Constant.UserHistory.vendorid));
                    row.set(Constant.UserHistory.quantity, row1.get(Constant.UserHistory.quantity));
                    row.set(Constant.UserHistory.stage, stage);
                    row.set(Constant.UserHistory.createdAt, row1.get(Constant.UserHistory.createdAt));
                    row.set(Constant.UserHistory.completedAt, completedAt);
                }
            }
            dataObject.addRow(row);

            DataAccess.add(dataObject);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public static void queryAddOrder(String ordertable, String carttable,String userid, String stage, DataObject dataObject){
        LOGGER.log(Level.INFO, "MUKILAN :: Called queryAddOrder");
        try {
            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
            DataObject DO = persistence.constructDataObject();
//            Object flag = null;
            if (!dataObject.isEmpty()) {
                java.util.Iterator itr = dataObject.getRows(carttable);
                while(itr.hasNext()) {
//                    synchronized (flag) {
                        Row row1 = (Row) itr.next();
                        Row row = new Row(ordertable);
                        DataAccess.generateValues(row);
                        LOGGER.log(Level.INFO, "MUKILAN :: Row1 of queryAddOrder " + row1);
                        row.set(Constant.OrderDetail.productid, row1.get(Constant.OrderDetail.productid));
                        row.set(Constant.OrderDetail.vendorid, row1.get(Constant.OrderDetail.vendorid));
                        row.set(Constant.OrderDetail.customerid, userid);
                        row.set(Constant.OrderDetail.quantity, row1.get(Constant.OrderDetail.quantity));
                        row.set(Constant.OrderDetail.stage, stage);
                        row.set(Constant.OrderDetail.createdAt, row1.get(Constant.OrderDetail.createdAt));
                        DO.addRow(row);
//                    }
                }
            }
            DataAccess.add(DO);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public static DataObject updateProductQuantity(String cart_tablename, String product_tablename, DataObject payload)
            throws DataAccessException {
        LOGGER.log(Level.INFO, "MUKILAN :: Called updateorder");

        if (!payload.isEmpty()) {
            DataObject dataObject = null;
            java.util.Iterator iterator = payload.getRows(cart_tablename);
            while(iterator.hasNext()) {
                Row row = (Row) iterator.next();
                Criteria criteria = new Criteria(new Column(product_tablename, Constant.DataBase_Gobal_Products.productid),
                        row.get(Constant.OrderDetail.productid), QueryConstants.EQUAL);
                dataObject = DataAccess.get(product_tablename, criteria);
                LOGGER.log(Level.INFO, "MUKILAN :: dataObject of updateorder " + dataObject);

                java.util.Iterator itr = dataObject.getRows(product_tablename);
                while (itr.hasNext()) {
                    Row row1 = (Row) itr.next();
                    LOGGER.log(Level.INFO, "MUKILAN :: Row of updateorder before update " + row1);

//                    Column column = Column.createOperation(Operation.operationType.SUBTRACT,
//                            Column.getColumn(product_tablename, Constant.DataBase_Gobal_Products.quantity),
//                            row.get(Constant.OrderDetail.quantity));
//                    LOGGER.log(Level.INFO, "MUKILAN :: Subtract column value of updateorder" + column);

                    int value = ((Long) row1.get(Constant.DataBase_Gobal_Products.quantity)).intValue() -
                            ((Long) row.get(Constant.OrderDetail.quantity)).intValue();
                    row1.set(Constant.DataBase_Gobal_Products.quantity, value);
                    LOGGER.log(Level.INFO, "MUKILAN :: Row of updateorder after update " + row1);
                    dataObject.updateRow(row1);
                }
            }
            if(dataObject != null)
                return DataAccess.update(dataObject);
        }
        return null;
    }
    public static void update(String tablename,String set, String value, Criteria condition)
            throws DataAccessException {
        LOGGER.log(Level.INFO, "MUKILAN :: Called update");
        DataObject dataObject = DataAccess.get(tablename, condition);
        Row row = dataObject.getRow(tablename);
        row.set(set, value);
        dataObject.updateRow(row);
        DataAccess.update(dataObject);
//        if (!dataObject.isEmpty()) {
//            Iterator itr = dataObject.getRows(tablename);
//            while (itr.hasNext()) {
//                Row row1 = (Row) itr.next();
//                String tabName = (String) row1.get("column name");
//                row1.set("column name","value");
//                dataObject.updateRow(row1);
//            }
//            DataAccess.update(dataObject);
//        }
    }

    public static void delete(String tablename,Criteria condition) throws DataAccessException {
        LOGGER.log(Level.INFO, "MUKILAN :: Called delete");
        DataObject dataObject = DataAccess.get(tablename, condition);
        LOGGER.log(Level.INFO, "MUKILAN :: Delete rows from specific table" + dataObject);
        if (!dataObject.isEmpty()) {
            dataObject.deleteRows(tablename, (Criteria) null);
            DataAccess.update(dataObject);
        }
    }
}
