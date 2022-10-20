package com.customer;

import com.adventnet.persistence.DataAccessException;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@WebServlet("/product")
public class HomePage extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONArray categorys = new JSONArray();
        HttpSession session = request.getSession(false);
        try {
            categorys = new CustomerService(session).getAllCategory();
            System.out.println(categorys.toString());
            LOGGER.log(Level.INFO, "MUKILAN :: List of category's to view in UI" + categorys);
            PrintWriter out = response.getWriter();
            out.print(categorys);
        } catch (SQLException e) {
            response.sendError(401, "SQL Error");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SessionException e) {
            response.sendError(401, "Unauthorized");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            response.sendError(401, "DataAccess");
            throw new RuntimeException(e);
        }
    }
}
