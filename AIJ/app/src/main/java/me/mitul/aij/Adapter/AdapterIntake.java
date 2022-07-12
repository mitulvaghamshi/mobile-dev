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

public class AdapterIntake extends BaseAdapter {
    private final Activity activity;
    private final ArrayList<BeanCollage> arrayIntake;

    public AdapterIntake(Activity a, ArrayList<BeanCollage> b) {
        arrayIntake = b;
        activity = a;
    }

    public int getCount() {
        return arrayIntake.size();
    }

    public Object getItem(int paramInt) {
        return arrayIntake.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return 0L;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        View row = paramView;
        ViewIntakeHolder holder;
        if (paramView == null) {
            LayoutInflater localLayoutInflater = activity.getLayoutInflater();
            row = localLayoutInflater.inflate(R.layout.intake_list_listview_items, null);
            holder = new ViewIntakeHolder(row);
            row.setTag(holder);
        } else holder = (ViewIntakeHolder) row.getTag();
        holder.txtClgDetailListBranch.setText(this.arrayIntake.get(paramInt).getLvBranch());
        holder.txtClgDetailListSeat.setText(String.valueOf(this.arrayIntake.get(paramInt).getLvSeat()));
        holder.txtClgDetailListVecent.setText(String.valueOf(this.arrayIntake.get(paramInt).getLvVecent()));
        return paramView;
    }

    private static class ViewIntakeHolder {
        private final TextView txtClgDetailListBranch;
        private final TextView txtClgDetailListSeat;
        private final TextView txtClgDetailListVecent;

        private ViewIntakeHolder(View paramView) {
            txtClgDetailListBranch = paramView.findViewById(R.id.intakedetail_listview_branch);
            txtClgDetailListSeat = paramView.findViewById(R.id.intakedetail_listview_seat);
            txtClgDetailListVecent = paramView.findViewById(R.id.intakedeatail_listview_vecant);
        }
    }
}
