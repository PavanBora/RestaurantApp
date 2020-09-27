package pavan.sample.restaurantapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class ChangeStatus1 extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://foodbuggy.in/FoodApp/ChangeStatus1.php";
    private Map<String, String> params;
    public ChangeStatus1(String phone,String status, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("phone",phone);
        params.put("status",status);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


