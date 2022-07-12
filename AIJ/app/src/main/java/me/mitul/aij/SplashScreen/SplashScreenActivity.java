package me.mitul.aij.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.Objects;

import me.mitul.aij.Adapter.AdapterViewPager;
import me.mitul.aij.Constants.FixedSpeedScroller;
import me.mitul.aij.Constants.SplashHolderFragment;
import me.mitul.aij.Helper.HelperLogin;
import me.mitul.aij.R;
import me.mitul.aij.Registration.LoginActivity;

public class SplashScreenActivity extends FragmentActivity {
    @Override
    public void onBackPressed() {
        long milli;
        if ((milli = System.currentTimeMillis()) < milli + 2000L) super.onBackPressed();
        else Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new HelperLogin(this);
        final MyViewPager viewPager = findViewById(R.id.pager);
        if (viewPager != null) setupViewPager(viewPager);
        try {
            Field localField = ViewPager.class.getDeclaredField("mScroller");
            localField.setAccessible(true);
            assert viewPager != null;
            FixedSpeedScroller localFixedSpeedScroller = new FixedSpeedScroller(viewPager.getContext());
            try {
                localField.set(viewPager, localFixedSpeedScroller);
            } catch (IllegalAccessException | IllegalArgumentException ignored) {
            }
        } catch (NoSuchFieldException ignored) {
        }
        new Thread(new Runnable() {
            int value = 0;
            final Handler handler = new Handler(Looper.getMainLooper());

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000L);
                        handler.post(() -> Objects.requireNonNull(viewPager)
                                .setCurrentItem(value <= 7 ? value++ : (value = 0), true));
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();

        findViewById(R.id.splash_fab_login_go).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }

    private void setupViewPager(MyViewPager paramViewPager) {
        AdapterViewPager localAdapter = new AdapterViewPager(getSupportFragmentManager());
        int i = 0;
        while (i++ < 7) localAdapter.addFragment(SplashHolderFragment.newInstance(i), "fragment");
        paramViewPager.setAdapter(localAdapter);
    }
}
