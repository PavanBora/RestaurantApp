package pavan.sample.restaurantapp;

import androidx.fragment.app.FragmentActivity;

public class Items1 {
    public String itemName;
    public String itemPrice;
    public String itemUrl;
    public String id;
    public String status;
//    private String offer;
//    private String best;
//    private String des;
//    private String vegStatus;


    public Items1(String itemName, String itemPrice, String itemUrl, String id, String status) {

        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemUrl = itemUrl;
        this.id = id;
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
