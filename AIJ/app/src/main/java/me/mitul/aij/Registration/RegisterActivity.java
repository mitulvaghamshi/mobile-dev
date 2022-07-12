package me.mitul.aij.Registration;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import me.mitul.aij.Helper.HelperRegistration;
import me.mitul.aij.R;

public class RegisterActivity extends AppCompatActivity {
    private AutoCompleteTextView editTextFullName;
    private AutoCompleteTextView editTextEmail;
    private AutoCompleteTextView editTextMobile;
    private AutoCompleteTextView editTextCity;
    private AutoCompleteTextView editTextPassword;
    private AutoCompleteTextView editTextPasswordRe;
    private View mProgressView;
    private View mRegFormView;
    private Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFullName = findViewById(R.id.full_name_reg);
        editTextEmail = findViewById(R.id.email_reg);
        editTextMobile = findViewById(R.id.mobile_reg);
        editTextCity = findViewById(R.id.city_reg);
        editTextPassword = findViewById(R.id.password_reg);
        editTextPasswordRe = findViewById(R.id.password_reg_re);
        mProgressView = findViewById(R.id.fab_register_go);
        mRegFormView = findViewById(R.id.reg_form);

        findViewById(R.id.fab_register_go).setOnClickListener(paramAnonymousView -> attemptReg());

        findViewById(R.id.fab_skip_go).setOnClickListener(v -> new AlertDialog
                .Builder(RegisterActivity.this)
                .setMessage("Registration required!")
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .create().show());
        shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
    }

    private void attemptReg() {
        boolean regAble = true;
        String strTextFullName = editTextFullName.getText().toString();
        String strTextEmail = editTextEmail.getText().toString();
        String strTextMobile = editTextMobile.getText().toString();
        String strTextCity = editTextCity.getText().toString();
        String strTextPassword = editTextPassword.getText().toString();
        String strTextPasswordRe = editTextPasswordRe.getText().toString();
        String deviceId = Settings.System
                .getString(RegisterActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (TextUtils.isEmpty(strTextFullName)) {
            editTextFullName.startAnimation(shake);
            regAble = false;
        }
        if (TextUtils.isEmpty(strTextEmail) ||
                !strTextEmail.contains("@") ||
                !strTextEmail.contains(".")) {
            editTextEmail.startAnimation(shake);
            regAble = false;
        }
        if (TextUtils.isEmpty(strTextMobile)) {
            editTextMobile.startAnimation(shake);
            regAble = false;
        }
        if (TextUtils.isEmpty(strTextCity)) {
            editTextCity.startAnimation(shake);
            regAble = false;
        }
        if (TextUtils.isEmpty(strTextPassword) ||
                strTextPassword.length() < 6) {
            editTextPassword.startAnimation(shake);
            regAble = false;
        }
        if (TextUtils.isEmpty(strTextPasswordRe) ||
                !strTextPassword.equals(strTextPasswordRe)) {
            editTextPasswordRe.startAnimation(shake);
            regAble = false;
        }
        if (regAble)
            new UserRegTask().execute(strTextFullName,
                    strTextEmail, strTextMobile, strTextCity,
                    strTextPassword, strTextPasswordRe, deviceId);
    }

    private class UserRegTask extends AsyncTask<String, Void, Boolean> {
        String uName;
        String pWord;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Thread.sleep(2000);
                return new HelperRegistration(RegisterActivity.this)
                        .attemtReg(uName = params[0], params[1], params[2], params[3],
                                pWord = params[4], params[5], params[6]);
            } catch (InterruptedException ignored) {
            }
            return false;
        }

        @Override
        protected void onPreExecute() {
            mProgressView.startAnimation(AnimationUtils
                    .loadAnimation(getApplicationContext(), R.anim.anim_fab));
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class)
                        .putExtra("IsRegistered", true)
                        .putExtra("UserName", uName)
                        .putExtra("Password", pWord));
                finish();
            } else mRegFormView.startAnimation(shake);
        }
    }
}
