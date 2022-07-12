package me.mitul.dankawala.adapter

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import me.mitul.dankawala.R
import me.mitul.dankawala.helper.DetailHelper
import me.mitul.dankawala.model.DetailModel

class DetailAdapter(
    private val mContext: Context,
    private val listDataHeader: List<DetailModel>,
    private val listDataChild: HashMap<DetailModel, Array<String>>,
    private val mIsIncome: Boolean
) : BaseExpandableListAdapter() {
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return listDataChild[listDataHeader[groupPosition]]!!
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View,
        parent: ViewGroup
    ): View {
        val mModelDetail = getChild(groupPosition, childPosition) as Array<String>
        val mChildViewHolder: ChildViewExpHolder = convertView.tag as ChildViewExpHolder
        mChildViewHolder.mDetailDescription.text = mModelDetail[childPosition]
        convertView.setOnLongClickListener {
            val mDialogEditView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog, null)
            val mEditDescription =
                mDialogEditView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_1)
            mEditDescription.hint = "Enter description(separated by ',' for multiple.)"
            mEditDescription.setText(mChildViewHolder.mDetailDescription.text.toString())
            AlertDialog.Builder(mContext).setTitle("Update Descriptions...")
                .setView(mDialogEditView)
                .setPositiveButton("Update") { _, _ ->
                    mModelDetail[childPosition] = mEditDescription.text.toString()
                    var tempString = ""
                    for (aMEditDesc in mModelDetail) tempString += if (TextUtils.isEmpty(aMEditDesc)) "" else "$aMEditDesc,"
                    DetailHelper(mContext).updateDetailData(
                        mIsIncome,
                        false,
                        (getGroup(groupPosition) as DetailModel).getmDetailID(),
                        tempString
                    )
                    notifyDataSetChanged()
                }
                .setNeutralButton("Delete") { _, _ ->
                    mModelDetail[childPosition] = ""
                    var tempString = ""
                    for (aMEditDesc in mModelDetail) tempString += if (TextUtils.isEmpty(aMEditDesc)) "" else "$aMEditDesc,"
                    DetailHelper(mContext).updateDetailData(
                        mIsIncome,
                        false,
                        (getGroup(groupPosition) as DetailModel).getmDetailID(),
                        tempString
                    )
                    notifyDataSetChanged()
                }
                .setNegativeButton("Discard", null).create().show()
            false
        }
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listDataChild[listDataHeader[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return listDataHeader[groupPosition]
    }

    override fun getGroupCount(): Int {
        return listDataHeader.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View,
        parent: ViewGroup
    ): View {
        val mDetailModel = getGroup(groupPosition) as DetailModel
        val mHeaderViewHolder: HeaderViewExpHolder = convertView.tag as HeaderViewExpHolder
        mHeaderViewHolder.mDetailCount.text = mDetailModel.getmDetailCount().toString()
        mHeaderViewHolder.mDetailID.text = mDetailModel.getmDetailID().toString()
        mHeaderViewHolder.mDetailDate.text = mDetailModel.getmDetailDate()
        mHeaderViewHolder.mDetailDayName.text = mDetailModel.getmDetailDayName()
        mHeaderViewHolder.mDetailAmountDay.text =
            String.format("%s/-", mDetailModel.getmDetailAmountDay())
        mHeaderViewHolder.mDetailEditButton.setOnClickListener {
            val mDialogEditView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog, null)
            val mEditAmount =
                mDialogEditView.findViewById<AutoCompleteTextView>(R.id.dialog_detail_box_1)
            mEditAmount.hint = "Enter amount..."
            mEditAmount.setText(
                mHeaderViewHolder.mDetailAmountDay.text.toString()
                    .substring(0, mHeaderViewHolder.mDetailAmountDay.text.length - 2)
            )
            AlertDialog.Builder(mContext).setTitle("Update Amount...")
                .setView(mDialogEditView)
                .setPositiveButton("Update") { _, _ ->
                    val tAmount =
                        if (TextUtils.isEmpty(mEditAmount.text.toString())) "0" else mEditAmount.text.toString()
                    DetailHelper(mContext).updateDetailData(
                        mIsIncome,
                        true,
                        mDetailModel.getmDetailID(),
                        tAmount
                    )
                    mDetailModel.setmDetailAmountDay(tAmount)
                    notifyDataSetChanged()
                }
                .setNeutralButton("Delete") { _, _ ->
                    DetailHelper(mContext).deleteDetailData(
                        mDetailModel.getmDetailID()
                    )
                }
                .setNegativeButton("Discard", null).create().show()
        }
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    private inner class HeaderViewExpHolder(v: View) {
        val mDetailCount: TextView = v.findViewById(R.id.detail_list_item_count)
        val mDetailID: TextView = v.findViewById(R.id.detail_list_item_id)
        val mDetailDate: TextView = v.findViewById(R.id.detail_list_item_date)
        val mDetailDayName: TextView = v.findViewById(R.id.detail_list_item_day)
        val mDetailAmountDay: TextView = v.findViewById(R.id.detail_list_item_amount_day)
        val mDetailEditButton: View = v.findViewById(R.id.detail_list_item_edit_button)
    }

    private inner class ChildViewExpHolder(v: View) {
        val mDetailDescription: TextView = v.findViewById(R.id.detail_list_item_description)
    }
}
