package pavan.sample.restaurantapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class ResetPassword extends AppCompatActivity {
    EditText pass1,pass2;
    Button reset;
    String pass11,pass22,phone;
    SpotsDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        pass1=findViewById(R.id.password);
        pass2=findViewById(R.id.password1);
        reset=findViewById(R.id.btnLogin);
        Intent i=getIntent();
        phone=i.getStringExtra("phone");
        progressDialog = new SpotsDialog(ResetPassword.this, R.style.Custom);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {
            progressDialog.show();

            pass11=pass1.getText().toString();
            pass22=pass2.getText().toString();

            if ( !pass11.isEmpty() && !pass22.isEmpty() ) {

                if (pass11.equals(pass22)) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Intent intent = new Intent(ResetPassword.this, Login.class);
                                    ResetPassword.this.startActivity(intent);
                                    progressDialog.dismiss();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassword.this);
                                    builder.setMessage("ResetPassword Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ResetReq resetReq = new ResetReq( phone,pass11, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ResetPassword.this);
                    queue.add(resetReq);
                }
                else
                {
                    Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                pass1.setError("please fill the details");
                pass2.setError("please fill the details");
            }
        }

}
