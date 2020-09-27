package pavan.sample.restaurantapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class MenuActivity extends AppCompatActivity {
    Switch switch1;
    TabLayout tabLayout;
    ViewPager viewPager;
    String resid;
    SpotsDialog progressDialog;
    private static final String HI ="https://foodbuggy.in/FoodApp/viewrest1.php" ;
    private static final String PREF_NAME = "pre";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Menu");
//        switch1=findViewById(R.id.switch1);
        tabLayout=findViewById( R.id.tabs );
        viewPager=findViewById(R.id.viewpager);
        SharedPreferences preferences11=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences11.edit();
        resid=preferences11.getString("resname","");
//        progressDialog = new SpotsDialog(MenuActivity.this, R.style.Custom);
//        progressDialog.show();
        getMovieData33();

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
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
    private void getMovieData33() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        if (ob.getString("res_name").equals(resid)) {
                            MyPagerAdapter myPagerAdapter=new MyPagerAdapter( getSupportFragmentManager() );
                            if (ob.getString("cat_a").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Tiffins(),"Tiffins" );
                            }
                            if (ob.getString("cat_b").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Starters(),"Starters" );
                            }
                            if (ob.getString("cat_c").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Breads(),"Pulka/Roties" );
                            }
                            if (ob.getString("cat_d").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Combos(),"Combo Packs" );
                            }
                            if (ob.getString("cat_e").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Biryani(),"Biryanis" );
                            }
                            if (ob.getString("cat_f").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Meals(),"Rices" );
                            }
                            if (ob.getString("cat_g").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Curries(),"Curries" );
                            }
                            if (ob.getString("cat_h").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Chinese(),"Noodles" );
                            }
                            if (ob.getString("cat_i").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Soups(),"Soups" );
                            }
                            if (ob.getString("cat_j").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Bevarages(),"Bevarages" );
                            }
                            if (ob.getString("cat_k").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Burgers(),"Burgers" );
                            }
                            if (ob.getString("cat_l").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Rolls(),"Rolls & Sandwichs" );
                            }
                            if (ob.getString("cat_m").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Pizzas(),"Pizzas" );
                            }
                            if (ob.getString("cat_n").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Fruits(),"Fruits" );
                            }
                            if (ob.getString("cat_o").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Meet(),"Meat" );
                            }
                            if (ob.getString("cat_p").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Icecream(),"Ice Cream" );
                            }
                            if (ob.getString("cat_q").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Sweets(),"Sweets" );
                            }
                            if (ob.getString("cat_r").equals("yes"))
                            {
                                myPagerAdapter.addFragment( new Salads(),"Salads" );
                            }

                            viewPager.setAdapter( myPagerAdapter );
                            tabLayout.setupWithViewPager( viewPager );
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

}