package me.mitul.aij.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanCollage;
import me.mitul.aij.R;

public class AdapterCollage extends BaseAdapter {
    private final Activity activity;
    private final ArrayList<BeanCollage> arrayCollage;

    public AdapterCollage(Activity a, ArrayList<BeanCollage> b) {
        arrayCollage = b;
        activity = a;
    }

    public int getCount() {
        return arrayCollage.size();
    }

    public Object getItem(int paramInt) {
        return arrayCollage.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return 0L;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        View row = paramView;
        ViewCollageHolder holder;
        if (row == null) {
            LayoutInflater localLayoutInflater = activity.getLayoutInflater();
            row = localLayoutInflater.inflate(R.layout.collage_list_listview_items, null);
            holder = new ViewCollageHolder(row);
            row.setTag(holder);
        } else holder = (ViewCollageHolder) row.getTag();
        holder.txtID.setText(String.valueOf(this.arrayCollage.get(paramInt).getCollageId()));
        holder.txtName.setText(String.valueOf(this.arrayCollage.get(paramInt).getCollageName()));
        holder.txtCollageFees.setText(String.valueOf(this.arrayCollage.get(paramInt).getFees()));
        holder.txtCollageHostel.setText(this.arrayCollage.get(paramInt).getHostel());
        holder.txtCollageBranches.setText(this.arrayCollage.get(paramInt).getBranches());
        return row;
    }

    private static class ViewCollageHolder {
        private final TextView txtID;
        private final TextView txtName;
        private final TextView txtCollageFees;
        private final TextView txtCollageHostel;
        private final TextView txtCollageBranches;

        private ViewCollageHolder(View v) {
            txtID = v.findViewById(R.id.collage_list_item_collage_id);
            txtName = v.findViewById(R.id.collage_list_item_collage_name);
            txtCollageFees = v.findViewById(R.id.collage_list_item_collage_fees);
            txtCollageHostel = v.findViewById(R.id.collage_list_item_collage_hostel);
            txtCollageBranches = v.findViewById(R.id.collage_list_item_collage_branches);
        }
    }
}
