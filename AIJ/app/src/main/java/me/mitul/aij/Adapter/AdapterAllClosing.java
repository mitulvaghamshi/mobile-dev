package me.mitul.aij.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanAllClosingList;
import me.mitul.aij.R;

public class AdapterAllClosing extends BaseAdapter {
    private final Activity activity;
    private final ArrayList<BeanAllClosingList> arrayClosing;

    public AdapterAllClosing(ArrayList<BeanAllClosingList> b, Activity a) {
        arrayClosing = b;
        activity = a;
    }

    @Override
    public int getCount() {
        return arrayClosing.size();
    }

    @Override
    public long getItemId(int paramInt) {
        return 0L;
    }

    @Override
    public Object getItem(int paramInt) {
        return arrayClosing.get(paramInt);
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup viewGroup) {
        View intakeRow = paramView;
        ViewAllClosingHolder collageIntakeHolder = null;
        if (intakeRow == null) {
            LayoutInflater localLayoutInflater = activity.getLayoutInflater();
            intakeRow = localLayoutInflater.inflate(R.layout.closing_list_listview_items, null);
            collageIntakeHolder = new ViewAllClosingHolder(intakeRow);
            intakeRow.setTag(collageIntakeHolder);
        } else collageIntakeHolder = (ViewAllClosingHolder) intakeRow.getTag();
        collageIntakeHolder.closingTextViewBranchName.setText(arrayClosing.get(paramInt).getClosingBranchName());
        collageIntakeHolder.closingTextViewTfwsValue.setText(String.valueOf(arrayClosing.get(paramInt).getClosingTfwsValue()));
        collageIntakeHolder.closingTextViewOpenValue.setText(String.valueOf(arrayClosing.get(paramInt).getClosingOpenValue()));
        collageIntakeHolder.closingTextViewSebcValue.setText(String.valueOf(arrayClosing.get(paramInt).getClosingSebcValue()));
        collageIntakeHolder.closingTextViewEbcValue.setText(String.valueOf(arrayClosing.get(paramInt).getClosingEbcValue()));
        collageIntakeHolder.closingTextViewScValue.setText(String.valueOf(arrayClosing.get(paramInt).getClosingScValue()));
        collageIntakeHolder.closingTextViewStValue.setText(String.valueOf(arrayClosing.get(paramInt).getClosingStValue()));
        return paramView;
    }

    private class ViewAllClosingHolder {
        private final TextView closingTextViewBranchName;
        private final TextView closingTextViewTfwsValue;
        private final TextView closingTextViewOpenValue;
        private final TextView closingTextViewEbcValue;
        private final TextView closingTextViewSebcValue;
        private final TextView closingTextViewScValue;
        private final TextView closingTextViewStValue;

        private ViewAllClosingHolder(View paramView) {
            closingTextViewBranchName = paramView.findViewById(R.id.allclosing_tv_branch);
            closingTextViewTfwsValue = paramView.findViewById(R.id.allclosing_tv_tfw);
            closingTextViewOpenValue = paramView.findViewById(R.id.allclosing_tv_open);
            closingTextViewSebcValue = paramView.findViewById(R.id.allclosing_tv_sebc);
            closingTextViewEbcValue = paramView.findViewById(R.id.allclosing_tv_ebc);
            closingTextViewScValue = paramView.findViewById(R.id.allclosing_tv_sc);
            closingTextViewStValue = paramView.findViewById(R.id.allclosing_tv_st1);
        }
    }
}
