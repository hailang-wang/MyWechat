package com.hikvision.mykotlintest

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ThankAgain :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thanks_again)
    }

    fun returnMain(view: View) {
        startActivity(Intent(this@ThankAgain,MainActivity::class.java))
    }
}