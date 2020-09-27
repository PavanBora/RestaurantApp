package pavan.sample.restaurantapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class OrderInfo1 extends AppCompatActivity {
    TextView orderid,restname,date,price,address,status;
    Button accept;
    String id,lat,lon,phoneNo,userphone,Restname;;
    SpotsDialog progressDialog;
    private RecyclerView mRecyclerView;
    ItemAdapter itemAdapter;
    int comm;
    List<Items> list = new ArrayList<>();
    private static final String PREF_NAME = "pre";
    private static final String HI ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php" ;
    private static final String HI1="https://foodbuggy.in/FoodApp/viewcheckout.php" ;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        orderid=findViewById(R.id.orderid);
        restname=findViewById(R.id.restname);
        date=findViewById(R.id.date);
        accept=findViewById(R.id.accept);
        mRecyclerView = findViewById(R.id.recycler);
        accept.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Info");
        SharedPreferences preferences11=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences11.edit();
        phoneNo=preferences11.getString("phone","");
        Restname=preferences11.getString("resname","");
        comm= Integer.parseInt(preferences11.getString("comm",""));
        Intent i=getIntent();
        id=i.getStringExtra("orderid");
//        progressDialog = new SpotsDialog(OrderInfo1.this, R.style.Custom);
//        progressDialog.show();
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        LinearLayoutManager lnlGrid = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(lnlGrid);
        getMovieData1();
        getMovieData();
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }
    private void getMovieData1() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        if ((ob.getString("orderid").equals(id)) && (ob.getString("phone").equals(userphone)) && ob.getString("restname").equals(Restname)) {
                            Items category = new Items(ob.getString("itemname"), ob.getString("itemprice"), ob.getString("url"), ob.getString("quantity"),comm);
                            list.add(category);
                        }
                    }
                    itemAdapter = new ItemAdapter(OrderInfo1.this, list);
                    mRecyclerView.setAdapter(itemAdapter);
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

    private void getMovieData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=array.length()-1; i>=0; i-- ){
                        JSONObject ob=array.getJSONObject(i);
                        if (ob.getString("orderid").equals(id) && ob.getString("restname").equals(Restname)){
                            userphone=ob.getString("phone");
                            date.setText(ob.getString("date"));
                            orderid.setText(ob.getString("orderid"));
                            restname.setText(ob.getString("restname"));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i=new Intent(getApplicationContext(),OrderHistory.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void submitForm()
    {
        progressDialog = new SpotsDialog(OrderInfo1.this, R.style.Custom);
        progressDialog.show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        i.putExtra("orderid",id);
                        startActivity(i);
                        progressDialog.dismiss();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderInfo1.this);
                        builder.setMessage("Not Accepted!!")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        InfoReq registerRequest = new InfoReq(id, phoneNo,responseListener);
        RequestQueue queue = Volley.newRequestQueue(OrderInfo1.this);
        queue.add(registerRequest);
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