package com.application.moneysense.ui.splashapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.application.moneysense.R
import com.application.moneysense.ui.moneyscan.MoneyScan

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // splash app, change to main view in 3 second
        Handler().postDelayed({
            val intent = Intent(this, MoneyScan::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}