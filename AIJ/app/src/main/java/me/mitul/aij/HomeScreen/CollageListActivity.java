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

import me.mitul.aij.Adapter.AdapterCollage;
import me.mitul.aij.Bean.BeanCollage;
import me.mitul.aij.Helper.HelperCollage;
import me.mitul.aij.R;

public class CollageListActivity extends Activity {
    private EditText edSearch;
    private final HelperCollage myDbHelper = new HelperCollage(CollageListActivity.this);
    private ArrayList<BeanCollage> list;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_listview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final ListView listview = findViewById(R.id.common_listview);
        listview.setVisibility(View.VISIBLE);
        findViewById(R.id.expandableListView).setVisibility(View.GONE);
        edSearch = findViewById(R.id.edSearchCommon);

        Intent getter = getIntent();
        String which = getter.getStringExtra(getString(R.string.selected_or_all_collages));
        if (which.equals(getString(R.string.retrive_all_collages)))
            list = myDbHelper.selectAllCollage();
        else if (which.equals(getString(R.string.branch_id))) {
            String which_id = getter.getStringExtra(getString(R.string.id_branch_collage));
            list = myDbHelper.selectBranchWiseCollage(Integer.parseInt(which_id));
        } else if (which.equals(getString(R.string.university_id_to_find_university))) {
            String which_id = getter.getStringExtra(getString(R.string.id_university_to_find));
            list = myDbHelper.selectUniversityWiseCollage(Integer.parseInt(which_id));
        }
        listview.setAdapter(new AdapterCollage(CollageListActivity.this, list));
        listview.setTextFilterEnabled(true);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                String str = edSearch.getText().toString().toLowerCase(Locale.getDefault());
                ArrayList<BeanCollage> list1 = new ArrayList<BeanCollage>();
                listview.setAdapter(new AdapterCollage(CollageListActivity.this, list1));
                if (!str.isEmpty()) {
                    for (int i = 0; i < list.size(); i++)
                        if (list.get(i).getCollageName().toLowerCase(Locale.getDefault()).startsWith(str) || list.get(i).getCollageName().toLowerCase(Locale.getDefault()).contains(str))
                            list1.add(list.get(i));
                    listview.setAdapter(new AdapterCollage(CollageListActivity.this, list1));
                } else
                    listview.setAdapter(new AdapterCollage(CollageListActivity.this, list));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        listview.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(CollageListActivity.this, DetailCollageActivity.class).putExtra(getString(R.string.id_to_find), ((TextView) view.findViewById(R.id.collage_list_item_collage_id)).getText().toString())));
    }
}
