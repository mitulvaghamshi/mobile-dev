package me.mitul.dankawala.model

class ShopModel {
    private var mHomeDnakaID = 0
    private var mHomeDankaNum = 0
    private var mHomeDankaName: String? = null
    private var mHomeDankaAddr: String? = null
    fun getmHomeDnakaID(): Int {
        return mHomeDnakaID
    }

    fun setmHomeDnakaID(mHomeDnakaID: Int) {
        this.mHomeDnakaID = mHomeDnakaID
    }

    fun getmHomeDankaNum(): Int {
        return mHomeDankaNum
    }

    fun setmHomeDankaNum(mHomeDankaNum: Int) {
        this.mHomeDankaNum = mHomeDankaNum
    }

    fun getmHomeDankaName(): String? {
        return mHomeDankaName
    }

    fun setmHomeDankaName(mHomeDankaName: String?) {
        this.mHomeDankaName = mHomeDankaName
    }

    fun getmHomeDankaAddr(): String? {
        return mHomeDankaAddr
    }

    fun setmHomeDankaAddr(mHomeDankaAddr: String?) {
        this.mHomeDankaAddr = mHomeDankaAddr
    }
}