package me.mitul.aij.Aij;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import me.mitul.aij.R;

public class ContactUs extends AppCompatActivity {
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.contact_us);
        setSupportActionBar(findViewById(R.id.toolbar12));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar12)).setTitle(getResources().getString(R.string.contact_us_txt));
    }
}
