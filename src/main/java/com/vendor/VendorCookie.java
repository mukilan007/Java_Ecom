package com.vendor;


import com.Constant;
import com.util.vendorSetCookie;
import org.json.simple.JSONArray;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/vendorcookies")
public class VendorCookie extends HttpServlet {
    public VendorCookie() {super();}
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        JSONArray categorys = new JSONArray();
        try {
            categorys = new VendorService(session).getAllCategory((String) session.getAttribute(Constant.Usersdata.userid));
        } catch (Throwable e) {
            response.sendError(401, "Unauthorized");
            throw new RuntimeException(e);
        }
        new vendorSetCookie().setcookie(response, categorys);
        System.out.println("cookie added");
        Cookie[] cookie1 = request.getCookies();
        String str = null;
        for(Cookie c: cookie1)
        {
            if(c.getName().equals("myproduct")){
                str = c.getValue();
            }
        }

        System.out.println(str);
        System.out.println(Base64.decode(str));
        System.out.println(Base64.decode(str).toString());
    }
}
