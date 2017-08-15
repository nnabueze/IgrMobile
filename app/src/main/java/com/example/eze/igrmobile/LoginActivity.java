package com.example.eze.igrmobile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.input_email) EditText emailText;
    @Bind(R.id.input_password) EditText passwordText;
    @Bind(R.id.btn_login) Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(i);
            }
        });

/*        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });*/
    }

    private void login() {
        if (!validate()){
            onLoginFail();
        }

        if (!isOnLine()){
            Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show();
        }else{
           makeCall();
        }
    }

    private void makeCall() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Utility.LOGIN_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        onLoginFailDialog();
                    }
                });
    }

    private void onLoginFailDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle("Error Message");
        builder.setMessage("Invalid Email and Password");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }

    private boolean isOnLine() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    private void onLoginFail() {
        Toast.makeText(this, "Invalid email and password", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Enter a valid email address");
            valid=false;
        }else{
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3){
            passwordText.setError("Password can not be less than 3");
            valid=false;
        }else{
            passwordText.setError(null);
        }
        return valid;
    }
}
