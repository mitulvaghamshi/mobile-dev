package me.mitul.dankawala.design

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*
import me.mitul.dankawala.R
import me.mitul.dankawala.adapter.DetailAdapter
import me.mitul.dankawala.adapter.MonthAdapter
import me.mitul.dankawala.adapter.PartnerAdapter
import me.mitul.dankawala.adapter.ShopAdapter
import me.mitul.dankawala.adapter.SpinMemAdapter
import me.mitul.dankawala.adapter.SpinYearAdapter
import me.mitul.dankawala.helper.DetailHelper
import me.mitul.dankawala.model.DetailModel
import me.mitul.dankawala.model.MonthModel
import me.mitul.dankawala.model.ShopModel
import me.mitul.dankawala.model.SpinPartnerModel
import me.mitul.dankawala.model.SpinYearModel
import me.mitul.dankawala.utility.Utils

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mCurrentMonthID = 0
    private var mMonthProfit = 0
    private var mDetailHelper: DetailHelper? = null
    private var mListViewShop: ListView? = null
    private var mListViewMonth: ListView? = null
    private var mLoginChecked = false
    private var mBackupChecked = false
    private var mHomeListText: TextView? = null
    private var mMonthListText: TextView? = null
    private var mFabAddNewRecord: FloatingActionButton? = null
    private var mFabExit: FloatingActionButton? = null
    private var errorColor = 0
    private var warnColor = 0
    private var normalColor = 0
    private var successColor = 0
    var mDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        mDialog = ProgressDialog(this)
        mDialog!!.setCanceledOnTouchOutside(false)
        mDialog!!.setMessage("Loading...")
        mHomeListText = findViewById(R.id.home_list_text)
        mMonthListText = findViewById(R.id.month_list_text)
        mFabAddNewRecord = findViewById(R.id.fab_add_new_record)
        mFabAddNewRecord?.setOnClickListener(View.OnClickListener {
            Handler().post {
                if (mShopID == 0) {
                    mHomeListText?.text = "please select shop"
                    mHomeListText?.setTextColor(errorColor)
                } else if (mMonthID == 0) {
                    mMonthID = mCurrentMonthID
                    mMonthListText?.text = String.format("current month: %s", mCurrentMonthID)
                    mMonthListText?.setTextColor(successColor)
                    insertNewItem(mShopID, mCurrentYearID, mMonthID)
                } else insertNewItem(mShopID, mCurrentYearID, mMonthID)
            }
        })
        mFabExit = findViewById(R.id.fab_exit)
        mFabExit?.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Do you want to exit?")
                .setIcon(R.drawable.ic_power)
                .setMultiChoiceItems(
                    arrayOf<CharSequence>("Log out now...?", "Backup Database...?"),
                    booleanArrayOf(false, false)
                ) { dialog, indexSelected, isChecked ->
                    when (indexSelected) {
                        0 -> {
                            mLoginChecked = isChecked
                            mBackupChecked = isChecked
                        }
                        1 -> mBackupChecked = isChecked
                    }
                }
                .setPositiveButton("Yes, Exit!") { _, _ ->
                    if (mBackupChecked) Utils(this@MainActivity).backupDB()
                    if (mLoginChecked) getSharedPreferences(
                        "DANKAWALA_PREFERENCES",
                        Context.MODE_PRIVATE
                    ).edit()
                        .putBoolean("KEEP_LOGGED_IN", false)
                        .putString("Email1", "mady@me")
                        .putString("Pass1", "me@mady").apply()
                    finish()
                }.setNeutralButton("Refresh!") { _, _ -> recreate() }
                .setNegativeButton("No, Go back!", null).create().show()
        })
        mFabExit?.setOnLongClickListener(OnLongClickListener {
            recreate()
            true
        })
        //TODO: Shop ListView
        mListViewShop = findViewById(R.id.home_listview)
        mListViewShop?.onItemClickListener = OnItemClickListener { parent, _, position, _ ->
            val mShopModel = parent.getItemAtPosition(position) as ShopModel
            mHomeListText?.text = mShopModel.getmHomeDankaName()
            mHomeListText?.setTextColor(normalColor)
            mMonthListText?.text = "please select month"
            mMonthListText?.setTextColor(errorColor)
            mShopID = mShopModel.getmHomeDnakaID()
            mMonthID = 0
            MonthTask().execute()
        }
        //TODO: Month ListView
        mListViewMonth = findViewById(R.id.month_listview)
        mListViewMonth?.onItemClickListener = OnItemClickListener { parent, _, position, _ ->
            val mMonthModel = parent.getItemAtPosition(position) as MonthModel
            mMonthListText?.text = mMonthModel.getmMonthName()
            mMonthListText?.setTextColor(normalColor)
            mMonthID = mMonthModel.getmMonthID()
            mMonthProfit = mMonthModel.getmMonthProfit()
            val mViewPager = findViewById<ViewPager>(R.id.home_viewpager)
            mViewPager.adapter = PagerAdapter(supportFragmentManager)
            val tabLayout = findViewById<TabLayout>(R.id.home_tabs)
            mViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))
            (findViewById<View>(R.id.month_tot_profit) as TextView).text =
                String.format("Profit: %s/-", mMonthProfit)
            Handler().post {
                val mMemberArray =
                    DetailHelper(this@MainActivity).getPartnerData(mShopID, mMonthProfit)
                val mListView = findViewById<ListView>(R.id.shop_keeper_listview)
                mListView.adapter = PartnerAdapter(this@MainActivity, mMemberArray)
            }
        }
        val mYearSpinner = findViewById<AppCompatSpinner>(R.id.year_spinner)
        mYearSpinner.adapter =
            SpinYearAdapter(this@MainActivity, DetailHelper(this@MainActivity).spinnerYearData)
        mYearSpinner.setSelection(mCurrentYearID - 19)
        mYearSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val tYearNum = (parent.getItemAtPosition(position) as SpinYearModel).yearNum
                if (tYearNum == 0) if (DetailHelper(this@MainActivity).addNewYear(mCurrentYearID + position) == 0) recreate()
                mCurrentYearID = tYearNum
                HomeTask().execute()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun addNewPartner() {
        if (mShopID == 0) {
            mHomeListText!!.text = "please select shop"
            mHomeListText!!.setTextColor(errorColor)
            return
        }
        val mDialogAddView =
            LayoutInflater.from(this@MainActivity).inflate(R.layout.item_dialog, null)
        val mPartnerShare =
            mDialogAddView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_1)
        mPartnerShare.hint = "Enter partner share(in 100%)"
        mPartnerShare.inputType = EditorInfo.TYPE_CLASS_NUMBER
        val mMemberSpinner: AppCompatSpinner = mDialogAddView.findViewById(R.id.member_spinner)
        mMemberSpinner.visibility = View.VISIBLE
        Handler().post {
            mMemberSpinner.adapter = SpinMemAdapter(
                this@MainActivity,
                DetailHelper(this@MainActivity).spinnerPartnerData
            )
        }
        val tMemberID = IntArray(1)
        mMemberSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                tMemberID[0] =
                    (parent.getItemAtPosition(position) as SpinPartnerModel).getmPartnerID()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        AlertDialog.Builder(this@MainActivity)
            .setTitle("Add Partner to " + mHomeListText!!.text.toString())
            .setIcon(R.drawable.ic_add_partner)
            .setView(mDialogAddView)
            .setPositiveButton("Add partner") { _, _ ->
                Handler().post {
                    if (DetailHelper(this@MainActivity).addPartner(
                            mShopID,
                            tMemberID[0],
                            if (TextUtils.isEmpty(mPartnerShare.text.toString())) 0 else mPartnerShare.text.toString()
                                .toInt()
                        ) != 0L
                    ) {
                        mHomeListText!!.text = "Successful!"
                        mHomeListText!!.setTextColor(successColor)
                        mMonthListText!!.text = "please refresh data"
                        mMonthListText!!.setTextColor(warnColor)
                    } else {
                        mHomeListText!!.text = "Failed!"
                        mHomeListText!!.setTextColor(errorColor)
                    }
                }
            }.setNegativeButton("Cancel", null).create().show()
    }

    private fun addNewShop() {
        val mDialogAddView =
            LayoutInflater.from(this@MainActivity).inflate(R.layout.item_dialog, null)
        val mShopName = mDialogAddView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_1)
        mShopName.hint = "Enter shop name"
        val mShopAddr = mDialogAddView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_2)
        mShopAddr.hint = "Enter shop address"
        mShopAddr.visibility = View.VISIBLE
        AlertDialog.Builder(this@MainActivity)
            .setTitle("Add new shop")
            .setIcon(R.drawable.ic_add_shop)
            .setView(mDialogAddView)
            .setPositiveButton("Add shop") { _, _ ->
                Handler().post {
                    if (DetailHelper(this@MainActivity).addNewShop(
                            if (TextUtils.isEmpty(mShopName.text.toString())) "New shop($mCurrentYearID)"
                            else mShopName.text.toString(),
                            if (TextUtils.isEmpty(mShopAddr.text.toString())) "no address provided"
                            else mShopAddr.text.toString()
                        ) == 0
                    ) {
                        mHomeListText!!.text = "Successful!"
                        mHomeListText!!.setTextColor(successColor)
                        mMonthListText!!.text = "please refresh data"
                        mMonthListText!!.setTextColor(warnColor)
                    } else {
                        mHomeListText!!.text = "Failed!"
                        mHomeListText!!.setTextColor(errorColor)
                    }
                }
            }.setNegativeButton("Cancel", null).create().show()
    }

    //TODO: Method to insert new (daily)record
    private fun insertNewItem(paramShopID: Int, paramYearID: Int, paramMonthID: Int) {
        val date = Calendar.getInstance().time
        val tDateID = SimpleDateFormat("dd", Locale.ENGLISH).format(date).toInt()
        val tDayName = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date)
        val tTempDate = "$tDateID/$paramMonthID/20$paramYearID"
        val mDialogAddView = LayoutInflater.from(this).inflate(R.layout.item_dialog, null)
        val mIncomeAmount =
            mDialogAddView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_1)
        mIncomeAmount.hint = "Enter income(counter)"
        mIncomeAmount.inputType = EditorInfo.TYPE_CLASS_NUMBER
        val mIncomeDesc =
            mDialogAddView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_2)
        mIncomeDesc.hint = "Enter description(use ,)"
        mIncomeDesc.visibility = View.VISIBLE
        val mOutcomeAmount =
            mDialogAddView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_3)
        mOutcomeAmount.hint = "Enter cost(purchases)"
        mOutcomeAmount.visibility = View.VISIBLE
        mOutcomeAmount.inputType = EditorInfo.TYPE_CLASS_NUMBER
        val mOutcomeDesc =
            mDialogAddView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_4)
        mOutcomeDesc.hint = "Enter description(use ,)"
        mOutcomeDesc.visibility = View.VISIBLE
        val tDateArray = intArrayOf(tDateID, paramMonthID - 1, paramYearID)
        val mCalendarView = CalendarView(this@MainActivity)
        mCalendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            tDateArray[0] = dayOfMonth
            tDateArray[1] = month
            tDateArray[2] = year
        }
        val mDateChangeView = mDialogAddView.findViewById<Button>(R.id.date_change_view)
        mDateChangeView.text =
            String.format("Today: %s/%s/%s Change?", tDateID, paramMonthID, paramYearID)
        mDateChangeView.visibility = View.VISIBLE
        mDateChangeView.tag = tDayName
        mDateChangeView.setOnClickListener {
            if (mCalendarView.parent != null) (mCalendarView.parent as ViewGroup).removeView(
                mCalendarView
            )
            AlertDialog.Builder(this@MainActivity)
                .setIcon(R.drawable.ic_date_range)
                .setTitle("Select new Date")
                .setView(mCalendarView)
                .setPositiveButton("Ok") { _, _ ->
                    val mCalendar1 = Calendar.getInstance()
                    mCalendar1[tDateArray[2], tDateArray[1]] = tDateArray[0]
                    mDateChangeView.text = String.format(
                        "Selected date: %s/%s/%s",
                        tDateArray[0],
                        tDateArray[1] + 1,
                        tDateArray[2]
                    )
                    mDateChangeView.tag =
                        SimpleDateFormat("EEEE", Locale.ENGLISH).format(mCalendar1.time)
                }.show()
        }
        AlertDialog.Builder(this)
            .setTitle(String.format("Today: %s(%s)", tTempDate, mHomeListText!!.text.toString()))
            .setIcon(R.drawable.ic_add_record)
            .setView(mDialogAddView)
            .setPositiveButton("Add") { _, _ ->
                if (DetailHelper(this@MainActivity).insertDetailData(
                        if (TextUtils.isEmpty(mIncomeAmount.text.toString())) 0
                        else mIncomeAmount.text.toString().toInt(),
                        if (TextUtils.isEmpty(mOutcomeAmount.text.toString())) 0
                        else mOutcomeAmount.text.toString().toInt(),
                        if (TextUtils.isEmpty(mIncomeDesc.text.toString())) "no description"
                        else mIncomeDesc.text.toString(),
                        if (TextUtils.isEmpty(mOutcomeDesc.text.toString())) "no description"
                        else mOutcomeDesc.text.toString(),
                        paramShopID,
                        tDateArray[2] % 100,
                        tDateArray[1] + 1,
                        tDateArray[0],
                        mDateChangeView.tag.toString()
                    ) == 999
                ) {
                    mHomeListText!!.text = tTempDate
                    mHomeListText!!.setTextColor(warnColor)
                    mMonthListText!!.text = "Record already exists"
                    mMonthListText!!.setTextColor(errorColor)
                } else {
                    mHomeListText!!.text = "Successful!"
                    mHomeListText!!.setTextColor(successColor)
                    mMonthListText!!.text = "please refresh data"
                    mMonthListText!!.setTextColor(warnColor)
                }
            }.setNegativeButton("Go back", null).create().show()
    }

    override fun onStart() {
        super.onStart()
        mShopID = 0
        mMonthID = 0
        mDetailHelper = DetailHelper(this)
        mCurrentYearID =
            SimpleDateFormat("yy", Locale.ENGLISH).format(Calendar.getInstance().time).toInt()
        mCurrentMonthID =
            SimpleDateFormat("MM", Locale.ENGLISH).format(Calendar.getInstance().time).toInt()
        errorColor = getColor(R.color.color_red1)
        warnColor = getColor(R.color.color_yellow1)
        normalColor = getColor(android.R.color.black)
        successColor = getColor(R.color.color_green1)
        mHomeListText!!.setTextColor(normalColor)
        mMonthListText!!.setTextColor(normalColor)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START)
        else mFabExit!!.callOnClick()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_insert_record -> return mFabAddNewRecord!!.callOnClick()
            R.id.nav_add_shop -> addNewShop()
            R.id.nav_add_partner -> addNewPartner()
            R.id.nav_summery -> startActivity(
                Intent(
                    this@MainActivity,
                    SummeryActivity::class.java
                ).putExtra("YEAR_NUM", mCurrentYearID)
            )
            R.id.nav_member -> startActivity(Intent(this@MainActivity, MemberActivity::class.java))
            R.id.nav_backup_db -> Handler().post { Utils(this@MainActivity).backupDB() }
            R.id.nav_restore_db -> {
                Handler().post {
                    Utils(this@MainActivity).restoreDb()
                    getPreferences(Context.MODE_PRIVATE).edit().putBoolean("FIRST_RUN", false)
                        .apply()
                }
                recreate()
            }
            R.id.nav_go_user -> startActivity(Intent(this@MainActivity, UserActivity::class.java))
            R.id.nav_about_us -> {
            }
            R.id.nav_help -> startActivity(Intent(this@MainActivity, HelpActivity::class.java))
            R.id.nav_restart -> recreate()
            R.id.nav_exit -> return mFabExit!!.callOnClick()
        }
        return true
    }

    //TODO: Adapter for ViewPager
    class PagerAdapter internal constructor(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            return PagerFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            return 2
        }
    }

    //TODO: class to build Fragments
    class PagerFragment : Fragment() {
        private var mAdapter: ExpandableListAdapter? = null
        private var mExpListView: ExpandableListView? = null
        private var mHeaderList: List<DetailModel>? = null
        private var mChildList: HashMap<DetailModel, Array<String>>? = null
        private var mEdSearch: EditText? = null
        private var mTextViewTotal: TextView? = null
        private var mInflateView: View? = null
        private var mIsIncome = false
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.list_detail, container, false)
                .also { mInflateView = it }
        }

        override fun onStart() {
            super.onStart()
            mExpListView = mInflateView!!.findViewById(R.id.exp_listview_detail)
            mEdSearch = mInflateView!!.findViewById(R.id.ed_search_detail)
            mTextViewTotal = mInflateView!!.findViewById(R.id.month_total_detail)
            mTextViewTotal?.setTextColor(
                if (arguments!!.getInt("SECTION_NUM") == 1) {
                    mIsIncome = true
                    resources.getColor(R.color.color_green1)
                } else resources.getColor(R.color.color_red1)
            )
            DetailTask(context).execute()

            //TODO: Listener called to filter data by date
            mEdSearch?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(
                    paramAnonymousCharSequence: CharSequence,
                    paramAnonymousInt1: Int,
                    paramAnonymousInt2: Int,
                    paramAnonymousInt3: Int
                ) {
                    val tValue = mEdSearch?.text.toString()
                    val tHeaderList = ArrayList<DetailModel>()
                    if (!tValue.isEmpty()) {
                        for (i in mHeaderList!!.indices) if (mHeaderList!![i].getmDetailDate()
                                ?.substring(0, 2)?.startsWith(tValue)!!
                        ) tHeaderList.add(mHeaderList!![i])
                        mExpListView?.setAdapter(
                            DetailAdapter(
                                activity!!,
                                tHeaderList,
                                mChildList!!,
                                mIsIncome
                            )
                        )
                    } else mExpListView?.setAdapter(
                        DetailAdapter(
                            activity!!,
                            mHeaderList!!,
                            mChildList!!,
                            mIsIncome
                        )
                    )
                }

                override fun afterTextChanged(editable: Editable) {}
            })
        }

        //TODO: Background Task to retrieve detail data
        private inner class DetailTask(mContext: Context?) :
            AsyncTask<Void?, Void?, Void?>() {
            private val tDetailHelper: DetailHelper = DetailHelper(mContext)
            private val tDialog = Utils(mContext!!).dialog
            private var mMonthTotal = 0
            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg voids: Void?): Void? {
                mMonthTotal =
                    tDetailHelper.getMonthTotal(false, mIsIncome, mShopID, mCurrentYearID, mMonthID)
                mHeaderList =
                    tDetailHelper.getDetailData(mIsIncome, mShopID, mCurrentYearID, mMonthID)
                mChildList = HashMap()
                for (detailModel in mHeaderList!!) mChildList!![detailModel] =
                    detailModel.getmDetailDescription()?.split(",")?.toTypedArray()!!
                try {
                    Thread.sleep(1000)
                } catch (ignored: InterruptedException) {
                }
                return null
            }

            @Deprecated("Deprecated in Java")
            override fun onPreExecute() {
                super.onPreExecute()
                tDialog.show()
            }

            @Deprecated("Deprecated in Java")
            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                mTextViewTotal!!.text = String.format(
                    "Total %s: %s/-",
                    if (mIsIncome) "Income" else "Cost",
                    mMonthTotal
                )
                mAdapter = DetailAdapter(activity!!, mHeaderList!!, mChildList!!, mIsIncome)
                mExpListView!!.setAdapter(mAdapter)
                tDialog.dismiss()
            }

        }

        companion object {
            fun newInstance(paramNumber: Int): PagerFragment {
                val mFragment = PagerFragment()
                val mArgs = Bundle()
                mArgs.putInt("SECTION_NUM", paramNumber)
                mFragment.arguments = mArgs
                return mFragment
            }
        }
    }

    //TODO: Background Task to retrieve month names
    private inner class MonthTask : AsyncTask<Void?, Void?, Void?>() {
        private var mMonthArray: ArrayList<MonthModel>? = null
        private val tDialog = Utils(this@MainActivity).dialog
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg voids: Void?): Void? {
            mMonthArray = mDetailHelper!!.getMonthNames(mShopID, mCurrentYearID)
            try {
                Thread.sleep(1000)
            } catch (ignored: InterruptedException) {
            }
            return null
        }

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            tDialog.show()
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            mListViewMonth!!.adapter =
                MonthAdapter(this@MainActivity, mMonthArray!!, mCurrentMonthID)
            tDialog.dismiss()
        }
    }

    //TODO: Background Task to retrieve month names
    private inner class HomeTask : AsyncTask<Void?, Void?, Void?>() {
        private var mShopArray: ArrayList<ShopModel>? = null
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg voids: Void?): Void? {
            mShopArray = mDetailHelper!!.shopNames
            try {
                Thread.sleep(1000)
            } catch (ignored: InterruptedException) {
            }
            return null
        }

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            mDialog!!.show()
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            mListViewShop!!.adapter = ShopAdapter(this@MainActivity, mShopArray!!)
            mDialog!!.dismiss()
        }
    }

    companion object {
        private var mShopID = 0
        private var mMonthID = 0
        private var mCurrentYearID = 0
    }
}
