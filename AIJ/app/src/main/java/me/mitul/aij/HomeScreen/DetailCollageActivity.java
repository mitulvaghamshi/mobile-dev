package me.mitul.aij.HomeScreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import me.mitul.aij.Bean.BeanCollage;
import me.mitul.aij.Helper.HelperCollage;
import me.mitul.aij.R;

public class DetailCollageActivity extends Activity {
    private final HelperCollage myDbHelper = new HelperCollage(DetailCollageActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_collage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final String id = getIntent().getStringExtra(getString(R.string.id_to_find));
        final BeanCollage localBeanCollageList = myDbHelper.selectCollageByID(Integer.parseInt(id));
        ((TextView) findViewById(R.id.collegedetail_tv_collegelabel)).setText(localBeanCollageList.getLabel());
        ((TextView) findViewById(R.id.collegedetail_tv_collegeshortname)).setText(localBeanCollageList.getClgSortName());
        ((TextView) findViewById(R.id.collegedetail_tv_collegefullname)).setText(localBeanCollageList.getClgFullName());
        ((TextView) findViewById(R.id.collegedetail_tv_collegeaddress)).setText(localBeanCollageList.getClgAddres());
        ((TextView) findViewById(R.id.collegedetail_tv_phone_value)).setText(localBeanCollageList.getPhone());
        ((TextView) findViewById(R.id.collegedetail_tv_website_value)).setText(localBeanCollageList.getWeb());
        ((TextView) findViewById(R.id.collegedetail_tv_email_value)).setText(localBeanCollageList.getEmail());
        ((TextView) findViewById(R.id.collegedetail_tv_fees_value)).setText(String.format(getString(R.string.fees_collage), localBeanCollageList.getFees()));
        ((TextView) findViewById(R.id.collegedetail_tv_collegetype_value)).setText(localBeanCollageList.getType());
        ((TextView) findViewById(R.id.collegedetail_tv_collegetype_hostel)).setText(localBeanCollageList.getHostel());
        ((TextView) findViewById(R.id.collegedetail_tv_collegetype_university)).setText(localBeanCollageList.getUniversity());
        DetailCollageActivity.this.setTitle(getString(R.string.collage_code) + (((TextView) findViewById(R.id.collegedetail_tv_collegelabel))).getText());

       /* findViewById(R.id.collegedetail_btn_Intake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailCollageActivity.this, IntakeDetailActivity.class).putExtra(getString(R.string.id_intake_to_find), id));
            }
        });

        findViewById(R.id.collegedetail_btn_closing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailCollageActivity.this, AllClosingActivity.class).putExtra(getString(R.string.id_to_find_closing), id).putExtra(getString(R.string.closing_collage_name), localBeanCollageList.getClgFullName()));
            }
        });*/
    }
}