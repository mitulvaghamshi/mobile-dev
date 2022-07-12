package me.mitul.dankawala.design

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import me.mitul.dankawala.R
import me.mitul.dankawala.adapter.MemberAdapter
import me.mitul.dankawala.helper.DetailHelper
import me.mitul.dankawala.model.MemberModel

class MemberActivity : AppCompatActivity() {
    private var edSearch: AutoCompleteTextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_member)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val mMemberArray = DetailHelper(this@MemberActivity).memberData
        val mListView = findViewById<ListView>(R.id.member_listview)
        edSearch = findViewById(R.id.ed_search_member)
        mListView.adapter = MemberAdapter(this@MemberActivity, mMemberArray)
        val fab = findViewById<FloatingActionButton>(R.id.fab_add_member)
        fab.setOnClickListener { insertNewMember() }
        //TODO: a Text Listener Called to filter data[current by date only]...
        edSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(
                paramAnonymousCharSequence: CharSequence,
                paramAnonymousInt1: Int,
                paramAnonymousInt2: Int,
                paramAnonymousInt3: Int
            ) {
                val str = edSearch?.text.toString().toLowerCase(Locale.ROOT)
                val tMemberArray = ArrayList<MemberModel>()
                if (str.isNotEmpty()) {
                    for (i in mMemberArray.indices) if (mMemberArray[i].getmMemberName()
                            ?.toLowerCase(Locale.ROOT)
                            ?.startsWith(str)!! || mMemberArray[i].getmMemberName()
                            ?.toLowerCase(Locale.ROOT)?.contains(str)!!
                    ) tMemberArray.add(mMemberArray[i])
                    mListView.adapter = MemberAdapter(this@MemberActivity, tMemberArray)
                } else mListView.adapter = MemberAdapter(this@MemberActivity, mMemberArray)
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun insertNewMember() {
        val mDialogMemberView = LayoutInflater.from(this).inflate(R.layout.item_dialog, null)
        val mMemberName =
            mDialogMemberView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_1)
        mMemberName.hint = "Enter name"
        val mMemberContact =
            mDialogMemberView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_2)
        mMemberContact.hint = "Enter contact(optional)"
        mMemberContact.visibility = View.VISIBLE
        mMemberContact.inputType = EditorInfo.TYPE_CLASS_NUMBER
        val mMemberCity =
            mDialogMemberView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_3)
        mMemberCity.hint = "Enter city(optional)"
        mMemberCity.visibility = View.VISIBLE
        AlertDialog.Builder(this)
            .setTitle("Add new Member...")
            .setView(mDialogMemberView)
            .setPositiveButton("Add") { _, _ ->
                if (DetailHelper(this@MemberActivity).insertMember(
                        mMemberName.text.toString(),
                        mMemberContact.text.toString(),
                        mMemberCity.text.toString()
                    ) == 0
                ) recreate()
            }.setNegativeButton("Cancel", null).create().show()
    }
}
