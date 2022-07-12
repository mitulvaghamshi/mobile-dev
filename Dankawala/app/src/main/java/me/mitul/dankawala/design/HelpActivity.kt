package me.mitul.dankawala.design

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val mWebView = WebView(this)
        //mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.loadUrl("file:///android_asset/guide/index.html");
        mWebView.loadData("<html><body>Under construction...</body></html>", "text/html", "UTF-8")
        setContentView(mWebView)
    }
}
