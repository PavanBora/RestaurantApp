package pavan.sample.restaurantapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class Forgot extends AppCompatActivity {
    EditText phone;
    Button submit;
    SpotsDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        phone=findViewById(R.id.phone);
        submit=findViewById(R.id.btn_reset_password);
        progressDialog = new SpotsDialog(Forgot.this, R.style.Custom);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String phone1 = phone.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String phone = jsonResponse.getString("phone");
                                Intent intent = new Intent(Forgot.this, ResetPassword.class);
                                intent.putExtra("phone",phone);
                                progressDialog.dismiss();
                                Forgot.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Forgot.this);
                                builder.setMessage("No user Found!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ForgotReq forgotReq = new ForgotReq(phone1, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Forgot.this);
                queue.add(forgotReq);
            }
        });
    }
}
