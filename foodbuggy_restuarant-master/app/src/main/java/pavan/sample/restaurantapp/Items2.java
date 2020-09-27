package pavan.sample.restaurantapp;


public class Items2 implements Comparable<Items2>{

    private String itemName;
    private String itemPrice;
    private String status;
    private String id;
    private String url;
    private String offer;
    private String best;
    private String des;
    private String vegStatus;
    private  boolean isSectionHeader;

    public Items2(String itemName, String itemPrice, String status, String id, String url, String offer, String best, String des,String vegStatus) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.status = status;
        this.id = id;
        this.url = url;
        this.offer = offer;
        this.best = best;
        this.des = des;
        this.vegStatus=vegStatus;
        isSectionHeader = false;
    }

    public String getVegStatus() {
        return vegStatus;
    }

    public void setVegStatus(String vegStatus) {
        this.vegStatus = vegStatus;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getBest() {
        return best;
    }

    public void setBest(String best) {
        this.best = best;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
    public boolean isSectionHeader() {
        return isSectionHeader;
    }

    @Override
    public int compareTo(Items2 itemModel) {
        return this.des.compareTo(itemModel.des);
    }

    public void setToSectionHeader() {
        isSectionHeader = true;
    }
}
