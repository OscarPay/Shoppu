package com.example.user.shoppu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.shoppu.models.Registration;
import com.example.user.shoppu.remote.PatientAPI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_lastname) EditText _lastnameText;
    @Bind(R.id.input_cellphone) EditText _phoneText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_password_confirm) EditText _passwordConfirmText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed(getString(R.string.register_failed));
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.creating_account));
        progressDialog.show();

        String name = _nameText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String phone = _phoneText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String passwordConfirm = _passwordConfirmText.getText().toString();

        Registration registration = new Registration(name,lastname,email,password,passwordConfirm, phone);

        PatientAPI.Factory.getInstance().register(registration).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                final int code = response.code();
                Gson gson = new Gson();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                switch (code){
                                    case 201:
                                        Log.e(TAG, response.body().toString());
                                        onSignupSuccess();
                                        break;
                                    case 422:
                                        try {
                                            Log.d(TAG,response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        //String message = (String) o.get(getString(R.string.message_key));
                                        //Log.e(TAG, message);
                                        onSignupFailed("Registro fallido");
                                        break;
                                    default:
                                        try {
                                            Log.e(TAG, Integer.toString(code));

                                            JSONObject o =  new JSONObject(response.errorBody().string());
                                            String message = (String) o.get(getString(R.string.message_key));
                                            Log.e(TAG, message);
                                            onSignupFailed(message);
                                        } catch (IOException | JSONException e) {
                                            Log.e(TAG, e.getMessage());
                                        }
                                        break;
                                }
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, t.getMessage());

            }
        });
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed(String message) {
        Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_SHORT).show();
        //Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String phone = _phoneText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String passwordConfirm = _passwordConfirmText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError(getString(R.string.at_leats_3_characters));
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (lastname.isEmpty() || lastname.length() < 5) {
            _lastnameText.setError(getString(R.string.at_leats_3_characters));
            valid = false;
        } else {
            _lastnameText.setError(null);
        }

        if (phone.isEmpty() || phone.length() < 5) {
            _phoneText.setError(getString(R.string.at_leats_3_characters));
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getString(R.string.valid_email));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 15) {
            _passwordText.setError(getString(R.string.between_8_characters));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (passwordConfirm.isEmpty() || passwordConfirm.length() < 8 || passwordConfirm.length() > 15) {
            _passwordConfirmText.setError(getString(R.string.between_8_characters));
            valid = false;
        } else {
            if(!password.equals(passwordConfirm)){
                _passwordConfirmText.setError(getString(R.string.password_not_match));
                valid = false;
            }else{
                _passwordConfirmText.setError(null);
            }
        }

        return valid;
    }
}
