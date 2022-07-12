package me.mitul.aij.HomeScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import me.mitul.aij.Adapter.AdapterBranch;
import me.mitul.aij.Bean.BeanBranch;
import me.mitul.aij.Helper.HelperBranch;
import me.mitul.aij.R;

public class BranchListActivity extends Activity {
    private EditText edSearchBranchList;
    private final HelperBranch myDbHelper = new HelperBranch(BranchListActivity.this);
    private ArrayList<BeanBranch> list;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_listview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final ListView listview = findViewById(R.id.common_listview);
        listview.setVisibility(View.VISIBLE);
        findViewById(R.id.expandableListView).setVisibility(View.GONE);
        edSearchBranchList = findViewById(R.id.edSearchCommon);
        list = myDbHelper.selectAllBranch();
        listview.setAdapter(new AdapterBranch(BranchListActivity.this, list));
        listview.setTextFilterEnabled(true);

        edSearchBranchList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                String str = edSearchBranchList.getText().toString().toLowerCase(Locale.getDefault());
                ArrayList<BeanBranch> list1 = new ArrayList<>();
                listview.setAdapter(new AdapterBranch(BranchListActivity.this, list1));
                if (!str.isEmpty()) {
                    for (int i = 0; i < list.size(); i++)
                        if ((list.get(i).getBranchName().toLowerCase(Locale.getDefault()).startsWith(str)) || list.get(i).getBranchName().toLowerCase(Locale.getDefault()).contains(str))
                            list1.add(list.get(i));
                    listview.setAdapter(new AdapterBranch(BranchListActivity.this, list1));
                } else
                    listview.setAdapter(new AdapterBranch(BranchListActivity.this, list));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        listview.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(BranchListActivity.this, CollageListActivity.class).putExtra(getString(R.string.selected_or_all_collages), getString(R.string.branch_id)).putExtra(getString(R.string.id_branch_collage), ((TextView) view.findViewById(R.id.branch_list_item_branch_id)).getText().toString())));
    }
}
