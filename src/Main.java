import java.sql.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            StoreManager storeManager = new StoreManager();
            Store store = storeManager.select(storeCode);
            System.out.println("The Store Id Value : " + store.getStoreId());
            System.out.println("The Store Name Value : " + store.getStoreName());
            System.out.println("The Store Code Value : " + store.getStoreCode());

            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/StockManagementSchema", "abstract-programmer", "example-password");
            PreparedStatement state = con.prepareStatement("insert into Store (storeName , storeCode) values (?,?)");
            ResultSet resultSet;
            Scanner input = new Scanner(System.in);
            System.out.println("Welcome to Stock Management Application!");
            System.out.println("Choose which operation you would like to perform : insert , select , delete , modify");

            String operationName = input.next();
            String entityName, storeNameValue, storeCodeValue, itemNameValue, itemCodeValue;
            int storeIdValue, itemIdValue;

            switch (operationName) {
                case "select": {
                    System.out.println("Please enter the entity name : store or item");
                    entityName = input.next();
                    switch (entityName) {
                        case "store": {
                            resultSet = state.executeQuery("select storeCode from Store ");
                            System.out.println("These are the available store code ");
                            while (resultSet.next()) {
                                System.out.print(resultSet.getString("storeCode") + "   ");
                                System.out.println();
                            }

                            System.out.println("Please enter the store codes");
                            int storeCode = input.nextInt();
                            resultSet = state.executeQuery("select * from Store where storeCode = " + storeCode);
                            while (resultSet.next()) {
                                System.out.println("The Store Id Value : " + resultSet.getInt("storeId"));
                                System.out.println("The Store Name Value : " + resultSet.getString("storeName"));
                                System.out.println("The Store Code Value : " + resultSet.getString("storeCode"));
                                break;

                            }
                            break;
                        }
                        case "item": {
                            resultSet = state.executeQuery("select itemCode from Item ");
                            System.out.println("These are the available item codes ");
                            while (resultSet.next()) {
                                System.out.print(resultSet.getString("itemCode") + "   ");
                                System.out.println();
                            }
                            System.out.println("Please enter the item code");
                            String itemCode = input.next();
                            String query = "select * from Item where itemCode = '" + itemCode + "'";
                            System.out.println(query);
                            resultSet = state.executeQuery(query);
                            while (resultSet.next()) {
                                System.out.println("The Item Id Value : " + resultSet.getInt("itemId"));
                                System.out.println("The Item Name Value : " + resultSet.getString("itemName"));
                                System.out.println("The Item Code Value : " + resultSet.getString("itemCode"));
                                break;
                            }
                        }
                    }
                    break;
                }
                case "insert": {
                    System.out.println("Please enter the entity name : store or item");
                    entityName = input.next();
                    switch (entityName) {
                        case "store": {
                            System.out.println("Please enter the store name , store code");

                            storeNameValue = input.next();
                            input.nextLine();
                            storeCodeValue = input.nextLine();
                            String query_st = "INSERT INTO Store(storeName , storeCode) VALUES ('" + storeNameValue + "', '" + storeCodeValue + "');";
                            System.out.println(query_st);
                            state.setString(1, storeNameValue);
                            state.setString(2, storeCodeValue);
                            state.execute(query_st);
                            break;
                        }
                        case "item": {
                            System.out.println("Please enter the item name , item code");

                            itemNameValue = input.next();
                            itemCodeValue = input.next();
                            String query_st = "INSERT INTO Item (itemName, itemCode) VALUES ('" + itemNameValue + "', '" + itemCodeValue + "');";
                            state.execute(query_st);
                            break;

                        }
                    }
                    break;
                }
            }


//            Statement state = con.createStatement();
//            ResultSet resultSet = state.executeQuery("select * from Store");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt("storeId"));
//                System.out.println(resultSet.getString("storeName"));
//                System.out.println(resultSet.getString("storeCode"));
//
//            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}