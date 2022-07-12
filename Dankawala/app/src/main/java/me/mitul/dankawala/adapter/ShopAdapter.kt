package me.mitul.dankawala.adapter

import android.app.Activity
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
import me.mitul.dankawala.model.ShopModel

class ShopAdapter(private val mContext: Activity, private val mHomeArray: ArrayList<ShopModel>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return mHomeArray.size
    }

    override fun getItem(position: Int): Any {
        return mHomeArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val mHolder: HomeViewHolder = convertView.tag as HomeViewHolder
        mHolder.mHomeDankaID.text = mHomeArray[position].getmHomeDnakaID().toString()
        mHolder.mHomeDankaNum.text = mHomeArray[position].getmHomeDankaNum().toString()
        mHolder.mHomeDankaName.text = mHomeArray[position].getmHomeDankaName()
        mHolder.mHomeDankaAddr.text = mHomeArray[position].getmHomeDankaAddr()
        mHolder.mHomeDankaEdit.setOnClickListener {
            val tID = mHolder.mHomeDankaID.text.toString().toInt()
            val tName = mHolder.mHomeDankaName.text.toString()
            val tAddr = mHolder.mHomeDankaAddr.text.toString()
            val mDialogEditView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog, null)
            val mEditName =
                mDialogEditView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_1)
            mEditName.hint = "Enter name"
            mEditName.inputType = EditorInfo.TYPE_CLASS_TEXT
            mEditName.setText(tName)
            val mEditAddr =
                mDialogEditView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_2)
            mEditAddr.hint = "Enter address"
            mEditAddr.inputType = EditorInfo.TYPE_CLASS_TEXT
            mEditAddr.visibility = View.VISIBLE
            mEditAddr.setText(tAddr)
            AlertDialog.Builder(mContext)
                .setIcon(R.drawable.ic_edit)
                .setTitle(tName)
                .setView(mDialogEditView)
                .setPositiveButton("Edit") { _, _ ->
                    val xName = mEditName.text.toString()
                    val xAddr = mEditAddr.text.toString()
                    DetailHelper(mContext).updateShop(
                        tID,
                        if (TextUtils.isEmpty(xName)) tName else xName,
                        if (TextUtils.isEmpty(xAddr)) tAddr else xAddr
                    )
                }.setNeutralButton("Delete shop?") { _, _ ->
                    AlertDialog.Builder(mContext)
                        .setTitle("Delete: $tName")
                        .setIcon(R.drawable.ic_remove)
                        .setMessage("CAUTION: All the records related to this shop will be deleted!")
                        .setPositiveButton("I know, delete!") { _, _ ->
                            DetailHelper(mContext).deleteShop(
                                tID
                            )
                        }
                        .setNegativeButton("No, Go back", null).create().show()
                }.setNegativeButton("No", null).create().show()
        }
        return convertView
    }

    private inner class HomeViewHolder(view: View) {
        var mHomeDankaID: TextView = view.findViewById(R.id.home_danka_id)
        var mHomeDankaNum: TextView = view.findViewById(R.id.home_danka_num)
        var mHomeDankaName: TextView = view.findViewById(R.id.home_danka_name)
        var mHomeDankaAddr: TextView = view.findViewById(R.id.home_danka_addr)
        var mHomeDankaEdit: View = view.findViewById(R.id.home_danka_edit)
    }
}
