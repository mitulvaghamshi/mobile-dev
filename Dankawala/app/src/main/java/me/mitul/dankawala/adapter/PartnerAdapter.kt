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
import me.mitul.dankawala.model.PartnerModel

class PartnerAdapter(
    private val mContext: Activity,
    private val mPartnerArray: ArrayList<PartnerModel>
) : BaseAdapter() {
    override fun getCount(): Int {
        return mPartnerArray.size
    }

    override fun getItem(position: Int): Any {
        return mPartnerArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val mHolder: MemberViewHolder = convertView.tag as MemberViewHolder
        mHolder.mPartnerID.text = mPartnerArray[position].getmShopKeeperID().toString()
        mHolder.mPartnerNum.text = mPartnerArray[position].getmShopKeeperNum().toString()
        mHolder.mPartnerName.text = mPartnerArray[position].getmShopKeeperName()
        mHolder.mPartnerShare.text =
            String.format("%s%%", mPartnerArray[position].getmShopKeeperShare())
        mHolder.mPartnerEarn.text =
            String.format("%s/-", mPartnerArray[position].getmShopKeeperEarn())
        mHolder.mEditView.setOnClickListener {
            val tShare = mHolder.mPartnerShare.text.toString()
            val tID = mHolder.mPartnerID.text.toString().toInt()
            val mDialogEditView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog, null)
            val mEditPartner =
                mDialogEditView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_1)
            mEditPartner.hint = "Enter share(in 100%)"
            mEditPartner.inputType = EditorInfo.TYPE_CLASS_NUMBER
            mEditPartner.setText(tShare.substring(0, tShare.length - 1))
            AlertDialog.Builder(mContext)
                .setIcon(R.drawable.ic_edit)
                .setTitle(mHolder.mPartnerName.text.toString())
                .setView(mDialogEditView)
                .setPositiveButton("Edit share") { _, _ ->
                    val xShare = mEditPartner.text.toString()
                    DetailHelper(mContext).updatePartnerShare(
                        if (TextUtils.isEmpty(xShare)) "0" else xShare.toInt(),
                        tID
                    )
                }
                .setNeutralButton("Delete partner") { dialog, which ->
                    DetailHelper(mContext).deletePartner(
                        tID
                    )
                }
                .setNegativeButton("No", null).create().show()
        }
        return convertView
    }

    private inner class MemberViewHolder(view: View) {
        var mPartnerID: TextView = view.findViewById(R.id.shop_keeper_id)
        var mPartnerNum: TextView = view.findViewById(R.id.shop_keeper_num)
        var mPartnerName: TextView = view.findViewById(R.id.shop_keeper_name)
        var mPartnerShare: TextView = view.findViewById(R.id.shop_keeper_share)
        var mPartnerEarn: TextView = view.findViewById(R.id.shop_keeper_earn)
        var mEditView: View = view.findViewById(R.id.partner_edit_view)
    }
}
