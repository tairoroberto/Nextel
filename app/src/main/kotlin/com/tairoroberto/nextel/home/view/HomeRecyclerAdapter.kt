package com.tairoroberto.nextel.home.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.tairoroberto.nextel.extension.loadImage
import com.tairoroberto.nextel.home.model.MovieDetail
import com.tairoroberto.nextel.R


/**
 * Created by tairo on 8/22/17.
 */
class HomeRecyclerAdapter(val context: Context,
                          private var listPetshops: ArrayList<MovieDetail>?,
                          private val onClick: OnClick) : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {

    private var lastPosition = -1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val petShop = listPetshops?.get(position)
        if (petShop != null) {
            holder.bind(context, petShop, position)
            holder.itemView.setOnClickListener({
                onClick.onItemClick(petShop)
            })
        }
        setAnimation(holder.itemView, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.petshops_item, parent, false))
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > 0) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount(): Int {
        return listPetshops?.size as Int
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        private val textViewOpenClose: TextView = view.findViewById(R.id.textViewOpenClose)
        private val imageViewMenu: ImageView = view.findViewById(R.id.imageViewMenu)

        fun bind(context: Context?, petShop: MovieDetail, position: Int) {

            imageView.loadImage(petShop.imageUrl)
            textViewTitle.text = petShop.name
            textViewOpenClose.text = petShop.address
        }
    }

    fun update(listPetshops: ArrayList<MovieDetail>) {
        this.listPetshops?.clear()
        this.listPetshops?.addAll(listPetshops)
        notifyDataSetChanged()
    }
}