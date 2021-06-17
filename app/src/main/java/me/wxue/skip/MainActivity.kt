package me.wxue.skip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.start).setOnClickListener {
            intent = Intent(this@MainActivity, SkipService::class.java)
            startService(intent)
        }

        findViewById<View>(R.id.settings).setOnClickListener {
            intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
    }
}