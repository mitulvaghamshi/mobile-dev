package me.mitul.aij.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanUniversity;
import me.mitul.aij.R;

public class AdapterUniversity extends BaseAdapter {
    private final Activity activity;
    private final ArrayList<BeanUniversity> arrayUniversity;

    public AdapterUniversity(Activity a, ArrayList<BeanUniversity> b) {
        arrayUniversity = b;
        activity = a;
    }

    public int getCount() {
        return arrayUniversity.size();
    }

    public Object getItem(int paramInt) {
        return arrayUniversity.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return 0L;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        View row = paramView;
        ViewUniversityHolder holder;
        if (row == null) {
            LayoutInflater localLayoutInflater = activity.getLayoutInflater();
            row = localLayoutInflater.inflate(R.layout.simple_one_tv_listview_items, null);
            holder = new ViewUniversityHolder(row);
            row.setTag(holder);
        } else holder = (ViewUniversityHolder) row.getTag();
        holder.universityID.setText(String.valueOf(arrayUniversity.get(paramInt).getUniversityID()));
        holder.universityName.setText(arrayUniversity.get(paramInt).getUniversityName());
        return row;
    }

    private static class ViewUniversityHolder {
        private final TextView universityID;
        private final TextView universityName;

        private ViewUniversityHolder(View v) {
            universityID = v.findViewById(R.id.list_simple_public_id);
            universityName = v.findViewById(R.id.list_simple_public_name);
        }
    }
}
