package pavan.sample.restaurantapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ResetReq extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://foodbuggy.in/FoodApp/rest/Reset.php";
    private Map<String, String> params;

    public ResetReq(String phone, String pass1, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("phone",phone);
        params.put("pass1", pass1);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
