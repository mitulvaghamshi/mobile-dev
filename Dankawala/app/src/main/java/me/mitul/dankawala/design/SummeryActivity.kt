package me.mitul.dankawala.design

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import me.mitul.dankawala.R
import me.mitul.dankawala.adapter.SummeryAdapter
import me.mitul.dankawala.helper.DetailHelper

class SummeryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summery)
        setSupportActionBar(findViewById<View>(R.id.toolbar_summery) as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        SummeryTask().execute()
    }

    private inner class SummeryTask : AsyncTask<Void?, Void?, Void?>() {
        private lateinit var mSummerArray: ArrayList<Array<String?>>
        private val mDialog = ProgressDialog(this@SummeryActivity)
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg voids: Void?): Void? {
            try {
                Thread.sleep(1000)
                mSummerArray =
                    DetailHelper(this@SummeryActivity).getSummery(intent.getIntExtra("YEAR_NUM", 0))
            } catch (ignored: InterruptedException) {
            }
            return null
        }

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            mDialog.show()
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            (findViewById<View>(R.id.listview_summery) as ListView).adapter =
                SummeryAdapter(this@SummeryActivity, mSummerArray)
            mDialog.dismiss()
        }
    }
}
