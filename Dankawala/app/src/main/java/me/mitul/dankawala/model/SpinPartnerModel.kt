package me.mitul.dankawala.model

class SpinPartnerModel {
    private var mPartnerID = 0
    private var mPartnerName: String? = null
    fun getmPartnerID(): Int {
        return mPartnerID
    }

    fun setmPartnerID(mPartnerID: Int) {
        this.mPartnerID = mPartnerID
    }

    fun getmPartnerName(): String? {
        return mPartnerName
    }

    fun setmPartnerName(mPartnerName: String?) {
        this.mPartnerName = mPartnerName
    }
}