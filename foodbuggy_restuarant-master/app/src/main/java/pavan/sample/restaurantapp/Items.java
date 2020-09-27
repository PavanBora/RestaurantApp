package pavan.sample.restaurantapp;

import androidx.fragment.app.FragmentActivity;

public class Items {
    public String itemName;
    public String itemPrice;
    public String itemUrl;
    public String quantity;
    public int comm;

    public Items(String itemName, String itemPrice, String itemUrl, String quantity,int comm) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemUrl = itemUrl;
        this.quantity = quantity;
        this.comm=comm;
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

    public void setCom(int comm){ this.comm=comm; }

    public int getComm(){ return comm; }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
