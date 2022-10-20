package com.Login;

import com.adventnet.authentication.AAAUSER;
import com.adventnet.authentication.AAALOGIN;
import com.adventnet.authentication.AAAACCOUNT;
import com.adventnet.authentication.AAAPASSWORD;
import com.adventnet.authentication.AAAACCPASSWORD;
import com.adventnet.authentication.AAAACCOWNERPROFILE;
import com.adventnet.authentication.AAAAUTHORIZEDROLE;
import com.adventnet.authentication.PasswordException;
import com.adventnet.authentication.util.AuthUtil;
import com.adventnet.db.persistence.metadata.MetaDataException;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.*;


import com.Constant;
import com.db.Query;
import com.db.RESTOperation;
import com.mickey.curdoperation.FindQuery;
import com.util.Accountmanagement;
import com.util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class LoginService {
    private RESTOperation rest = null;
    private final Accountmanagement accountmanagement = new Accountmanagement();
    public LoginService(){
        rest = RESTOperation.getInstance();
    }
    public boolean SignIn(HttpServletRequest request, Map<String, String> payload) throws SQLException, DataAccessException {
        List<String> list = new ArrayList<>();
        String tablename = Constant.DataBase_UserTableName.DBUserdata;

        Criteria criteria = new Criteria(new Column(tablename, Constant.Usersdata.email),
                payload.get(Constant.Usersdata.email), QueryConstants.EQUAL);
        DataObject dataObject = FindQuery.find(tablename, criteria);
        if (!dataObject.isEmpty())
        {
            Iterator itr = dataObject.getRows(tablename);
            if (itr.hasNext()) {
                Row row = (Row) itr.next();
                LOGGER.log(Level.INFO, "MUKILAN :: Row SignIn " + row);
                list.add(Integer.toString((Integer) row.get(Constant.Usersdata.userid)));
                list.add((String) row.get(Constant.Usersdata.name));
                list.add((String) row.get(Constant.Usersdata.email));
                list.add((String) row.get(Constant.Usersdata.password));
                list.add(Boolean.toString((Boolean) row.get(Constant.Usersdata.isadmin)));
                list.add(Boolean.toString((Boolean) row.get(Constant.Usersdata.isdeleted)));
            }
        }
        LOGGER.log(Level.INFO, "MUKILAN :: criteria SignIn " + criteria);
        LOGGER.log(Level.INFO, "MUKILAN :: dataObject SignIn " + dataObject);
        LOGGER.log(Level.INFO, "MUKILAN :: List SignIn" + Arrays.toString(list.toArray()));

//        String condition = " "+ Constant.Usersdata.email +" = '"+ payload.get(Constant.Usersdata.email) +"';";
//        ResultSet resultdata = rest.executeQuery(Query.find(tablename, condition));
//        try {
//            while (resultdata.next()) {
//                list.add(resultdata.getString(Constant.Usersdata.userid));
//                list.add(resultdata.getString(Constant.Usersdata.name));
//                list.add(resultdata.getString(Constant.Usersdata.email));
//                list.add(resultdata.getString(Constant.Usersdata.password));
//                list.add(resultdata.getString(Constant.Usersdata.isadmin));
//                list.add(resultdata.getString(Constant.Usersdata.isdeleted));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e);
//        }
        if(list.size() > 0) {
            byte[] decodedBytes = Base64.getDecoder().decode(list.get(3));
            String decodedString = new String(decodedBytes);
            if (list.get(5).equals("false") && !(list.get(2).equals(payload.get(Constant.Usersdata.email))
                    && decodedString.equals(payload.get(Constant.Usersdata.password))))
                return false;
        }
        else {
            return false;
        }

        HttpSession session = request.getSession();
        session.setAttribute(Constant.Usersdata.userid, list.get(0));
        session.setAttribute(Constant.Usersdata.name, list.get(1));
        session.setAttribute(Constant.Usersdata.isadmin, list.get(4));
        LOGGER.log(Level.INFO,"MUKILAN :: Logger Name: SignIn "+list.get(1));

        criteria = new Criteria(new Column(tablename, Constant.Usersdata.email),
                payload.get(Constant.Usersdata.email), QueryConstants.EQUAL);
        FindQuery.update(tablename, Constant.Usersdata.lastcheckin,
                String.valueOf(accountmanagement.getTimeNow()),criteria);

//        condition = " " + Constant.Usersdata.email + " = '" + payload.get(Constant.Usersdata.email) + "';";
//        String set = Constant.Usersdata.lastcheckin + " = '" + String.valueOf(accountmanagement.getTimeNow());
//        rest.executeUpdate(Query.update(Constant.DataBase_UserTableName.DBUserdata, set, condition));

        return true;
    }

    public boolean SignUp(HttpServletRequest request, Map<String, String> payload, String admin) throws SQLException, DataAccessException, MetaDataException {
        String usertablename = Constant.DataBase_UserTableName.DBUserdata;

        Criteria criteria = new Criteria(new Column(usertablename, Constant.Usersdata.email),
                payload.get(Constant.Usersdata.email), QueryConstants.EQUAL);
        DataObject dataObject = FindQuery.find(usertablename, criteria);
        LOGGER.log(Level.INFO, "MUKILAN :: criteria sign up" + criteria);
        LOGGER.log(Level.INFO, "MUKILAN :: dataObject sign up" + dataObject);
        LOGGER.log(Level.INFO, "MUKILAN :: dataObject is empty sign up " + dataObject.isEmpty());
        if (dataObject.isEmpty())
        {
            payload.put(Constant.Usersdata.isadmin,admin);
            payload.put(Constant.Usersdata.createdat,String.valueOf(accountmanagement.getCreatedAt()));
            payload.put(Constant.Usersdata.lastcheckin,String.valueOf(accountmanagement.getTimeNow()));
            payload.put(Constant.Usersdata.isdeleted,String.valueOf(accountmanagement.getDeleted()));
            String encodedString = Base64.getEncoder().encodeToString(payload.get("password").getBytes());
            payload.put("encrypt-password",encodedString);
           LOGGER.log(Level.INFO, "MUKILAN :: return dataObject sign up"
                   + FindQuery.queryAddUser(Constant.DataBase_UserTableName.DBUserdata, payload));

            if (SignIn(request, payload)) {
                HttpSession session = request.getSession(false);
                String tablename = Constant.DataBase_UserTableName.OrderHistory
                        + String.valueOf(session.getAttribute(Constant.Usersdata.userid));
                new Validation().checkUserHistoryTable(tablename);
            }
            return true;
        }
    return false;

//        String condition = " "+ Constant.Usersdata.email +" = '"+ payload.get(Constant.Usersdata.email) +"';";
//        ResultSet resultdata = rest.executeQuery(Query.find(Constant.DataBase_UserTableName.DBUserdata, condition));
//        if(!resultdata.next()) {
//            payload.put(Constant.Usersdata.isadmin, admin);
//            payload.put(Constant.Usersdata.createdat, String.valueOf(accountmanagement.getCreatedAt()));
//            payload.put(Constant.Usersdata.lastcheckin, String.valueOf(accountmanagement.getTimeNow()));
//            payload.put(Constant.Usersdata.isdeleted, String.valueOf(accountmanagement.getDeleted()));
//            String encodedString = Base64.getEncoder().encodeToString(payload.get("password").getBytes());
//            payload.put("encrypt-password", encodedString);
//            rest.executeUpdate(Query.queryAddUser(Constant.DataBase_UserTableName.DBUserdata, payload));

//            if (SignIn(request, payload)) {
//                HttpSession session = request.getSession(false);
//                String tablename = Constant.DataBase_UserTableName.OrderHistory
//                      + String.valueOf(session.getAttribute(Constant.Usersdata.userid));
//                if (rest.checkTable(tablename)) {
//                    rest.createTable(Query.CreateUserHistoryTable(tablename));
//                }
//            }
//            return true;
//        }


//        String loginName = "";
//        String firstName = "";
//        String middleName = "";
//        String lastName = "";
//        String serviceName = "";
//        String accAdminProfile = "";
//        String password = "";
//        String passwordProfile = "";
//        try
//        {
//            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
//            DataObject dobj = persistence.constructDataObject();
//            Row userRow = new Row(AAAUSER.TABLE);
//            userRow.set(AAAUSER.FIRST_NAME, firstName);
//            userRow.set(AAAUSER.MIDDLE_NAME, middleName); // optional
//            userRow.set(AAAUSER.LAST_NAME, lastName); // optional
//            dobj.addRow(userRow);
//
//            Row loginRow = new Row(AAALOGIN.TABLE);
//            loginRow.set(AAALOGIN.NAME, loginName);
//            dobj.addRow(loginRow);
//
//            Row accRow = new Row(AAAACCOUNT.TABLE);
//            accRow.set(AAAACCOUNT.SERVICE_ID, AuthUtil.getServiceId(serviceName));
//            accRow.set(AAAACCOUNT.ACCOUNTPROFILE_ID, AuthUtil.getAccountProfileId(accAdminProfile));
//            dobj.addRow(accRow);
//
//            Row passwordRow = new Row(AAAPASSWORD.TABLE);
//            passwordRow.set(AAAPASSWORD.PASSWORD, password);
//            passwordRow.set(AAAPASSWORD.PASSWDPROFILE_ID, AuthUtil.getPasswordProfileId(passwordProfile));
//            dobj.addRow(passwordRow);
//
//            Row accPassRow = new Row(AAAACCPASSWORD.TABLE);
//            accPassRow.set(AAAACCPASSWORD.ACCOUNT_ID, accRow.get(AAAACCOUNT.ACCOUNT_ID));
//            accPassRow.set(AAAACCPASSWORD.PASSWORD_ID, passwordRow.get(AAAPASSWORD.PASSWORD_ID));
//            dobj.addRow(accPassRow);
//
//// to assign roles - optional
//            Row authRoleRow1 = new Row(AAAAUTHORIZEDROLE.TABLE);
//            authRoleRow1.set(AAAAUTHORIZEDROLE.ACCOUNT_ID, accRow.get(AAAACCOUNT.ACCOUNT_ID));
//            authRoleRow1.set(AAAAUTHORIZEDROLE.ROLE_ID, AuthUtil.getRoleId(role1));
//            dobj.addRow(authRoleRow1);
//
//            Row authRoleRow2 = new Row(AAAAUTHORIZEDROLE.TABLE);
//            authRoleRow2.set(AAAAUTHORIZEDROLE.ACCOUNT_ID, accRow.get(AAAACCOUNT.ACCOUNT_ID));
//            authRoleRow2.set(AAAAUTHORIZEDROLE.ROLE_ID, AuthUtil.getRoleId(role2));
//            dobj.addRow(authRoleRow2);
//
//// to permit this user to create another user - optional
//            int noOfSubAccounts = -1; // -1 to create unlimited users, 0 - for no user, n - to create n user accounts
//            Row accOwnerProfileRow = new Row(AAAACCOWNERPROFILE.TABLE);
//            accOwnerProfileRow.set(AAAACCOWNERPROFILE.ACCOUNT_ID, accRow.get(AAAACCOUNT.ACCOUNT_ID));
//            accOwnerProfileRow.set(AAAACCOWNERPROFILE.ALLOWED_SUBACCOUNT, noOfSubAccounts);
//            dobj.addRow(accOwnerProfileRow);
//
//            AuthUtil.createUserAccount(dobj);
//        }
//        catch(PasswordException pe)
//        {
//            pe.printStackTrace();
//        }
//        catch(DataAccessException dae)
//        {
//            dae.printStackTrace();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        retrun false
    }
}
