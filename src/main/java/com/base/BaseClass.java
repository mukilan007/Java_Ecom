package com.base;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class BaseClass {
    public Map<String, String> getPayload(HttpServletRequest request){

//        JSONObject jsonObject=new JSONObject(request.getParameter("payload"));
//        String data = jsonObject.get("data").toString();

        String payload = request.getParameter("payload");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> maps = null;
        try {
            maps = mapper.readValue(payload, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maps;
    }
}
