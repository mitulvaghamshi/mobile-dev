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

import me.mitul.aij.Adapter.AdapterUniversity;
import me.mitul.aij.Bean.BeanUniversity;
import me.mitul.aij.Helper.HelperUniversity;
import me.mitul.aij.R;

public class UniversityListActivity extends Activity {
    private EditText edSearch;
    private final HelperUniversity mDbHelper = new HelperUniversity(UniversityListActivity.this);
    private ArrayList<BeanUniversity> list;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_listview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final ListView listview = findViewById(R.id.common_listview);
        listview.setVisibility(View.VISIBLE);
        findViewById(R.id.expandableListView).setVisibility(View.GONE);
        edSearch = findViewById(R.id.edSearchCommon);
        list = mDbHelper.selectAllUniversity();
        listview.setAdapter(new AdapterUniversity(UniversityListActivity.this, list));
        listview.setTextFilterEnabled(true);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                String str = edSearch.getText().toString().toLowerCase(Locale.getDefault());
                ArrayList<BeanUniversity> list1 = new ArrayList<>();
                listview.setAdapter(new AdapterUniversity(UniversityListActivity.this, list1));
                if (!str.isEmpty()) {
                    for (int i = 0; i < list.size(); i++)
                        if (list.get(i).getUniversityName().toLowerCase(Locale.getDefault()).startsWith(str) || list.get(i).getUniversityName().toLowerCase(Locale.getDefault()).contains(str))
                            list1.add(list.get(i));
                    listview.setAdapter(new AdapterUniversity(UniversityListActivity.this, list1));
                } else
                    listview.setAdapter(new AdapterUniversity(UniversityListActivity.this, list));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        listview.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(UniversityListActivity.this, DetailUniversityActivity.class).putExtra(getString(R.string.id_university_to_find), ((TextView) view.findViewById(R.id.list_simple_public_id)).getText().toString())));
    }
}
