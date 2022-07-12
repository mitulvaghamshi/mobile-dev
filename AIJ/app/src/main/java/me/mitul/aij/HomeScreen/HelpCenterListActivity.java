package me.mitul.aij.HomeScreen;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
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
import me.mitul.aij.Helper.HelperHelpCenter;
import me.mitul.aij.R;

public class HelpCenterListActivity extends Activity {
    private EditText edSearch;
    private final HelperHelpCenter mDbHelper = new HelperHelpCenter(HelpCenterListActivity.this);
    private List<String> list;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<BeanCommon>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_listview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        expListView = findViewById(R.id.expandableListView);
        expListView.setVisibility(View.VISIBLE);
        findViewById(R.id.common_listview).setVisibility(View.GONE);
        edSearch = findViewById(R.id.edSearchCommon);
        prepareListData();
        ExpandableListAdapter listAdapter = new AdapterExpandableList(this, listDataHeader, listDataChild);
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
                expListView.setAdapter(new AdapterExpandableList(HelpCenterListActivity.this, list1, listDataChild));
                if (!str.isEmpty()) {
                    for (int i = 0; i < list.size(); i++)
                        if (list.get(i).toLowerCase(Locale.getDefault()).startsWith(str) || (list.get(i)).toLowerCase(Locale.getDefault()).contains(str))
                            list1.add(list.get(i));
                    expListView.setAdapter(new AdapterExpandableList(HelpCenterListActivity.this, list1, listDataChild));
                } else
                    expListView.setAdapter(new AdapterExpandableList(HelpCenterListActivity.this, list, listDataChild));
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
            PopupMenu popup = new PopupMenu(HelpCenterListActivity.this, v);
            popup.getMenu().add(listDataHeader.get(groupPosition));
            popup.getMenu().add((listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)).getCommonAddress());
            //popup.getMenuInflater().inflate(R.menu.popup_menu,);
            popup.setOnMenuItemClickListener(item -> {
                Toast.makeText(HelpCenterListActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });
            popup.show();
            return false;
        });
    }

    private void prepareListData() {
        listDataHeader = mDbHelper.selectCityForHelpCenter();
        listDataChild = new HashMap<>();
        list = listDataHeader;
        for (String name1 : listDataHeader)
            listDataChild.put(name1, new ArrayList<>(mDbHelper.selectHelpCenter(name1)));
    }
}
