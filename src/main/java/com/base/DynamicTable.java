package com.base;


import com.adventnet.db.persistence.metadata.*;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;

import java.sql.SQLException;

public class DynamicTable {
    public static void createOrderHistory(String tablename) throws MetaDataException, SQLException, DataAccessException {
        TableDefinition tableDefinition = new TableDefinition(false);
        tableDefinition.setTableName(tablename);

        ColumnDefinition col1 = new ColumnDefinition();
        col1.setTableName(tablename);
        col1.setColumnName("_id");
        col1.setDataType("INTEGER");
        col1.setNullable(false);
//        col1.setKey(true);
        UniqueValueGeneration uniqueValueGeneration = new UniqueValueGeneration();
        uniqueValueGeneration.setGeneratorName(tablename+"._id");
        col1.setUniqueValueGeneration(uniqueValueGeneration);
        tableDefinition.addColumnDefinition(col1);

        ColumnDefinition col2 = new ColumnDefinition();
        col2.setTableName(tablename);
        col2.setColumnName("product_id");
        col2.setDataType("BIGINT");
        col2.setNullable(true);
        tableDefinition.addColumnDefinition(col2);

        ColumnDefinition col3 = new ColumnDefinition();
        col3.setTableName(tablename);
        col3.setColumnName("vendor_id");
        col3.setDataType("BIGINT");
        col3.setNullable(true);
        tableDefinition.addColumnDefinition(col3);

        ColumnDefinition col4 = new ColumnDefinition();
        col4.setTableName(tablename);
        col4.setColumnName("quantity");
        col4.setDataType("BIGINT");
        col4.setNullable(true);
        tableDefinition.addColumnDefinition(col4);

        ColumnDefinition col5 = new ColumnDefinition();
        col5.setTableName(tablename);
        col5.setColumnName("stage");
        col5.setDataType("CHAR");
        col5.setMaxLength(100);
        col5.setNullable(true);
        tableDefinition.addColumnDefinition(col5);

        ColumnDefinition col6 = new ColumnDefinition();
        col6.setTableName(tablename);
        col6.setColumnName("created_at");
        col6.setDataType("TIMESTAMP");
        col6.setDefaultValue(null);
        tableDefinition.addColumnDefinition(col6);

        ColumnDefinition col7 = new ColumnDefinition();
        col7.setTableName(tablename);
        col7.setColumnName("completed_at");
        col7.setDataType("TIMESTAMP");
        col7.setDefaultValue(null);
        tableDefinition.addColumnDefinition(col7);

        PrimaryKeyDefinition primaryKeyDefinition = new PrimaryKeyDefinition();
        primaryKeyDefinition.setName(tablename+"_PK");
        primaryKeyDefinition.setTableName(tablename);
        primaryKeyDefinition.addColumnName("_id");
        tableDefinition.setPrimaryKey(primaryKeyDefinition);

        DataAccess.createTable("TotalTable", tableDefinition);
    }
}
