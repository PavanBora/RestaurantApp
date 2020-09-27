package pavan.sample.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;
    SpotsDialog progressDialog;
    private static final String PREF_NAME = "pre";
    String Restname;
    OrderDetailsAdapter orderDetailsAdapter;
    List<OrderDetails> list= new ArrayList<>();
    private Context mContext = MainActivity.this;
    String comm;
    private ShimmerFrameLayout mShimmerViewContainer;
    private static final String HI ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Orders");
        recyclerView=findViewById(R.id.recycler);
        editText=findViewById(R.id.search);
//        progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
//        progressDialog.show();
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        LinearLayoutManager lnl3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lnl3);
        getMovieData();
        recyclerView.setHasFixedSize(true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        SharedPreferences preferences11=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences11.edit();
        Restname=preferences11.getString("resname","");
        comm=preferences11.getString("comm","");

    }
    public void filter(String text) {
        ArrayList<OrderDetails> filteredlist1 = new ArrayList<>();
        for (OrderDetails item : list) {
            if(item.getDate().toLowerCase().contains(text.toLowerCase())) {
                filteredlist1.add(item);
            }
            else if(item.getOrderid().toLowerCase().contains(text.toLowerCase())) {
                filteredlist1.add(item);
            }
            else if(item.getRestname().toLowerCase().contains(text.toLowerCase())) {
                filteredlist1.add(item);
            }
        }

        orderDetailsAdapter.filterlist(filteredlist1);
    }

    private void getMovieData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        if (ob.getString("restStatus").equals("0") && ob.getString("restname").equals(Restname)) {
                            OrderDetails product = new OrderDetails(ob.getString("date"), ob.getString("orderid"), ob.getString("restname"), ob.getString("totalprice"),ob.getString("restStatus"),ob.getString("delCharge"),ob.getString("taxes"));
                            list.add(product);
                        }
                    }
                    orderDetailsAdapter = new OrderDetailsAdapter(MainActivity.this, list,comm);
                    orderDetailsAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(orderDetailsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                progressDialog.dismiss();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onClickCalled1(String anyValue){
        Intent i=new Intent(getApplicationContext(),OrderInfo.class);
        i.putExtra("orderid",anyValue);
        startActivity(i);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}