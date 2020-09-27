package pavan.sample.restaurantapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class Login extends AppCompatActivity {
    EditText phone, pass;
    Button login;
    TextView sign;
    CheckBox checkBox;
    TextView forgot;
    SpotsDialog progressDialog ;
    boolean isLoggedIn = false;
    public SharedPreferences preferences;
    private static final String PREF_NAME = "pre";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone = findViewById(R.id.username1);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.btnLogin);
        forgot = findViewById(R.id.forget);
        checkBox = findViewById(R.id.prem);
        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        progressDialog = new SpotsDialog(Login.this, R.style.Custom);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Forgot.class);
                startActivity(i);
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {
                if (isChecked) {

                    checkBox.setText("Hide Password");// change
                    pass.setInputType(InputType.TYPE_CLASS_TEXT);
                    pass.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    checkBox.setText("Show Password");// change
                    pass.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password

                }

            }
        });
        if (preferences.getBoolean("LoggedIn", isLoggedIn))
        {
            progressDialog.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 10 seconds

                    Intent intent = new Intent(Login.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                }
            }, 1500);

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String phone1 = phone.getText().toString();
                final String password = pass.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                isLoggedIn = true;
                                SharedPreferences preferences1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences1.edit();
                                editor.putBoolean("LoggedIn", isLoggedIn);
                                editor.putString("phone",phone1);
                                editor.apply();
                                editor.commit();
                                String phone = jsonResponse.getString("phone");
                                String password = jsonResponse.getString("password");
                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                progressDialog.dismiss();
                                intent.putExtra("phone", phone);
//                                intent.putExtra("id", id);
//                                intent.putExtra("username", email);
                                Login.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(phone1, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
//        new androidx.appcompat.app.AlertDialog.Builder(this)
//                .setTitle("Really Exit?")
//                .setMessage("Are you sure you want to exit?")
//                .setNegativeButton(android.R.string.no, null)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        Login.super.onBackPressed();
//                    }
//                }).create().show();
    }
}
