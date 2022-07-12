package me.mitul.dankawala.model

class MemberModel {
    private var mMemberID = 0
    private var mMemberNum = 0
    private var mMemberName: String? = null
    private var mMemberContact: String? = null
    private var mMemberCity: String? = null
    fun getmMemberID(): Int {
        return mMemberID
    }

    fun setmMemberID(mMemberID: Int) {
        this.mMemberID = mMemberID
    }

    fun getmMemberName(): String? {
        return mMemberName
    }

    fun setmMemberName(mMemberName: String?) {
        this.mMemberName = mMemberName
    }

    fun getmMemberNum(): Int {
        return mMemberNum
    }

    fun setmMemberNum(mMemberNum: Int) {
        this.mMemberNum = mMemberNum
    }

    fun getmMemberContact(): String? {
        return mMemberContact
    }

    fun setmMemberContact(mMemberContact: String?) {
        this.mMemberContact = mMemberContact
    }

    fun getmMemberCity(): String? {
        return mMemberCity
    }

    fun setmMemberCity(mMemberCity: String?) {
        this.mMemberCity = mMemberCity
    }
}