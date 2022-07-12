package me.mitul.dankawala.model

class PartnerModel {
    private var mShopKeeperID = 0
    private var mShopKeeperNum = 0
    private var mShopKeeperName: String? = null
    private var mShopKeeperShare = 0
    private var mShopKeeperEarn = 0
    fun getmShopKeeperID(): Int {
        return mShopKeeperID
    }

    fun setmShopKeeperID(mShopKeeperID: Int) {
        this.mShopKeeperID = mShopKeeperID
    }

    fun getmShopKeeperNum(): Int {
        return mShopKeeperNum
    }

    fun setmShopKeeperNum(mShopKeeperNum: Int) {
        this.mShopKeeperNum = mShopKeeperNum
    }

    fun getmShopKeeperName(): String? {
        return mShopKeeperName
    }

    fun setmShopKeeperName(mShopKeeperName: String?) {
        this.mShopKeeperName = mShopKeeperName
    }

    fun getmShopKeeperShare(): Int {
        return mShopKeeperShare
    }

    fun setmShopKeeperShare(mShopKeeperShare: Int) {
        this.mShopKeeperShare = mShopKeeperShare
    }

    fun getmShopKeeperEarn(): Int {
        return mShopKeeperEarn
    }

    fun setmShopKeeperEarn(mShopKeeperEarn: Int) {
        this.mShopKeeperEarn = mShopKeeperEarn
    }
}