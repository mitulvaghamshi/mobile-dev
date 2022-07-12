package me.mitul.aij.Aij;

import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import me.mitul.aij.Constants.PlaceHolderFragment;
import me.mitul.aij.R;

public class CourcesActivity extends AppCompatActivity {
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_cources);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewPager viewPager = findViewById(R.id.viewpager_home_screen_aij);
        if (viewPager != null) setupViewPager(viewPager);
        ((TabLayout) findViewById(R.id.tabs_cources)).setupWithViewPager(viewPager);
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.my_collage, paramMenu);
        return true;
    }

    private void setupViewPager(ViewPager paramViewPager) {
        CourcesAdapter localAdapter = new CourcesAdapter(getSupportFragmentManager());
        localAdapter.addFragment(PlaceHolderFragment.newInstance(6), "Architect");
        localAdapter.addFragment(PlaceHolderFragment.newInstance(7), "Automobile");
        localAdapter.addFragment(PlaceHolderFragment.newInstance(8), "Civil");
        localAdapter.addFragment(PlaceHolderFragment.newInstance(9), "Computer");
        localAdapter.addFragment(PlaceHolderFragment.newInstance(10), "Electrical");
        localAdapter.addFragment(PlaceHolderFragment.newInstance(11), "Inst. & Control");
        localAdapter.addFragment(PlaceHolderFragment.newInstance(12), "Mechanical");
        paramViewPager.setAdapter(localAdapter);
    }

    private static class CourcesAdapter extends FragmentPagerAdapter {
        private final ArrayList<String> mFragmentTitles = new ArrayList<>();
        private final ArrayList<Fragment> mFragments = new ArrayList<>();

        CourcesAdapter(FragmentManager paramFragmentManager) {
            super(paramFragmentManager);
        }

        void addFragment(Fragment paramFragment, String paramString) {
            mFragments.add(paramFragment);
            mFragmentTitles.add(paramString);
        }

        public int getCount() {
            return mFragments.size();
        }

        @NonNull
        public Fragment getItem(int paramInt) {
            return this.mFragments.get(paramInt);
        }

        public CharSequence getPageTitle(int paramInt) {
            return this.mFragmentTitles.get(paramInt);
        }
    }
}
