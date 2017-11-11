package com.tairoroberto.nextel

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tairoroberto.nextel.home.view.HomeFragment

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
