package com.sunit.zingo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sunit.zingo.Models.Vendor;
import com.sunit.zingo.Utils.APIInterface;
import com.sunit.zingo.Utils.ApiClient;
import com.sunit.zingo.R;
import com.sunit.zingo.Utils.AppUtil;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    EditText phoneNo;
    EditText password;
    Button login;
    boolean isPhoneNoChecked = false;
    boolean isPasswordChecked = false;
    TextInputLayout phoneNo_layout, password_layout;
    boolean haveConnection;
    TextView signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        haveConnection = checkNetworkConnection();
        if (!haveConnection){
            Toast.makeText(this, "Please Turn on Data Connection", Toast.LENGTH_SHORT).show();
            return;
        }

        phoneNo_layout = (TextInputLayout) findViewById(R.id.textInputPhone);
        password_layout = (TextInputLayout) findViewById(R.id.textInputPassword);
        phoneNo = findViewById(R.id.editTextPhoneNo);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUp);

        apiInterface = ApiClient.getClient().create(APIInterface.class);

        String text = "<font color='white'>Don't have an account?</font><font color='yellow'>Sign Up</font>";

        signUp.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isPhoneNoChecked = CheckPhoneNo();
                isPasswordChecked = CheckPassword();

                if (isPhoneNoChecked && isPasswordChecked) {

                    authenticate(AppUtil.getHEADER(), new Vendor(phoneNo.getText().toString(), password.getText().toString(), AppUtil.getPLAYERID()));
                }
            }
        });
    }


    private boolean CheckPhoneNo() {

        String phoneInput = phoneNo.getText().toString().trim();

        if (phoneInput.isEmpty()) {
            phoneNo_layout.setError("Phone Number is required");
            return false;
        } else if (phoneInput.length() < 10 || phoneInput.length() > 10) {
            phoneNo_layout.setError("Phone Number must be 10 digits");
            return false;
        } else if (!Patterns.PHONE.matcher(phoneInput).matches()) {
            phoneNo_layout.setError("Invalid Phone Number");
            return false;
        } else {
            phoneNo_layout.setError(null);
        }

        return true;
    }

    private boolean CheckPassword() {

        String passwordInput = password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            password_layout.setError("Password is required");
            return false;
        } else if (!AppUtil.getPasswordPattern().matcher(passwordInput).matches()) {
            password_layout.setError("Passwords must contain a minimum password length of 6 characters and must contain at least 1 lowercase letter, 1 capital letter, 1 number and 1 special character");
            return false;
        }


        return true;
    }


    private void authenticate(String header, Vendor vendor) {

        Call<Vendor> call = apiInterface.authenticate(header, vendor);

        call.enqueue(new Callback<Vendor>() {
            @Override
            public void onResponse(Call<Vendor> call, Response<Vendor> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code: ", String.valueOf(response.code()));
                    return;
                }


                keepSignedIn();

                Intent intent = new Intent(AuthenticationActivity.this, HomeActivity.class);
                startActivity(intent);

                Toast.makeText(AuthenticationActivity.this, "Welcome to Zingo!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Vendor> call, Throwable t) {

            }
        });

    }
    private boolean checkNetworkConnection() {

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void keepSignedIn() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("SignInStatus", true);
        editor.apply();
    }

}