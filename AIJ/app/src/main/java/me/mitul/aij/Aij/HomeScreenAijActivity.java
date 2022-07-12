package me.mitul.aij.Aij;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import me.mitul.aij.Adapter.AdapterViewPager;
import me.mitul.aij.Constants.PlaceHolderFragment;
import me.mitul.aij.R;

public class HomeScreenAijActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_home_aij);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mDrawerLayout = findViewById(R.id.drawer_layout_home_screen_aij);
        NavigationView localNavigationView = findViewById(R.id.navigation_drawer_home_screen_aij);
        if (localNavigationView != null) setupDrawerContent(localNavigationView);
        ViewPager localViewPager = findViewById(R.id.viewpager_home_screen_aij);
        if (localViewPager != null) setupViewPager(localViewPager);
        ((TabLayout) findViewById(R.id.tabs11)).setupWithViewPager(localViewPager);
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.my_collage, paramMenu);
        return true;
    }

    private void setupViewPager(ViewPager paramViewPager) {
        AdapterViewPager localAdapter = new AdapterViewPager(getSupportFragmentManager());
        localAdapter.addFragment(PlaceHolderFragment.newInstance(1), getResources().getString(R.string.welcome_lbl));
        localAdapter.addFragment(PlaceHolderFragment.newInstance(16), getResources().getString(R.string.about_lbl));
        localAdapter.addFragment(PlaceHolderFragment.newInstance(21), getResources().getString(R.string.vision_lbl));
        localAdapter.addFragment(PlaceHolderFragment.newInstance(20), getResources().getString(R.string.misssion_lbl));
        localAdapter.addFragment(PlaceHolderFragment.newInstance(22), getResources().getString(R.string.why_aij_lbl));
        paramViewPager.setAdapter(localAdapter);
    }

    private void setupDrawerContent(NavigationView paramNavigationView) {
        paramNavigationView.setNavigationItemSelectedListener(paramAnonymousMenuItem -> {
            paramAnonymousMenuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            int localItemID = 0;
            switch (paramAnonymousMenuItem.getItemId()) {
                case R.id.nav_aij_faqs:
                    localItemID = 1;
                    break;
                case R.id.nav_aij_carrier:
                    localItemID = 3;
                    break;
                case R.id.nav_aij_admission_procedure:
                    localItemID = 4;
                    break;
                case R.id.nav_aij_eligibility_criteria:
                    localItemID = 99;
                    break;
                case R.id.nav_cources:
                    startActivity(new Intent(HomeScreenAijActivity.this, CourcesActivity.class));
                    return true;
                case R.id.nav_aij_facilities:
                    localItemID = 13;
                    break;
                case R.id.nav_aij_Activities:
                    localItemID = 14;
                    break;
                case R.id.nav_aij_policy:
                    localItemID = 15;
                    break;
                case R.id.nav_aij_about_shect:
                    localItemID = 17;
                    break;
                case R.id.nav_aij_infrastructure:
                    localItemID = 18;
                    break;
                case R.id.nav_placement:
                    localItemID = 19;
                    break;
            }
            startActivity(new Intent(HomeScreenAijActivity.this, AijExplorerActivity.class).putExtra(getString(R.string.item_id), localItemID));
            return true;
        });
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        return true;
    }
}
