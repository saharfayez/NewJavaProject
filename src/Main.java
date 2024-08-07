import connection.ConnectionManager;
import general.GeneralManager;
import item.Item;
import item.ItemManager;
import store.Store;
import store.StoreManager;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
        Connection connection = ConnectionManager.getConnection();

        GeneralManager<Object> generalManager = new GeneralManager<>(connection);
        Item itemObject = new Item("555" , "irir");
        generalManager.insert(itemObject);
        //generalManager.select(itemObject);

        StoreManager storeManager = new StoreManager(connection);
        ItemManager itemManager = new ItemManager(connection);

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Stock Management Application!");
        System.out.println("Choose which operation you would like to perform : insert , select , delete , modify");
        String operationName = input.next();
        String entityName, storeNameValue, storeCodeValue, itemNameValue, itemCodeValue;
        switch (operationName) {
            case "select": {
                System.out.println("Please enter the entity name : store or item");
                entityName = input.next();
                switch (entityName) {
                    case "store": {
                        System.out.println("Please enter the store code");
                        String storeCode = input.next();
                        Store store = storeManager.findStore(storeCode);
                        System.out.println("The Store Id Value : " + store.getStoreId());
                        System.out.println("The Store Name Value : " + store.getStoreName());
                        System.out.println("The Store Code Value : " + store.getStoreCode());
                        break;
                    }
                    case "item": {
                        System.out.println("Search by code or name ?");
                        String searchValue = input.next();
                        switch (searchValue) {
                            case "code": {
                                System.out.println("Please enter the item code");
                                String itemCode = input.next();
                                Item item = itemManager.findItem(itemCode);
                                System.out.println("The Item Id Value : " + item.getItemId());
                                System.out.println("The Item Name Value : " + item.getItemName());
                                System.out.println("The Item Code Value : " + item.getItemCode());
                                break;
                            }
                            case "name": {
                                System.out.println("Please enter the item name");
                                String itemName = input.next();
                                List<Item> items = itemManager.findItemsByNameContains(itemName);
                                for (Item item : items) {
                                    System.out.println("The Item Id Value : " + item.getItemId());
                                    System.out.println("The Item Name Value : " + item.getItemName());
                                    System.out.println("The Item Code Value : " + item.getItemCode());
                                    System.out.println();
                                }
                                break;
                            }
                        }
                        break;
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
                        storeCodeValue = input.next();
                        storeManager.insertStore(storeNameValue, storeCodeValue);
                        break;
                    }
                    case "item": {
                        System.out.println("Please enter the item name , item code");
                        itemNameValue = input.next();
                        itemCodeValue = input.next();
                        itemManager.insertItem(itemNameValue, itemCodeValue);
                        break;
                    }
                }
                break;
            }
        }
    }
}
