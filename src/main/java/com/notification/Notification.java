package com.notification;

import java.util.HashMap;
import java.util.Map;

public class Notification {
    Emailnotification emailnotification;
    SMSnotification smsnotification;
    Map<String, String> map = new HashMap<String, String>();
    private Notification(){}
    public Notification(Emailnotification emailnotification, SMSnotification smsnotification) {
        this.smsnotification =smsnotification;
        this.emailnotification = emailnotification;
    }

    public void add(String product_id,String vendor_id) {
        map.put(product_id,vendor_id);
    }

    public void dataChange() {
        emailnotification.sendMessage(map);
        smsnotification.sendMessage(map);
    }
}
