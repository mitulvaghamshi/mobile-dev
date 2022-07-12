package me.mitul.dankawala.adapter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import me.mitul.dankawala.R
import me.mitul.dankawala.helper.DetailHelper
import me.mitul.dankawala.model.MemberModel

class MemberAdapter(
    private val mContext: Activity,
    private val mMemberArray: ArrayList<MemberModel>
) : BaseAdapter() {
    override fun getCount(): Int {
        return mMemberArray.size
    }

    override fun getItem(position: Int): Any {
        return mMemberArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val mHolder: MemeberViewHolder = convertView.tag as MemeberViewHolder
        mHolder.mMemberID.text = mMemberArray[position].getmMemberID().toString()
        mHolder.mMemberNum.text = mMemberArray[position].getmMemberNum().toString()
        mHolder.mMemberName.text = mMemberArray[position].getmMemberName()
        mHolder.mMemberContact.text = mMemberArray[position].getmMemberContact()
        mHolder.mMemberCity.text = mMemberArray[position].getmMemberCity()
        mHolder.mMemberCall.setOnClickListener {
            mContext.startActivity(
                Intent().setAction(Intent.ACTION_VIEW).setData(
                    Uri.parse(
                        String.format(
                            "tel:%s",
                            mHolder.mMemberContact.text.toString()
                        )
                    )
                )
            )
        }
        mHolder.mMemberEditView.setOnClickListener {
            val tID = mHolder.mMemberID.text.toString().toInt()
            val tName = mHolder.mMemberName.text.toString()
            val tContact = mHolder.mMemberContact.text.toString()
            val tCity = mHolder.mMemberCity.text.toString()
            val mDialogEditView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog, null)
            val mEditMemName =
                mDialogEditView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_1)
            mEditMemName.hint = "Enter name"
            mEditMemName.inputType = EditorInfo.TYPE_CLASS_TEXT
            mEditMemName.setText(tName)
            val mEditMemContact =
                mDialogEditView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_2)
            mEditMemContact.visibility = View.VISIBLE
            mEditMemContact.hint = "Enter contact"
            mEditMemContact.inputType = EditorInfo.TYPE_CLASS_NUMBER
            mEditMemContact.setText(tContact)
            val mEditMemCity =
                mDialogEditView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_3)
            mEditMemCity.visibility = View.VISIBLE
            mEditMemCity.hint = "Enter city"
            mEditMemCity.inputType = EditorInfo.TYPE_CLASS_TEXT
            mEditMemCity.setText(tCity)
            AlertDialog.Builder(mContext)
                .setIcon(R.drawable.ic_edit)
                .setTitle("Update: " + mHolder.mMemberName.text.toString())
                .setView(mDialogEditView)
                .setPositiveButton("Update It!") { _, _ ->
                    DetailHelper(mContext).updateMember(
                        tID,
                        if (TextUtils.isEmpty(mEditMemName.text.toString())) tName else mEditMemName.text.toString(),
                        if (TextUtils.isEmpty(mEditMemContact.text.toString())) tContact else mEditMemContact.text.toString(),
                        if (TextUtils.isEmpty(mEditMemCity.text.toString())) tCity else mEditMemCity.text.toString()
                    )
                }
                .setNeutralButton("Delete member") { _, _ ->
                    AlertDialog.Builder(mContext)
                        .setTitle(tName)
                        .setIcon(R.drawable.ic_remove)
                        .setMessage("Do you really want to delete?")
                        .setPositiveButton("Yes, delete!") { _, _ ->
                            DetailHelper(mContext).deleteMember(
                                tID
                            )
                        }
                        .setNegativeButton("No, Go back", null).create().show()
                }
                .setNegativeButton("Back", null).create().show()
        }
        return convertView
    }

    private inner class MemeberViewHolder(view: View) {
        var mMemberID: TextView = view.findViewById(R.id.member_id)
        var mMemberNum: TextView = view.findViewById(R.id.member_num)
        var mMemberName: TextView = view.findViewById(R.id.member_name)
        var mMemberContact: TextView = view.findViewById(R.id.member_contact)
        var mMemberCity: TextView = view.findViewById(R.id.member_city)
        var mMemberCall: View = view.findViewById(R.id.member_call_view)
        var mMemberEditView: View = view.findViewById(R.id.edit_member_view)
    }
}
