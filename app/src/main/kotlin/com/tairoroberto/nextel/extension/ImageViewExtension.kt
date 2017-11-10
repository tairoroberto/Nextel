package com.tairoroberto.nextel.extension

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tairoroberto.nextel.R


/**
 * Created by tairo on 9/2/17.
 */

fun ImageView.loadImage(url: String?) {
    Picasso.with(context).load(url).placeholder(R.drawable.animais).into(this)
}