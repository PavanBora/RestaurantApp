package pavan.sample.restaurantapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ForgotReq extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://foodbuggy.in/FoodApp/rest/Forgot.php";
    private Map<String, String> params;

    public ForgotReq(String phone, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("phone", phone);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
