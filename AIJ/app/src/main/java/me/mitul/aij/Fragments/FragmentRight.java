package me.mitul.aij.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import me.mitul.aij.Adapter.AdapterExpandableList;
import me.mitul.aij.Bean.BeanCommon;
import me.mitul.aij.Helper.HelperBankBranch;
import me.mitul.aij.R;

public class FragmentRight extends Fragment {
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<BeanCommon>> listDataChild;
    private EditText edSearch;
    private final HelperBankBranch mDbHelper = new HelperBankBranch(getContext());
    private List<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_common_listview1, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        expListView = getActivity().findViewById(R.id.expandableListView1);
        expListView.setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.common_listview1).setVisibility(View.GONE);
        edSearch = getActivity().findViewById(R.id.edSearchCommon1);
        prepareListData();
        ExpandableListAdapter listAdapter = new AdapterExpandableList(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setTag(-1);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                String str = edSearch.getText().toString().toLowerCase(Locale.getDefault());
                ArrayList<String> list1 = new ArrayList<>();
                expListView.setAdapter(new AdapterExpandableList(getActivity(), list1, listDataChild));
                if (!str.isEmpty()) {
                    for (int i = 0; i < list.size(); i++)
                        if (list.get(i).toLowerCase(Locale.getDefault()).startsWith(str) || (list.get(i)).toLowerCase(Locale.getDefault()).contains(str))
                            list1.add(list.get(i));
                    expListView.setAdapter(new AdapterExpandableList(getActivity(), list1, listDataChild));
                } else
                    expListView.setAdapter(new AdapterExpandableList(getActivity(), list, listDataChild));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        expListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            //   Toast.makeText(getApplicationContext(), "Group Clicked " + listDataHeader.get(groupPosition), Toast.LENGTH_SHORT).show();
            return false;
        });

        expListView.setOnGroupExpandListener(groupPosition -> {
            int tempPos = (int) expListView.getTag();
            if (tempPos != groupPosition && tempPos != -1) expListView.collapseGroup(tempPos);
            expListView.setTag(groupPosition);
            //   Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
        });

        expListView.setOnGroupCollapseListener(groupPosition -> {
            //    Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
        });

        expListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            PopupMenu popup = new PopupMenu(getActivity(), v);
            popup.getMenu().add(listDataHeader.get(groupPosition));
            popup.getMenu().add((listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)).getCommonAddress());
            //popup.getMenuInflater().inflate(R.menu.popup_menu,);
            popup.setOnMenuItemClickListener(item -> {
                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });
            popup.show();
            return false;
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataHeader = mDbHelper.selectCityForBankBranch();
        list = listDataHeader;
        for (String name1 : listDataHeader) {
            ArrayList<BeanCommon> ss = mDbHelper.selectBankBranch(name1);
            ArrayList<BeanCommon> top = new ArrayList<BeanCommon>(ss);
            listDataChild.put(name1, top);
        }
    }

	/*   //---Button view---
	 Button btnGetText = (Button) 
	 getActivity().findViewById(R.id.btnGetText);
	 btnGetText.setOnClickListener(new View.OnClickListener() {
	 public void onClick(View v) {
	 TextView lbl = (TextView)
	 getActivity().findViewById(R.id.lblFragment1);
	 Toast.makeText(getActivity(), lbl.getText(), 
	 Toast.LENGTH_SHORT).show();                
	 }
	 });
	   */
}
