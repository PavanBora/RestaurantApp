package pavan.sample.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
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

import dmax.dialog.SpotsDialog;

public class Account extends AppCompatActivity {
ImageView imageView;
String phoneNo;
    SpotsDialog progressDialog;
    Bitmap bitmap;
TextView oname,ophone,oemail,oaadhar,orest,ophone1,id22;
    private static final String HI2 ="https://foodbuggy.in/FoodApp/restaurantInfo.php";
    private static final String PREF_NAME = "pre";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account");
        imageView=findViewById(R.id.photo);
        oname=findViewById(R.id.oname);
        ophone=findViewById(R.id.ophone);
        oemail=findViewById(R.id.oemail);
        oaadhar=findViewById(R.id.oaadhar);
        ophone1=findViewById(R.id.ophone1);
        id22=findViewById(R.id.id22);
        orest=findViewById(R.id.orest);
        progressDialog = new SpotsDialog(Account.this, R.style.Custom);
        progressDialog.show();
        SharedPreferences preferences11=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences11.edit();
        phoneNo=preferences11.getString("phone","");
        getMovieData1();
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
                        if (ob.getString("own_phone").equals(phoneNo)) {
                            orest.setText(ob.getString("res_name"));
                            oname.setText(ob.getString("own_name"));
                            ophone.setText(ob.getString("own_phone"));
                            oemail.setText(ob.getString("own_email"));
                            oaadhar.setText(ob.getString("aadhar_num"));
                            ophone1.setText(ob.getString("own_phone"));
                            id22.setText(ob.getString("res_id"));
                            String url=ob.getString("own_pic");
                            if (imageView != null) {
                                /*-------------fatching image------------*/;
                                new ImageDownloaderTask(imageView).execute(url);
                            }
                           imageView.setImageBitmap(bitmap);
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