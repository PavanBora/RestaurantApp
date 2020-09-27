package pavan.sample.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

import dmax.dialog.SpotsDialog;

public class AmountActivity extends AppCompatActivity {
    String date,Restname;
    private static final String HI ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php" ;
    SpotsDialog progressDialog;
    private static final String PREF_NAME = "pre";
    RecyclerView recyclerView;
    TextView resname,date22,price22;
    AmountAdapter orderDetailsAdapter;
    ArrayList<ItemModel> items= new ArrayList<>();
    int comm;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);
        recyclerView=findViewById(R.id.inforecy);
        resname=findViewById(R.id.resname);
        date22=findViewById(R.id.date);
        price22=findViewById(R.id.time);
        Intent i=getIntent();
        date=i.getStringExtra("date");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(date+" Report");
//        progressDialog = new SpotsDialog(AmountActivity.this, R.style.Custom);
//        progressDialog.show();
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        SharedPreferences preferences11=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences11.edit();
        comm=Integer.parseInt(preferences11.getString("comm",""));
        Restname=preferences11.getString("resname","");
        resname.setText(Restname);
        date22.setText(date);
        LinearLayoutManager lnl3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lnl3);
        getMovieData();
        recyclerView.setHasFixedSize(true);

    }
    private void getMovieData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int money=0,delmoney=0,tax=0;
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=array.length()-1; i>=0; i-- ){
                        JSONObject ob=array.getJSONObject(i);
                        if (ob.getString("restStatus").equals("2")&& ob.getString("restname").equals(Restname)&&ob.getString("date1").equals(date)) {
                            ItemModel product = new ItemModel(ob.getString("orderid"), ob.getString("totalprice"),ob.getString("delCharge") , ob.getString("date1"),ob.getString("taxes"));
                            items.add(product);
                            money=money+Integer.valueOf(ob.getString("totalprice"));
                            delmoney=delmoney+Integer.valueOf(ob.getString("delCharge"));
                            tax=tax+Integer.valueOf(ob.getString("taxes"));
                        }
                    }
                    orderDetailsAdapter = new AmountAdapter(AmountActivity.this, items);
                    orderDetailsAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(orderDetailsAdapter);
                    int tot=money-delmoney-tax;
                    tot=tot-tot*comm/100;
                    price22.setText("₹ "+String.valueOf(tot));
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
        super.onBackPressed();
//        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
//        startActivity(i);
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