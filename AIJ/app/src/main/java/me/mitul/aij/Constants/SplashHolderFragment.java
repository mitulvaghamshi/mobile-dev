package me.mitul.aij.Constants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import me.mitul.aij.Bean.BeanSplashScreen;
import me.mitul.aij.Helper.HelperSplashScreen;
import me.mitul.aij.R;

public class SplashHolderFragment extends Fragment {

    public static SplashHolderFragment newInstance(int paramStringID) {
        SplashHolderFragment fragment = new SplashHolderFragment();
        Bundle args = new Bundle();
        args.putInt("ARG_String_ID", paramStringID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        int ARG_ITEM_ID = getArguments().getInt("ARG_String_ID");
        if (ARG_ITEM_ID == 0) return rootView;
        TextView tv = rootView.findViewById(R.id.fragment_tv_splash_text);
        BeanSplashScreen localBeanSplashScreen = new HelperSplashScreen(getContext()).selectSplashTextData(ARG_ITEM_ID);
        tv.setText(localBeanSplashScreen.getSplashText());
        //tv.setTextColor(Color.parseColor(localBeanSplashScreen.getSplashTextColor())/*getResources().getColor(getResources().getIdentifier(localBeanSplashScreen.getSplashTextColor(), "color", getContext().getPackageName()))*/);
        //tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 50/*Float.valueOf(localBeanSplashScreen.getSplashTextSize())*//*getResources().getDimension(getResources().getIdentifier(localBeanSplashScreen.getSplashTextSize(), "dimen", getContext().getPackageName()))*/);
        // tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/Precious.ttf"));
        return rootView;
    }
}
