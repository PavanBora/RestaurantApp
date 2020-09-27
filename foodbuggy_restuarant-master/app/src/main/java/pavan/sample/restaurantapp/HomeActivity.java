package pavan.sample.restaurantapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String PREF_NAME = "pre";
    private static final String TAG = PackageManager.class.getName();
    public boolean isLoggedIn = true;
    private Calendar calendar;
    private int year1, month, day;
    int finalTime,totTime,initialTime;
    TextView date1,time1,phone,ototal,opending,oongoing;
    String phoneNo,status,token;
    Switch aSwitch;
    String resid,restname;
    SpotsDialog progressDialog;
    private static final String HI2 ="https://foodbuggy.in/FoodApp/restaurants.php";
    private static final String HI3 ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php";
    String time,date,totaddress,totaddress11,dinTime,doutTime="00:00",latitude, longitude;
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = HomeActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Home");
        date1=findViewById(R.id.date);
        time1=findViewById(R.id.time);
        phone=findViewById(R.id.phone);
        aSwitch=findViewById(R.id.switch1);
        ototal=findViewById(R.id.total);
        oongoing=findViewById(R.id.ongoing);
        opending=findViewById(R.id.pending);
        progressDialog = new SpotsDialog(HomeActivity.this, R.style.Custom);
date1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
});
        aSwitch.setVisibility(View.GONE);
        calendar = Calendar.getInstance();
        year1 = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int sec=calendar.get(Calendar.SECOND);
        time=hour+":"+minute+":"+sec;
        initialTime=hour*60+minute;
        showDate(year1, month + 1, day);
        date1.setText(date);
        date1.setText(date);
        time1.setText(time);
        SharedPreferences preferences11=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences11.edit();
        phoneNo=preferences11.getString("phone","");
        token=preferences11.getString("token","");
        phone.setText(phoneNo);
        Log.i("token",token);
        server(token);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ss.setSelected(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getMovieData1();
aSwitch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressDialog.show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setMessage("Item not deleted")
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

        ChangeStatus1 registerRequest = new ChangeStatus1(phoneNo,status, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(registerRequest);
    }
}); final Handler handler = new Handler();
        final int delay = 100; //milliseconds
        handler.postDelayed(new Runnable(){
            public void run(){
                getMovieData2();
                handler.postDelayed(this, delay);
            }
        }, delay);

    }

    private void getMovieData2() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
        final String strDate = mdformat.format(calendar.getTime());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI3, new Response.Listener<String>() {
            int p=0,t=0,og=0;
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        if (ob.getString("restname").equals(restname) && ob.getString("date1").equals(strDate)) {
                            if (ob.getString("restStatus").equals("1")) {
                                og = og+ 1;
                            }
                            if (ob.getString("restStatus").equals("0")) {
                                p = p + 1;
                            }
                            t=t+1;
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

    private void server(String token) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
//                        Toast.makeText(HomeActivity.this, "Token Stored sucessfully", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setMessage("Item not added")
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

        TokenReq registerRequest = new TokenReq(phoneNo,token, responseListener);
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        queue.add(registerRequest);
    }

    private void submit1() {

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
                        if (ob.getString("res_phone").equals(phoneNo)) {
                            resid=ob.getString("res_id");
                            restname=ob.getString("res_name");
                           if(ob.getString("status").equals("yes")){
                               status="no";
                               aSwitch.setChecked(true);
                               aSwitch.setVisibility(View.VISIBLE);
                           }
                           else
                           {
                               status="yes";
                               aSwitch.setChecked(false);
                               aSwitch.setVisibility(View.VISIBLE);
                           }
                           String com=ob.getString("commission");
                            SharedPreferences preferences1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences1.edit();
                            editor.putString("resid",resid);
                            editor.putString("resid1",resid);
                            editor.putString("resname",restname);
                            editor.putString("comm",com);
                            editor.apply();
                            editor.commit();
                            getMovieData2();

                        }
                    }

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



    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(HomeActivity.this, myDateListener, year1, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year1, int month, int day) {
        date=day+"-"+month+"-"+year1;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
//            case R.id.profile:
//                Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_LONG).show();
////                startActivity(new Intent(Home.this,Profile.class));
//                return true;
            case R.id.logout:
//                Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("signout");
                builder.setMessage("Do you want to signout?");
                builder.setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isLoggedIn = false;
                        SharedPreferences preferences=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("LoggedIn", isLoggedIn);
                        editor.apply();
                        editor.commit();
                        Intent intent=new Intent(HomeActivity.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id= item.getItemId();
        switch (id){
            case R.id.home:
//                Toast.makeText(getApplicationContext(),"Buggy",Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this,HomeActivity.class));
                return true;
            case R.id.action_orders:
//                Toast.makeText(getApplicationContext(),"Orders",Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                return true;
            case R.id.action_profile:
//                Toast.makeText(getApplicationContext(),"Account",Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this,OrderHistory.class));
                return true;
            case R.id.action_menu:
//                Toast.makeText(getApplicationContext(),"Menu",Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this,MenuActivity.class));
                return true;
            case R.id.action_review:
//                Toast.makeText(getApplicationContext(),"Review Orders",Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this,MainActivity2.class));
                return true;
            case R.id.action_logs:
//                Toast.makeText(getApplicationContext(),"Logistics",Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this,MainActivity3.class));
                return true;
            case R.id.action_account:
//                Toast.makeText(getApplicationContext(),"Account",Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this,Account.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity();
                        finish();
                    }
                }).create().show();
    }
}