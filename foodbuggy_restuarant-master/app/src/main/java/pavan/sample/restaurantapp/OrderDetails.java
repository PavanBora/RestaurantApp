package pavan.sample.restaurantapp;


public class OrderDetails {
    public String date;
    public String orderid;
    public String restname;
    public String totalprice;
    public String restStatus;
    public String delprice;
    public String taxes;

    public OrderDetails(String date, String orderid, String restname, String totalprice, String restStatus,String delprice,String taxes) {
        this.date = date;
        this.orderid = orderid;
        this.restname = restname;
        this.totalprice = totalprice;
        this.restStatus = restStatus;
        this.delprice=delprice;
        this.taxes=taxes;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getRestname() {
        return restname;
    }

    public void setRestname(String restname) {
        this.restname = restname;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setDelprice(String delprice) {
        this.delprice = delprice;
    }

    public String getDelprice() {
        return delprice;
    }

    public  void setTaxes(String taxes){ this.taxes=taxes;}

    public String getTaxes(){ return taxes; }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getRestStatus() {
        return restStatus;
    }

    public void setRestStatus(String restStatus) {
        this.restStatus = restStatus;
    }
}