package com.tairoroberto.nextel.home.view

import android.widget.ImageView
import com.tairoroberto.nextel.home.model.domain.Movie

/**
 * Created by tairo on 12/12/17.
 */
interface OnClick {
    fun onItemClick(movie: Movie, imageView: ImageView)
}