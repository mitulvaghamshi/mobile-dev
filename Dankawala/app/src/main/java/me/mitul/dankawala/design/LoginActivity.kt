package me.mitul.dankawala.design

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import me.mitul.dankawala.R
import me.mitul.dankawala.helper.LoginHelper
import me.mitul.dankawala.utility.Utils

class LoginActivity : AppCompatActivity() {
    private var mFirstRun = false
    private var mLoggedIn: CheckBox? = null
    private var mProgressView: View? = null
    private var mLoginFormView: View? = null
    private var mShake: Animation? = null
    private val mAuthTask: LoginTask? = null
    private var mUsername: AutoCompleteTextView? = null
    private var mPassword: AutoCompleteTextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById<View>(R.id.toolbar_login_screen) as Toolbar)
        mUsername = findViewById(R.id.login_username)
        mPassword = findViewById(R.id.login_password)
        mLoggedIn = findViewById(R.id.login_keep_me)
        mLoginFormView = findViewById(R.id.login_form)
        mProgressView = findViewById(R.id.login_progress_send)
        mShake = AnimationUtils.loadAnimation(this@LoginActivity, R.anim.anim_shake)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            201
        )
        findViewById<View>(R.id.login_progress_send).setOnClickListener {
            if (mFirstRun) {
                if (LoginHelper(this@LoginActivity).insertUser(
                        mUsername?.text.toString(),
                        mPassword?.text.toString(),
                        if (mLoggedIn?.isChecked!!) 1 else 0
                    ) != -1L
                ) attemptLogin()
            } else attemptLogin()
        }
        val firstTimeRun = getPreferences(Context.MODE_PRIVATE)
        if (firstTimeRun.getBoolean("FIRST_RUN", true)) {
            mFirstRun = true
            mUsername?.hint = "enter new username"
            mPassword?.hint = "enter new password"
            if (Utils(this).isBackupFound) {
                AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_restore)
                    .setTitle("Backup found!")
                    .setMessage("Do you want to restore previous data ?")
                    .setCancelable(false)
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes, Restore!") { p1, p2 ->
                        LoginHelper(this@LoginActivity)
                        Utils(this@LoginActivity).restoreDb()
                        firstTimeRun.edit().putBoolean("FIRST_RUN", false).apply()
                        mUsername?.hint = "enter username"
                        mPassword?.hint = "enter password"
                        mFirstRun = false
                        recreate()
                    }.create().show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val userLoginPreference =
            getSharedPreferences("DANKAWALA_PREFERENCES", Context.MODE_PRIVATE)
        if (userLoginPreference.getBoolean("KEEP_LOGGED_IN", false)) {
            mUsername!!.setText(userLoginPreference.getString("Email1", "Username"))
            mPassword!!.setText(userLoginPreference.getString("Pass1", "Password"))
            mLoggedIn!!.isChecked = userLoginPreference.getBoolean("KEEP_LOGGED_IN", false)
        }
    }

    override fun onStop() {
        super.onStop()
        if (mLoggedIn!!.isChecked) {
            getSharedPreferences("DANKAWALA_PREFERENCES", Context.MODE_PRIVATE).edit()
                .putBoolean("KEEP_LOGGED_IN", mLoggedIn!!.isChecked)
                .putString("Email1", mUsername!!.text.toString())
                .putString("Pass1", mPassword!!.text.toString()).apply()
        } else getSharedPreferences("DANKAWALA_PREFERENCES", Context.MODE_PRIVATE).edit()
            .putBoolean("KEEP_LOGGED_IN", mLoggedIn!!.isChecked)
            .putString("Email1", "mady@me")
            .putString("Pass1", "me@mady").apply()
    }

    private fun attemptLogin() {
        if (mAuthTask != null) return
        var logAble = true
        val mEmail = mUsername!!.text.toString()
        val mPass = mPassword!!.text.toString()
        if (TextUtils.isEmpty(mEmail)) {
            mUsername!!.startAnimation(mShake)
            logAble = false
        }
        if (TextUtils.isEmpty(mPass)) {
            mPassword!!.startAnimation(mShake)
            logAble = false
        }
        if (logAble) LoginTask().execute(mEmail, mPass)
    }

    private inner class LoginTask : AsyncTask<String?, Void?, Int>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): Int {
            val tVar = LoginHelper(this@LoginActivity).attemptLogin(params[0]!!, params[1]!!)
            try {
                Thread.sleep(2000)
            } catch (ignored: InterruptedException) {
            }
            return tVar
        }

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            mProgressView!!.startAnimation(
                AnimationUtils.loadAnimation(
                    applicationContext,
                    R.anim.anim_rotate
                )
            )
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(localID: Int) {
            if (localID != 999) {
                getPreferences(Context.MODE_PRIVATE).edit().putBoolean("FIRST_RUN", false).apply()
                startActivity(
                    Intent(applicationContext, MainActivity::class.java)
                        .putExtra("UserID", localID.toString())
                )
                finish()
            } else mLoginFormView!!.startAnimation(mShake)
        }
    }
}
