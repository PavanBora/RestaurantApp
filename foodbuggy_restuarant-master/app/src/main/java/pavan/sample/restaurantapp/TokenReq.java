package pavan.sample.restaurantapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class TokenReq extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://foodbuggy.in/FoodApp/rest/StoreToken.php";
    private Map<String, String> params;
    public TokenReq(String phone,String token, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("phone", phone);
        params.put("token",token);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


