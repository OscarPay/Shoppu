package com.example.user.shoppu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompleteRegisterActivity extends AppCompatActivity {
    private static final String TAG = "CompleteRegisterActivity";

    @Bind(R.id.input_blood) EditText _bloodType;
    @Bind(R.id.input_birthday) EditText _birthday;
    @Bind(R.id.input_height) EditText _height;
    @Bind(R.id.input_weight) EditText _weight;
    @Bind(R.id.input_allergies) EditText _allergies;
    @Bind(R.id.input_gender) EditText _gender;
    @Bind(R.id.btn_fillProfile) Button _saveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);
        ButterKnife.bind(this);

        _saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillProfile();
            }
        });
    }

    private void fillProfile(){
        Log.d(TAG, "Filling Profile");

        if (!validate()) {
            onFillFailed(getString(R.string.register_failed));
            return;
        }

        _saveProfile.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(CompleteRegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.creating_account));
        progressDialog.show();

        String bloodType = _bloodType.getText().toString();
        String birthday = _birthday.getText().toString();
        String height = _height.getText().toString();
        String weight = _weight.getText().toString();
        String allergies = _allergies.getText().toString();
        String gender = _gender.getText().toString();


    }

    public void onFillFailed(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        _saveProfile.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String bloodType = _bloodType.getText().toString();
        String birthday = _birthday.getText().toString();
        String height = _height.getText().toString();
        String weight = _weight.getText().toString();
        String allergies = _allergies.getText().toString();
        String gender = _gender.getText().toString();

        if (bloodType.isEmpty() || bloodType.length() > 3) {
            _bloodType.setError(getString(R.string.at_leats_3_characters));
            valid = false;
        } else {
            _bloodType.setError(null);
        }

        if (birthday.isEmpty() || birthday.length() < 5) {
            _birthday.setError(getString(R.string.at_leats_5_characters));
            valid = false;
        } else {
            _birthday.setError(null);
        }

        if (height.isEmpty() || Integer.parseInt(height) < 0) {
            _height.setError(getString(R.string.invalid_value));
            valid = false;
        } else {
            _height.setError(null);
        }

        if (weight.isEmpty() || Integer.parseInt(weight)<0) {
            _weight.setError(getString(R.string.invalid_value));
            valid = false;
        } else {
            _weight.setError(null);
        }

        if (allergies.isEmpty()) {
            _allergies.setError(getString(R.string.invalid_value));
            valid = false;
        } else {
            _allergies.setError(null);
        }

        if(gender.isEmpty() || gender.length() > 2){
            _gender.setError(getString(R.string.gender));
        }

        return valid;
    }
}
