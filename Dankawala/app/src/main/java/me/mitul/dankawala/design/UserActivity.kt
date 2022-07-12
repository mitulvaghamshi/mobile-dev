package me.mitul.dankawala.design

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import me.mitul.dankawala.R

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_user)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val fab = findViewById<FloatingActionButton>(R.id.fab_add_user_record)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Soon...", Snackbar.LENGTH_SHORT).show()
        }
    }
}
