package me.mitul.dankawala.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.mitul.dankawala.R
import me.mitul.dankawala.model.SpinYearModel

class SpinYearAdapter(
    private val mContext: Activity,
    private val mSpinnerArray: ArrayList<SpinYearModel>
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
        mHolder.mYearID.text = mSpinnerArray[position].yearId.toString()
        mHolder.mYearNum.text = mSpinnerArray[position].yearNum.toString()
        mHolder.mYearYear.text = mSpinnerArray[position].yearYear
        return convertView
    }

    private inner class SpinnerViewHolder(view: View) {
        var mYearID: TextView = view.findViewById(R.id.year_spinner_id)
        var mYearNum: TextView = view.findViewById(R.id.year_spinner_num)
        var mYearYear: TextView = view.findViewById(R.id.year_spinner_year)
    }
}
