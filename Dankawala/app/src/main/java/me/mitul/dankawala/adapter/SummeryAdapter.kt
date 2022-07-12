package me.mitul.dankawala.adapter

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import me.mitul.dankawala.R

class SummeryAdapter(
    private val mContext: Activity,
    private val mSummeryArray: ArrayList<Array<String?>>
) : BaseAdapter() {
    private val mColorDrawable: ColorDrawable = ColorDrawable(Color.WHITE)
    override fun getCount(): Int {
        return mSummeryArray.size
    }

    override fun getItem(position: Int): Any {
        return mSummeryArray[position]
    }

    override fun getItemId(position: Int): Long {
        return 0L
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val mHolder: SummeryViewHolder = convertView.tag as SummeryViewHolder
        val mArray = mSummeryArray[position]
        mHolder.mDankaNum.text = mArray[0]
        mHolder.mDankaName.text = mArray[1]
        mHolder.mMonth1.text = mArray[2]
        mHolder.mMonth2.text = mArray[3]
        mHolder.mMonth3.text = mArray[4]
        mHolder.mMonth4.text = mArray[5]
        mHolder.mMonth5.text = mArray[6]
        mHolder.mMonth6.text = mArray[7]
        mHolder.mMonth7.text = mArray[8]
        mHolder.mMonth8.text = mArray[9]
        mHolder.mMonth9.text = mArray[10]
        mHolder.mMonth10.text = mArray[11]
        mHolder.mMonth11.text = mArray[12]
        mHolder.mMonth12.text = mArray[13]
        mHolder.mCent.text = mArray[14]
        mHolder.mTotalYear.text = mArray[15]
        if (position == 0 || position == count - 1) {
            convertView.setBackgroundColor(Color.BLACK)
            (convertView as LinearLayout).showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            convertView.dividerDrawable = mColorDrawable
            mHolder.mDankaNum.setTextColor(Color.WHITE)
            mHolder.mDankaName.setTextColor(Color.WHITE)
            mHolder.mMonth1.setTextColor(Color.WHITE)
            mHolder.mMonth2.setTextColor(Color.WHITE)
            mHolder.mMonth3.setTextColor(Color.WHITE)
            mHolder.mMonth4.setTextColor(Color.WHITE)
            mHolder.mMonth5.setTextColor(Color.WHITE)
            mHolder.mMonth6.setTextColor(Color.WHITE)
            mHolder.mMonth7.setTextColor(Color.WHITE)
            mHolder.mMonth8.setTextColor(Color.WHITE)
            mHolder.mMonth9.setTextColor(Color.WHITE)
            mHolder.mMonth10.setTextColor(Color.WHITE)
            mHolder.mMonth11.setTextColor(Color.WHITE)
            mHolder.mMonth12.setTextColor(Color.WHITE)
            mHolder.mCent.setTextColor(Color.WHITE)
            mHolder.mTotalYear.setTextColor(Color.WHITE)
        }
        return convertView
    }

    private inner class SummeryViewHolder(view: View) {
        var mDankaNum: TextView = view.findViewById(R.id.text0)
        var mDankaName: TextView = view.findViewById(R.id.text1)
        var mMonth1: TextView = view.findViewById(R.id.text2)
        var mMonth2: TextView = view.findViewById(R.id.text3)
        var mMonth3: TextView = view.findViewById(R.id.text4)
        var mMonth4: TextView = view.findViewById(R.id.text5)
        var mMonth5: TextView = view.findViewById(R.id.text6)
        var mMonth6: TextView = view.findViewById(R.id.text7)
        var mMonth7: TextView = view.findViewById(R.id.text8)
        var mMonth8: TextView = view.findViewById(R.id.text9)
        var mMonth9: TextView = view.findViewById(R.id.text10)
        var mMonth10: TextView = view.findViewById(R.id.text11)
        var mMonth11: TextView = view.findViewById(R.id.text12)
        var mMonth12: TextView = view.findViewById(R.id.text13)
        var mCent: TextView = view.findViewById(R.id.text14)
        var mTotalYear: TextView = view.findViewById(R.id.text15)
    }
}
