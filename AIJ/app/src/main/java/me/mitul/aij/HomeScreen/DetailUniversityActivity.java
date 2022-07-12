package me.mitul.aij.HomeScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import me.mitul.aij.Bean.BeanUniversity;
import me.mitul.aij.Helper.HelperUniversity;
import me.mitul.aij.R;

public class DetailUniversityActivity extends Activity {
    private TextView universityID;
    private final HelperUniversity myDbHelper = new HelperUniversity(DetailUniversityActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_university);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final String id = getIntent().getStringExtra(getString(R.string.id_university_to_find));
        BeanUniversity localBeanBeanList = myDbHelper.selectUniversityByID(Integer.parseInt(id));
        universityID = findViewById(R.id.universitydetail_tv_university_id);
        universityID.setText(String.valueOf(localBeanBeanList.getUniversityID()));
        ((TextView) findViewById(R.id.universitydetail_tv_university_shortname)).setText(localBeanBeanList.getUniversityShortName());
        ((TextView) findViewById(R.id.universitydetail_tv_university_fullname)).setText(localBeanBeanList.getUniversityName());
        ((TextView) findViewById(R.id.universitydetail_tv_university_address)).setText(localBeanBeanList.getUniversityAddress());
        ((TextView) findViewById(R.id.universitydetail_tv_university_phone)).setText(localBeanBeanList.getUniversityPhone());
        ((TextView) findViewById(R.id.universitydetail_tv_university_website)).setText(localBeanBeanList.getUniversityWebsite());
        ((TextView) findViewById(R.id.universitydetail_tv_university_email)).setText(localBeanBeanList.getUniversityEmail());
        ((TextView) findViewById(R.id.universitydetail_tv_university_type)).setText(localBeanBeanList.getUniversityType());
        Button universityButton = findViewById(R.id.universitydetail_tv_university_button);
        universityButton.setText(String.format(getString(R.string.collge_in_ss), localBeanBeanList.getUniversityShortName()));

        universityButton.setOnClickListener(v -> startActivity(new Intent(DetailUniversityActivity.this, CollageListActivity.class).putExtra(getString(R.string.selected_or_all_collages), getString(R.string.university_id_to_find_university)).putExtra(getString(R.string.id_university_to_find), universityID.getText().toString())));
    }
}