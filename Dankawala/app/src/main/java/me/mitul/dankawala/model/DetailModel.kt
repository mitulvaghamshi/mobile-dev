package me.mitul.dankawala.model

class DetailModel {
    private var mDetailCount = 0
    private var mDetailID = 0
    private var mDetailDate: String? = null
    private var mDetailDayName: String? = null
    private var mDetailAmountDay: String? = null
    private var mDetailDescription: String? = null
    fun getmDetailCount(): Int {
        return mDetailCount
    }

    fun setmDetailCount(mDetailCount: Int) {
        this.mDetailCount = mDetailCount
    }

    fun getmDetailID(): Int {
        return mDetailID
    }

    fun setmDetailID(mDetailID: Int) {
        this.mDetailID = mDetailID
    }

    fun getmDetailDate(): String? {
        return mDetailDate
    }

    fun setmDetailDate(mDetailDate: String?) {
        this.mDetailDate = mDetailDate
    }

    fun getmDetailDayName(): String? {
        return mDetailDayName
    }

    fun setmDetailDayName(mDetailDayName: String?) {
        this.mDetailDayName = mDetailDayName
    }

    fun getmDetailAmountDay(): String? {
        return mDetailAmountDay
    }

    fun setmDetailAmountDay(mDetailAmountDay: String?) {
        this.mDetailAmountDay = mDetailAmountDay
    }

    fun getmDetailDescription(): String? {
        return mDetailDescription
    }

    fun setmDetailDescription(mDetailDescription: String?) {
        this.mDetailDescription = mDetailDescription
    }
}