package com;

public class Constant {
    public static class VendorStage {               //TODO: change packed, order, shipped to order constant class.
        public static final String packed = "packed";
        public static final String ordered = "ordered";
        public static final String shipped = "shipped";

        public static final String delivered = "delivered";
        public static final String cancel = "cancel";

    }
    public static class CustomerStage {
        public static final String cart = "cart";
    }
    public static class DataBaseName {
        public static final String Eco = "Eco";
    }
    public static class DataBase_UserTableName {
        public static final String DBUserdata = "UsersData";
        public static final String DBProductdata = "GlobalProductsData";
        public static final String Categorydata = "ListOfCateory";
        public static final String OrderDetail = "OrderDetail";
        public static final String OrderHistory = "OrderHistory";
    }
    public static class Usersdata {
        public static final String userid = "user_id";
        public static final String name = "name";
        public static final String password = "password";
        public static final String dateofbirth = "date_of_birth";
        public static final String email = "e_mail";
        public static final String address = "address";
        public static final String phone = "phone";
        public static final String isadmin = "is_admin";
        public static final String createdat = "created_at";
        public static final String lastcheckin = "last_check_in";
        public static final String isdeleted = "is_deleted";
    }
    public static class DataBase_Gobal_Products {
        public static final String productid = "product_id";
        public static final String vendorid = "vendor_id";
        public static final String product_name = "product_name";
        public static final String detail = "detail";
        public static final String quantity = "quantity";
        public static final String size = "size";
        public static final String color = "color";
        public static final String brand_name = "brand_name";
        public static final String price = "price";
        public static final String categoryname = "category_name";
        public static final String type = "type";
    }
    public static class AllCategory {
        public static final String categoryid = "cateory_id";
        public static final String categoryname = "cateory_name";
    }
    public static class UserHistory {
        public static final String productid = "product_id";
        public static final String vendorid = "vendor_id";
        public static final String quantity = "quantity";
        public static final String stage = "stage";
        public static final String createdAt = "created_at";
        public static final String completedAt = "completed_at";
//        public static final String status = "status";
    }
    public static class OrderDetail {
        public static final String id = "_id";
        public static final String productid = "product_id";
        public static final String vendorid = "vendor_id";
        public static final String customerid = "customer_id";
        public static final String stage = "stage";
        public static final String quantity = "quantity";
        public static final String createdAt = "created_at";
        public static final String completedAt = "completed_at";
    }
}
