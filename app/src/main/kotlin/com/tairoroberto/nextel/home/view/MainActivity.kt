package com.tairoroberto.nextel.home.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tairoroberto.nextel.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, HomeFragment())
                ?.commit()
    }
}
