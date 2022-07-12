package me.mitul.dankawala.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.mitul.dankawala.R
import me.mitul.dankawala.model.MonthModel

class MonthAdapter(
    private val mContext: Activity,
    private val mMonthArray: ArrayList<MonthModel>,
    private val mMonthID: Int
) : BaseAdapter() {
    override fun getCount(): Int {
        return mMonthArray.size
    }

    override fun getItem(position: Int): Any {
        return mMonthArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val mHolder: MonthViewHolder = convertView.tag as MonthViewHolder
        mHolder.mMonthID.text = mMonthArray[position].getmMonthID().toString()
        mHolder.mMonthName.text = mMonthArray[position].getmMonthName()
        mHolder.mMonthProfit.text =
            String.format("P: %s/-", mMonthArray[position].getmMonthProfit())
        mHolder.mMonthIncome.text =
            String.format("I: %s/-", mMonthArray[position].getmMonthIncome())
        mHolder.mMonthOutcome.text =
            String.format("C: %s/-", mMonthArray[position].getmMonthOutcome())
        return convertView
    }

    private inner class MonthViewHolder(view: View) {
        var mMonthID: TextView = view.findViewById(R.id.month_id)
        var mMonthName: TextView = view.findViewById(R.id.month_name)
        var mMonthIncome: TextView = view.findViewById(R.id.month_income)
        var mMonthOutcome: TextView = view.findViewById(R.id.month_outcome)
        var mMonthProfit: TextView = view.findViewById(R.id.month_profit)
    }
}
