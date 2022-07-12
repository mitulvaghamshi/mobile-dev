package me.mitul.aij.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdapterViewPager extends FragmentPagerAdapter {
    private final ArrayList<String> titles = new ArrayList<>();
    private final ArrayList<Fragment> arrayFragment = new ArrayList<>();

    public AdapterViewPager(FragmentManager paramFragmentManager) {
        super(paramFragmentManager);
    }

    public void addFragment(Fragment paramFragment, String paramString) {
        arrayFragment.add(paramFragment);
        titles.add(paramString);
    }

    public int getCount() {
        return arrayFragment.size();
    }

    public Fragment getItem(int paramInt) {
        return arrayFragment.get(paramInt);
    }

    public CharSequence getPageTitle(int paramInt) {
        return titles.get(paramInt);
    }
}
