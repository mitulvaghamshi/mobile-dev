package me.mitul.dankawala.model

class MonthModel {
    private var mMonthID = 0
    private var mMonthName: String? = null
    private var mMonthIncome = 0
    private var mMonthOutcome = 0
    private var mMonthProfit = 0
    fun getmMonthID(): Int {
        return mMonthID
    }

    fun setmMonthID(mMonthID: Int) {
        this.mMonthID = mMonthID
    }

    fun getmMonthName(): String? {
        return mMonthName
    }

    fun setmMonthName(mMonthName: String?) {
        this.mMonthName = mMonthName
    }

    fun getmMonthIncome(): Int {
        return mMonthIncome
    }

    fun setmMonthIncome(mMonthIncome: Int) {
        this.mMonthIncome = mMonthIncome
    }

    fun getmMonthOutcome(): Int {
        return mMonthOutcome
    }

    fun setmMonthOutcome(mMonthOutcome: Int) {
        this.mMonthOutcome = mMonthOutcome
    }

    fun getmMonthProfit(): Int {
        return mMonthProfit
    }

    fun setmMonthProfit(mMonthProfit: Int) {
        this.mMonthProfit = mMonthProfit
    }
}