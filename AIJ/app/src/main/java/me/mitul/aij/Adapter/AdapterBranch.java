package me.mitul.aij.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanBranch;
import me.mitul.aij.R;

public class AdapterBranch extends BaseAdapter {
    private final Activity activity;
    private final ArrayList<BeanBranch> arrayBranch;

    public AdapterBranch(Activity a, ArrayList<BeanBranch> b) {
        arrayBranch = b;
        activity = a;
    }

    public int getCount() {
        return arrayBranch.size();
    }

    public Object getItem(int paramInt) {
        return arrayBranch.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return 0L;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        View row = paramView;
        ViewBranchHolder holder;
        if (row == null) {
            LayoutInflater localLayoutInflater = activity.getLayoutInflater();
            row = localLayoutInflater.inflate(R.layout.branch_list_listview_items, null);
            holder = new ViewBranchHolder(row);
            row.setTag(holder);
        } else holder = (ViewBranchHolder) row.getTag();
        holder.txtBranchID.setText(String.valueOf(arrayBranch.get(paramInt).getBranchId()));
        holder.txtBranchNameName.setText(this.arrayBranch.get(paramInt).getBranchName());
        holder.txtCollageNum.setText(String.format("Foud In %d Collages", this.arrayBranch.get(paramInt).getNumCollege()));
        return row;
    }

    private class ViewBranchHolder {
        private final TextView txtBranchID;
        private final TextView txtBranchNameName;
        private final TextView txtCollageNum;

        private ViewBranchHolder(View v) {
            txtBranchID = v.findViewById(R.id.branch_list_item_branch_id);
            txtBranchNameName = v.findViewById(R.id.branch_list_item_branch_name);
            txtCollageNum = v.findViewById(R.id.branch_list_items_collage_count_number);
        }
    }
}
