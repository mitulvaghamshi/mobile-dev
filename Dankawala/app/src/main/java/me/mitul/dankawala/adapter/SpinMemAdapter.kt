package me.mitul.dankawala.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.mitul.dankawala.R
import me.mitul.dankawala.model.SpinPartnerModel

class SpinMemAdapter(
    private val mContext: Activity,
    private val mSpinnerArray: ArrayList<SpinPartnerModel>
) : BaseAdapter() {
    override fun getCount(): Int {
        return mSpinnerArray.size
    }

    override fun getItem(position: Int): Any {
        return mSpinnerArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val mHolder: SpinnerViewHolder = convertView.tag as SpinnerViewHolder
        mHolder.mMemberID.text = mSpinnerArray[position].getmPartnerID().toString()
        mHolder.mMemberName.text = mSpinnerArray[position].getmPartnerName()
        return convertView
    }

    private inner class SpinnerViewHolder(view: View) {
        var mMemberID: TextView = view.findViewById(R.id.member_spinner_id)
        var mMemberName: TextView = view.findViewById(R.id.member_spinner_name)
    }
}
