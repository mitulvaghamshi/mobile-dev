package me.mitul.aij.Registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import me.mitul.aij.Helper.HelperBackupRestore;
import me.mitul.aij.Helper.HelperLogin;
import me.mitul.aij.HomeScreen.HomeScreenActivity;
import me.mitul.aij.R;

public class LoginActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mPasswordView;
    private CheckBox cbxKeepMeLoggedIn;
    private View mProgressView;
    private View mLoginFormView;
    private Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = findViewById(R.id.login_email);
        mPasswordView = findViewById(R.id.login_password);
        cbxKeepMeLoggedIn = findViewById(R.id.checkBoxKeepMeLoggedIn);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.fab_login_go);

        SharedPreferences userLoginPreference = getSharedPreferences("Login1", 0);
        Intent localIntent = getIntent();

        if (localIntent.getBooleanExtra("IsRegistered", false)) {
            mEmailView.setText(localIntent.getStringExtra("UserName"));
            mPasswordView.setText(localIntent.getStringExtra("Password"));
        }

        SharedPreferences firstTimeRun = getPreferences(Context.MODE_PRIVATE);
        if (firstTimeRun.getBoolean("firstTime", true)) {
            if (HelperBackupRestore.sdDatabaseExists()) {
                new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
                        .setTitle("Backup found!")
                        .setMessage("Do you want to restore previous data ?(Restart Required!)")
                        .setCancelable(false).setNegativeButton("No", null)
                        .setPositiveButton("Yes", (p1, p2) -> {
                            HelperBackupRestore.restoreDb();
                            finish();
                            startActivity(getIntent());
                        }).create().show();
            }
            firstTimeRun.edit().putBoolean("firstTime", false).apply();
        }

        if (userLoginPreference.getBoolean("keepMeLoggdIn", false)) {
            mEmailView.setText(userLoginPreference.getString("Email1", ""));
            mPasswordView.setText(userLoginPreference.getString("Pass1", ""));
            cbxKeepMeLoggedIn.setChecked(userLoginPreference.getBoolean("keepMeLoggdIn", false));
            // attemptLogin();
        }
        findViewById(R.id.fab_login_go).setOnClickListener(view -> attemptLogin());

        findViewById(R.id.fab_reg_new).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
        shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cbxKeepMeLoggedIn.isChecked()) getSharedPreferences("Login1", 0).edit()
                .putBoolean("keepMeLoggdIn", cbxKeepMeLoggedIn.isChecked())
                .putString("Email1", mEmailView.getText().toString())
                .putString("Pass1", mPasswordView.getText().toString()).apply();
        else getSharedPreferences("Login1", 0).edit()
                .putBoolean("keepMeLoggdIn", cbxKeepMeLoggedIn.isChecked())
                .putString("Email1", "mady@me")
                .putString("Pass1", "123456").apply();
    }

    private void attemptLogin() {
        boolean logAble = true;
        String mEmail = mEmailView.getText().toString();
        String mPass = mPasswordView.getText().toString();

        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.startAnimation(shake);
            logAble = false;
        }
        if (TextUtils.isEmpty(mPass)) {
            mPasswordView.startAnimation(shake);
            logAble = false;
        }
        if (logAble) new UserLoginTask().execute(mEmail, mPass);
    }

    private class UserLoginTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            try {
                Thread.sleep(2000);
                return new HelperLogin(LoginActivity.this).attemtLogin(params[0], params[1]);
            } catch (InterruptedException ignored) {
            }
            return 999;
        }

        @Override
        protected void onPreExecute() {
            mProgressView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fab));
        }

        @Override
        protected void onPostExecute(Integer localID) {
            if (localID != 999) {
                startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class)
                        .putExtra("UserID", localID.toString()));
                finish();
            } else mLoginFormView.startAnimation(shake);
        }
    }
}
