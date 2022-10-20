package com.util;

import org.json.simple.JSONArray;
//import org.json.JSONArray;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class vendorSetCookie {

    public void setcookie (HttpServletResponse response, JSONArray payload){
        Cookie cookie = new Cookie("myproduct",
                Base64.encode(payload.toString().getBytes()).toString());
        response.addCookie(cookie);
    }

}
