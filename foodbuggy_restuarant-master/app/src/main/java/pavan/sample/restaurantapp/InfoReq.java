package pavan.sample.restaurantapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InfoReq extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://foodbuggy.in/FoodApp/Restaurant/AcceptDelivery.php";
    private Map<String, String> params;

    public InfoReq(String orderid, String phone, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("orderid", orderid);
        params.put("phone",phone);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
