package com.util;

import java.sql.Timestamp;

public class Accountmanagement {
    public Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    public Timestamp getCreatedAt(){
        return timestamp;
    }
    public Timestamp getTimeNow(){
        return timestamp;
    }
    public boolean getDeleted(){
        return false;
    }
}
