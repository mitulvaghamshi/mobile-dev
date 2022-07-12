package me.mitul.aij.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import me.mitul.aij.Bean.BeanCommon;
import me.mitul.aij.R;

public class AdapterExpandableList extends BaseExpandableListAdapter {
    private final Context context;
    private final List<String> listDataHeader;
    private final HashMap<String, List<BeanCommon>> listDataChild;

    public AdapterExpandableList(Context c, List<String> l, HashMap<String, List<BeanCommon>> b) {
        context = c;
        listDataHeader = l;
        listDataChild = b;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        BeanCommon childText2 = (BeanCommon) getChild(groupPosition, childPosition);
        ViewExpHolder holder;
        if (convertView == null) {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.simple_two_tv_listview_items, null);
            holder = new ViewExpHolder(convertView);
            convertView.setTag(holder);
        } else holder = (ViewExpHolder) convertView.getTag();
        holder.txtListChild.setText(childText2.getCommonName());
        holder.txtListChild2.setText(childText2.getCommonAddress());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (listDataChild.get(listDataHeader.get(groupPosition)).size());
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null)
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.simple_one_tv_listview_items, null);
        TextView lblListHeader = convertView.findViewById(R.id.list_simple_public_name);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewExpHolder {
        private final TextView txtListChild;
        private final TextView txtListChild2;

        private ViewExpHolder(View v) {
            txtListChild = v.findViewById(R.id.list_simple_two_tv_listview_name);
            txtListChild2 = v.findViewById(R.id.list_simple_two_tv_listview_address);
        }
    }
}
