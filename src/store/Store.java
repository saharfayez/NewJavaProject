package store;

public class Store {

    private String storeCode;
    private String storeName;
    private Integer storeId;

    public Store(String storeCode, String storeName, Integer storeId) {
        this.storeCode = storeCode;
        this.storeName = storeName;
        this.storeId = storeId;
    }
    public Store (String storeCode, String storeName) {
        this.storeCode = storeCode;
        this.storeName = storeName;
    }

    public Store() {

    }


    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
