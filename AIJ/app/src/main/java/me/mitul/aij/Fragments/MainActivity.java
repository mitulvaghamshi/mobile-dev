package me.mitul.aij.Fragments;

import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import me.mitul.aij.R;

public class MainActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = findViewById(R.id.activity_mainLinearLayout);
        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();
        if (d.getWidth() < d.getHeight()) ll.setOrientation(LinearLayout.HORIZONTAL);
        else ll.setOrientation(LinearLayout.VERTICAL);
		
		/*
        if (getResources().getConfiguration().orientation == 
                Configuration.ORIENTATION_LANDSCAPE) { 
            //---landscape mode---
            setContentView(R.layout.activity_landscape_main);            
        } else {
            //---portrait mode---
            setContentView(R.layout.activity_main);            
        }*/
    }
}
