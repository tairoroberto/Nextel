package com.tairoroberto.nextel.base.extension

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.*
import com.tairoroberto.nextel.R
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import com.tairoroberto.nextel.R.id.imageView


/**
 * Created by tairo on 9/2/17.
 */

fun ImageView.loadImage(url: String?, progress: ProgressBar?) {
    progress?.visibility = View.VISIBLE
    Picasso.with(context)
            .load(url)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(this, object : Callback {
                override fun onSuccess() {
                    Log.v("Picasso", "fetch image success in first time.")
                    progress?.visibility = View.GONE
                }

                override fun onError() {
                    progress?.visibility = View.VISIBLE
                    //Try again online if cache failed
                    Log.v("Picasso", "Could not fetch image in first time...")
                    Picasso.with(context).load(url).networkPolicy(NetworkPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).error(R.drawable.nextel)
                            .into(this@loadImage, object : Callback {

                                override fun onSuccess() {
                                    Log.v("Picasso", "fetch image success in try again.")
                                    progress?.visibility = View.GONE
                                }

                                override fun onError() {
                                    Log.v("Picasso", "Could not fetch image again...")
                                    progress?.visibility = View.VISIBLE
                                }
                            })
                }
            })
}