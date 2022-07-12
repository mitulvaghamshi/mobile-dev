package me.mitul.dankawala.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import me.mitul.dankawala.model.DetailModel
import me.mitul.dankawala.model.MemberModel
import me.mitul.dankawala.model.MonthModel
import me.mitul.dankawala.model.PartnerModel
import me.mitul.dankawala.model.ShopModel
import me.mitul.dankawala.model.SpinPartnerModel
import me.mitul.dankawala.model.SpinYearModel
import me.mitul.dankawala.utility.Utils

class DetailHelper @SuppressLint("SdCardPath")
constructor(context: Context?) : SQLiteAssetHelper(
    context,
    Utils.DB_NAME,
    "/data/data/me.mady.dankawala/databases",
    null,
    Utils.DB_VERSION
) {
    //TODO: Detail data
    fun getDetailData(
        paramIsIncome: Boolean,
        paramShopID: Int,
        paramYearNum: Int,
        paramMonthID: Int
    ): ArrayList<DetailModel> {
        val mField =
            if (paramIsIncome) "detail_income_desc 'detail_item_desc', detail_income_amount" else " detail_outcome_desc 'detail_item_desc',  detail_outcome_amount"
        val mQuery = "SELECT detail_id," +
                "       date_date || '/' || month_short_name || '/' || year_year 'detail_date'," +
                "       day_name," +
                "       " + mField + " 'detail_amount'" +
                "  FROM master_detail," +
                "       master_date," +
                "       master_day," +
                "       master_month," +
                "       master_shop," +
                "       master_year" +
                " WHERE detail_date_id = date_id AND " +
                "       detail_day_id = day_id AND " +
                "       detail_month_id = month_id AND " +
                "       detail_shop_id = shop_id AND " +
                "       detail_year_id = year_num AND " +
                "       shop_id = " + paramShopID + " AND " +
                "       year_num = " + paramYearNum + " AND " +
                "       month_id = " + paramMonthID + " ORDER BY detail_date_id;"
        val mSQLiteDatabase = readableDatabase
        val mCursor = mSQLiteDatabase.rawQuery(mQuery, null)
        val mArrayList = ArrayList<DetailModel>()
        if (mCursor.moveToFirst()) do {
            val mDetailModel = DetailModel()
            mDetailModel.setmDetailCount(mCursor.position + 1)
            mDetailModel.setmDetailID(mCursor.getInt(mCursor.getColumnIndex("detail_id")))
            mDetailModel.setmDetailDate(mCursor.getString(mCursor.getColumnIndex("detail_date")))
            mDetailModel.setmDetailDayName(mCursor.getString(mCursor.getColumnIndex("day_name")))
            mDetailModel.setmDetailAmountDay(mCursor.getString(mCursor.getColumnIndex("detail_amount")))
            mDetailModel.setmDetailDescription(mCursor.getString(mCursor.getColumnIndex("detail_item_desc")))
            mArrayList.add(mDetailModel)
        } while (mCursor.moveToNext())
        mCursor.close()
        mSQLiteDatabase.close()
        return mArrayList
    }

    //TODO: Month total
    fun getMonthTotal(
        paramIsProfit: Boolean,
        paramIsIncome: Boolean,
        paramShopID: Int,
        paramYearNum: Int,
        paramMonthID: Int
    ): Int {
        val mField =
            if (!paramIsProfit) if (paramIsIncome) "detail_income_amount" else "detail_outcome_amount" else "detail_income_amount - detail_outcome_amount"
        val mQuery = "SELECT SUM(" + mField + ") 'amount_total'" +
                "  FROM master_detail" +
                " WHERE detail_shop_id = " + paramShopID + " AND " +
                "       detail_year_id = " + paramYearNum + " AND " +
                "       detail_month_id = " + paramMonthID + ";"
        val mSQLiteDatabase = readableDatabase
        val mCursor = mSQLiteDatabase.rawQuery(mQuery, null)
        if (mCursor.moveToFirst()) return mCursor.getInt(mCursor.getColumnIndex("amount_total"))
        mCursor.close()
        mSQLiteDatabase.close()
        return -1
    }

    //TODO: Insert new record
    fun insertDetailData(
        paramIncomeAmount: Int,
        paramOutcomeAmount: Int,
        paramIncomeDesc: String?,
        paramOutcomeDesc: String?,
        paramShopID: Int,
        paramYearNum: Int,
        paramMonthID: Int,
        paramDateID: Int,
        paramDayName: String
    ): Int {
        val mSQLiteDatabase = writableDatabase
        var mCursor = mSQLiteDatabase.rawQuery(
            "SELECT * " +
                    "  FROM master_detail" +
                    " WHERE detail_shop_id = " + paramShopID + " AND " +
                    "       detail_year_id = " + paramYearNum + " AND " +
                    "       detail_month_id = " + paramMonthID + " AND " +
                    "       detail_date_id = " + paramDateID + ";", null
        )
        if (mCursor.moveToFirst()) return 999
        mCursor = mSQLiteDatabase.rawQuery(
            "SELECT day_id FROM master_day WHERE day_name = '$paramDayName';",
            null
        )
        mCursor.moveToFirst()
        val tDayID = mCursor.getInt(mCursor.getColumnIndex("day_id"))
        val mValues = ContentValues()
        mValues.put("detail_day_id", tDayID)
        mValues.put("detail_date_id", paramDateID)
        mValues.put("detail_month_id", paramMonthID)
        mValues.put("detail_year_id", paramYearNum)
        mValues.put("detail_shop_id", paramShopID)
        mValues.put("detail_income_amount", paramIncomeAmount)
        mValues.put("detail_outcome_amount", paramOutcomeAmount)
        mValues.put("detail_income_desc", paramIncomeDesc)
        mValues.put("detail_outcome_desc", paramOutcomeDesc)
        mSQLiteDatabase.insert("master_detail", null, mValues)
        mCursor.close()
        mValues.clear()
        mSQLiteDatabase.close()
        return 0
    }

    //TODO: Update detail data
    fun updateDetailData(isIncome: Boolean, isAmount: Boolean, paramID: Int, paramValue: String) {
        val mSQLiteDatabase = writableDatabase
        val mValues = ContentValues()
        if (isAmount) mValues.put(
            if (isIncome) "detail_income_amount" else "detail_outcome_amount",
            paramValue.toInt()
        ) else mValues.put(
            if (isIncome) "detail_income_desc" else "detail_outcome_desc",
            paramValue
        )
        mSQLiteDatabase.update("master_detail", mValues, "detail_id=$paramID", null)
        mValues.clear()
        mSQLiteDatabase.close()
    }

    //TODO: Shop name
    val shopNames: ArrayList<ShopModel>
        get() {
            val mQuery = "SELECT * FROM master_shop;"
            val mSQLiteDatabase = readableDatabase
            val mCursor = mSQLiteDatabase.rawQuery(mQuery, null)
            val mShopModelArray = ArrayList<ShopModel>()
            if (mCursor.moveToFirst()) do {
                val mShopModel = ShopModel()
                mShopModel.setmHomeDnakaID(mCursor.getInt(mCursor.getColumnIndex("shop_id")))
                mShopModel.setmHomeDankaNum(mCursor.position + 1)
                mShopModel.setmHomeDankaName(mCursor.getString(mCursor.getColumnIndex("shop_name")))
                mShopModel.setmHomeDankaAddr(mCursor.getString(mCursor.getColumnIndex("shop_address")))
                mShopModelArray.add(mShopModel)
            } while (mCursor.moveToNext())
            mCursor.close()
            mSQLiteDatabase.close()
            return mShopModelArray
        }

    //TODO: Month name
    fun getMonthNames(paramShopID: Int, paramYearNum: Int): ArrayList<MonthModel> {
        val mSQLiteDatabase = readableDatabase
        val mQuery = "SELECT month_id," +
                "       month_name," +
                "       SUM(detail_income_amount) 'month_income'," +
                "       SUM(detail_outcome_amount) 'month_outcome'," +
                "       CASE WHEN month_id IS NULL THEN 0 ELSE SUM(detail_income_amount - detail_outcome_amount) END AS 'month_profit'" +
                "  FROM master_shop," +
                "       master_year," +
                "       master_month," +
                "       master_detail" +
                " WHERE detail_shop_id = shop_id AND " +
                "       detail_year_id = year_num AND " +
                "       detail_month_id = month_id AND " +
                "       shop_id = " + paramShopID + " AND " +
                "       year_num = " + paramYearNum +
                " GROUP BY month_id;"
        val mCursor = mSQLiteDatabase.rawQuery(mQuery, null)
        val mMonthModelArray = ArrayList<MonthModel>()
        if (mCursor.moveToFirst()) do {
            val mMonthModel = MonthModel()
            mMonthModel.setmMonthID(mCursor.getInt(mCursor.getColumnIndex("month_id")))
            mMonthModel.setmMonthName(mCursor.getString(mCursor.getColumnIndex("month_name")))
            mMonthModel.setmMonthProfit(mCursor.getInt(mCursor.getColumnIndex("month_profit")))
            mMonthModel.setmMonthIncome(mCursor.getInt(mCursor.getColumnIndex("month_income")))
            mMonthModel.setmMonthOutcome(mCursor.getInt(mCursor.getColumnIndex("month_outcome")))
            mMonthModelArray.add(mMonthModel)
        } while (mCursor.moveToNext())
        mCursor.close()
        mSQLiteDatabase.close()
        return mMonthModelArray
    }

    //TODO: Summery data
    fun getSummery(paramYearNum: Int): ArrayList<Array<String?>> {
        val mSqLiteDatabase = readableDatabase
        val mSummeryArray = ArrayList<Array<String?>>()
        val mQuery1 = "SELECT shop_id, shop_name FROM master_shop;"
        val mCursor1 = mSqLiteDatabase.rawQuery(mQuery1, null)
        val mLastRecord = arrayOf<String?>(
            "---",
            "Profit(Month)",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0"
        )
        mSummeryArray.add(
            arrayOf(
                "No",
                "Year: 20$paramYearNum",
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December",
                "Cent(Year)",
                "Profit(Year)"
            )
        )
        var mGrandProfit = 0
        if (mCursor1.moveToFirst()) do {
            val tShopID = mCursor1.getInt(mCursor1.getColumnIndex("shop_id"))
            val mQuery2 = "SELECT month_id, SUM(detail_income_amount) 'month_income'," +
                    " CASE WHEN " +
                    " SUM(detail_income_amount - detail_outcome_amount)" +
                    " IS NULL THEN '0' ELSE " +
                    " SUM(detail_income_amount - detail_outcome_amount)" +
                    " END AS 'month_profit' " +
                    "  FROM master_shop," +
                    "       master_year," +
                    "       master_month," +
                    "       master_detail" +
                    " WHERE detail_shop_id = shop_id AND " +
                    "       detail_year_id = year_num AND " +
                    "       detail_month_id = month_id AND " +
                    "       shop_id = " + tShopID + " AND " +
                    "       year_num = " + paramYearNum +
                    " GROUP BY month_id;"
            val mCursor2 = mSqLiteDatabase.rawQuery(mQuery2, null)
            val mDataArray = arrayOfNulls<String>(16)
            mDataArray[0] = (mCursor1.position + 1).toString()
            mDataArray[1] = mCursor1.getString(mCursor1.getColumnIndex("shop_name"))
            var mProfitYear = 0
            var mIncomeYear = 0
            if (mCursor2.moveToFirst()) do {
                val tMonthID = mCursor2.getInt(mCursor2.getColumnIndex("month_id"))
                val tProfit = mCursor2.getString(mCursor2.getColumnIndex("month_profit"))
                mLastRecord[tMonthID + 1] =
                    (mLastRecord[tMonthID + 1]!!.toInt() + tProfit.toInt()).toString()
                val tIncome = mCursor2.getInt(mCursor2.getColumnIndex("month_income"))
                mDataArray[tMonthID + 1] =
                    tProfit + "/-(" + (if (tIncome == 0) "0" else tProfit.toInt() * 100 / tIncome) + "%)"
                mIncomeYear += tIncome
                mProfitYear += tProfit.toInt()
            } while (mCursor2.moveToNext())
            mDataArray[14] =
                if (mProfitYear != 0 && mIncomeYear != 0) (mProfitYear * 100 / mIncomeYear).toString() + "%" else "---"
            mDataArray[15] = if (mProfitYear != 0) "$mProfitYear/-" else "---"
            mGrandProfit += mProfitYear
            mSummeryArray.add(mDataArray)
            mCursor2.close()
        } while (mCursor1.moveToNext())
        mLastRecord[15] = mGrandProfit.toString()
        for (i in 2 until mLastRecord.size) mLastRecord[i] =
            if (mLastRecord[i] != "0") mLastRecord[i] + "/-" else ""
        mSummeryArray.add(mLastRecord)
        mCursor1.close()
        mSqLiteDatabase.close()
        return mSummeryArray
    }

    //TODO: Member data
    val memberData: ArrayList<MemberModel>
        get() {
            val mSQLiteDatabase = readableDatabase
            val mQuery = "SELECT * FROM master_member"
            val mCursor = mSQLiteDatabase.rawQuery(mQuery, null)
            val mMemberArray = ArrayList<MemberModel>()
            if (mCursor.moveToFirst()) do {
                val mMemberModel = MemberModel()
                mMemberModel.setmMemberID(mCursor.getInt(mCursor.getColumnIndex("member_id")))
                mMemberModel.setmMemberNum(mCursor.position + 1)
                mMemberModel.setmMemberName(mCursor.getString(mCursor.getColumnIndex("member_name")))
                mMemberModel.setmMemberContact(mCursor.getString(mCursor.getColumnIndex("member_phone")))
                mMemberModel.setmMemberCity(mCursor.getString(mCursor.getColumnIndex("member_city")))
                mMemberArray.add(mMemberModel)
            } while (mCursor.moveToNext())
            mCursor.close()
            mSQLiteDatabase.close()
            return mMemberArray
        }

    //TODO: Insert Member
    fun insertMember(paramName: String?, paramContact: String?, paramCity: String?): Int {
        val mSQLiteDatabase = writableDatabase
        val mValues = ContentValues()
        mValues.put("member_name", paramName)
        mValues.put("member_phone", paramContact)
        mValues.put("member_city", paramCity)
        mSQLiteDatabase.insert("master_member", null, mValues)
        mValues.clear()
        mSQLiteDatabase.close()
        return 0
    }

    //TODO: Update member
    fun updateMember(paramMemID: Int, paramName: String, paramContact: String, paramCity: String) {
        val mSQLiteDatabase = writableDatabase
        mSQLiteDatabase.execSQL("UPDATE master_member SET member_name = '$paramName', member_phone = '$paramContact', member_city = '$paramCity' WHERE member_id = $paramMemID;")
        mSQLiteDatabase.close()
    }

    //TODO: Spinner partner data
    val spinnerPartnerData: ArrayList<SpinPartnerModel>
        get() {
            val mSQLiteDatabase = readableDatabase
            val mQuery = "SELECT member_id, member_name FROM master_member"
            val mCursor = mSQLiteDatabase.rawQuery(mQuery, null)
            val mSpinnerArray = ArrayList<SpinPartnerModel>()
            if (mCursor.moveToFirst()) do {
                val mSpinPartnerModel = SpinPartnerModel()
                mSpinPartnerModel.setmPartnerID(mCursor.getInt(mCursor.getColumnIndex("member_id")))
                mSpinPartnerModel.setmPartnerName(mCursor.getString(mCursor.getColumnIndex("member_name")))
                mSpinnerArray.add(mSpinPartnerModel)
            } while (mCursor.moveToNext())
            mCursor.close()
            mSQLiteDatabase.close()
            return mSpinnerArray
        }

    //TODO: Insert new record
    fun addNewYear(paramYearNum: Int): Int {
        val mSQLiteDatabase = writableDatabase
        val mValues = ContentValues()
        mValues.put("year_num", paramYearNum)
        mValues.put("year_year", "20$paramYearNum")
        mSQLiteDatabase.insert("master_year", null, mValues)
        mValues.clear()
        mSQLiteDatabase.close()
        return 0
    }

    //TODO: Spinner year data
    val spinnerYearData: ArrayList<SpinYearModel>
        get() {
            val mSQLiteDatabase = readableDatabase
            val mQuery = "SELECT * FROM master_year"
            val mCursor = mSQLiteDatabase.rawQuery(mQuery, null)
            val mSpinnerArray = ArrayList<SpinYearModel>()
            if (mCursor.moveToFirst()) do {
                val mSpinYearModel = SpinYearModel()
                mSpinYearModel.yearId = mCursor.getInt(mCursor.getColumnIndex("year_id"))
                mSpinYearModel.yearNum = mCursor.getInt(mCursor.getColumnIndex("year_num"))
                mSpinYearModel.yearYear =
                    mCursor.getInt(mCursor.getColumnIndex("year_year")).toString()
                mSpinnerArray.add(mSpinYearModel)
            } while (mCursor.moveToNext())
            val mSpinYearModel = SpinYearModel()
            mSpinYearModel.yearId = mCursor.count + 1
            mSpinYearModel.yearNum = 0
            mSpinYearModel.yearYear = "+"
            mSpinnerArray.add(mSpinYearModel)
            mCursor.close()
            mSQLiteDatabase.close()
            return mSpinnerArray
        }

    //TODO: Add shop
    fun addNewShop(paramShopName: String?, paramShopAddress: String?): Int {
        val mSQLiteDatabase = writableDatabase
        val mValues = ContentValues()
        mValues.put("shop_name", paramShopName)
        mValues.put("shop_address", paramShopAddress)
        mSQLiteDatabase.insert("master_shop", null, mValues)
        mValues.clear()
        mSQLiteDatabase.close()
        return 0
    }

    //TODO: Delete shop
    fun deleteShop(paramShopID: Int) {
        val mSQLiteDatabase = writableDatabase
        mSQLiteDatabase.execSQL("DELETE FROM master_detail WHERE detail_shop_id = $paramShopID;")
        mSQLiteDatabase.execSQL("DELETE FROM master_shop WHERE shop_id = $paramShopID;")
        mSQLiteDatabase.close()
    }

    //TODO: Update shop
    fun updateShop(paramShopID: Int, paramName: String, paramAddr: String) {
        val mSQLiteDatabase = writableDatabase
        mSQLiteDatabase.execSQL("UPDATE master_shop SET shop_name = '$paramName', shop_address = '$paramAddr' WHERE shop_id = $paramShopID;")
        mSQLiteDatabase.close()
    }

    //TODO: Delete member
    fun deleteMember(paramMemberID: Int) {
        val mSQLiteDatabase = writableDatabase
        mSQLiteDatabase.execSQL("DELETE FROM master_member WHERE member_id = $paramMemberID;")
        mSQLiteDatabase.close()
    }

    //TODO: Delete detailData
    fun deleteDetailData(paramDetailID: Int) {
        val mSQLiteDatabase = writableDatabase
        mSQLiteDatabase.execSQL("DELETE FROM master_detail WHERE detail_id = $paramDetailID;")
        mSQLiteDatabase.close()
    }

    //TODO: Shop keeper data
    fun getPartnerData(paramShopID: Int, paramProfit: Int): ArrayList<PartnerModel> {
        val mSQLiteDatabase = readableDatabase
        val mQuery = "SELECT keeper_id," +
                "       member_name," +
                "       keeper_share" +
                "  FROM master_member," +
                "       shop_keeper," +
                "       master_shop" +
                " WHERE keeper_member_id = member_id AND " +
                "       keeper_shop_id = shop_id AND " +
                "       keeper_shop_id = " + paramShopID + ";"
        val mCursor = mSQLiteDatabase.rawQuery(mQuery, null)
        val mPartnerArray = ArrayList<PartnerModel>()
        if (mCursor.moveToFirst()) do {
            val mPartnerModel = PartnerModel()
            mPartnerModel.setmShopKeeperID(mCursor.getInt(mCursor.getColumnIndex("keeper_id")))
            mPartnerModel.setmShopKeeperNum(mCursor.position + 1)
            val tShare = mCursor.getInt(mCursor.getColumnIndex("keeper_share"))
            mPartnerModel.setmShopKeeperShare(tShare)
            mPartnerModel.setmShopKeeperName(mCursor.getString(mCursor.getColumnIndex("member_name")))
            mPartnerModel.setmShopKeeperEarn(paramProfit / 100 * tShare)
            mPartnerArray.add(mPartnerModel)
        } while (mCursor.moveToNext())
        mCursor.close()
        mSQLiteDatabase.close()
        return mPartnerArray
    }

    //TODO: Add partner
    fun addPartner(paramShopID: Int, paramMemberID: Int, paramShare: Int): Long {
        val mSQLiteDatabase = writableDatabase
        val mValues = ContentValues()
        mValues.put("keeper_shop_id", paramShopID)
        mValues.put("keeper_member_id", paramMemberID)
        mValues.put("keeper_share", paramShare)
        val tLong = mSQLiteDatabase.insert("shop_keeper", null, mValues)
        mValues.clear()
        mSQLiteDatabase.close()
        return tLong
    }

    //TODO: Delete partner
    fun deletePartner(paramPartnerID: Int) {
        val mSQLiteDatabase = writableDatabase
        mSQLiteDatabase.execSQL("DELETE FROM shop_keeper WHERE keeper_id = $paramPartnerID;")
        mSQLiteDatabase.close()
    }

    //TODO: Delete detailData
    fun updatePartnerShare(paramPartnerShare: Any, paramPartnerID: Int) {
        val mSQLiteDatabase = writableDatabase
        mSQLiteDatabase.execSQL("UPDATE shop_keeper SET keeper_share = $paramPartnerShare WHERE keeper_id = $paramPartnerID;")
        mSQLiteDatabase.close()
    }

    init {
        writableDatabase.close()
    }
}
