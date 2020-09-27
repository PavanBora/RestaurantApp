package pavan.sample.restaurantapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class ChangeStatus extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://foodbuggy.in/FoodApp/ChangeStatus.php";
    private Map<String, String> params;
    public ChangeStatus(String restid,String status,String name, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id",restid);
        params.put("status",status);
        params.put("name",name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


