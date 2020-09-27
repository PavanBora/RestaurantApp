package pavan.sample.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class MainActivity3 extends AppCompatActivity {
    private static final String PREF_NAME = "pre";
    private static final String TAG = PackageManager.class.getName();
    String resid,restname;
    int p=0,t=0,og=0,comm=0,pmoney=0,TOT=0;
    TextView ototal,opending,oongoing,atotal,apending;
    SpotsDialog progressDialog;
    private static final String HI3 ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php";
    private static final String HI2 ="https://foodbuggy.in/FoodApp/Restaurant/settlement.php";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = MainActivity3.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Logistics");
        ototal=findViewById(R.id.total);
        oongoing=findViewById(R.id.ongoing);
        opending=findViewById(R.id.pending);
        apending=findViewById(R.id.apending);
        atotal=findViewById(R.id.atotal);
        progressDialog = new SpotsDialog(MainActivity3.this, R.style.Custom);
        progressDialog.show();
        SharedPreferences preferences11=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences11.edit();
        restname=preferences11.getString("resname","");
        comm=Integer.parseInt(preferences11.getString("comm",""));
        getMovieData();
    }

    private void getMovieData1() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        if (ob.getString("resname").equals(restname)) {
                            pmoney=pmoney+Integer.valueOf(ob.getString("amount"));
                        }
                    }
                    apending.setText("Pending\n\n₹ "+String.valueOf(TOT-pmoney));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
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
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int money=0,delmoney=0,tax=0;
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        if (ob.getString("restname").equals(restname)) {
                            if (ob.getString("restStatus").equals("1")) {
                                og = og+ 1;
                            }
                            if (ob.getString("restStatus").equals("0")) {
                                p = p + 1;
                            }
                            t=t+1;
                            money=money+Integer.valueOf(ob.getString("totalprice"));
                            delmoney=delmoney+Integer.valueOf(ob.getString("delCharge"));
                            tax=tax+Integer.valueOf(ob.getString("taxes"));
                        }
                    }
                    oongoing.setText("Ongoing\n\n"+String.valueOf(og));
                    oongoing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getApplicationContext(),OrderHistory.class));
                        }
                    });
                    ototal.setText("Total\n\n"+String.valueOf(t));
                    ototal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getApplicationContext(),OrderHistory.class));
                        }
                    });
                    opending.setText("Pending\n\n"+String.valueOf(p));
                    opending.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    });
                    int tot=money-delmoney-tax;
                    tot=tot-tot*comm/100;
                    TOT=tot;
                    atotal.setText("Total\n\n₹ "+String.valueOf(tot));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getMovieData1();
                progressDialog.dismiss();
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
}