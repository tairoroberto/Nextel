package com.tairoroberto.nextel.home.model.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieResponse(
        @SerializedName("page")
        val page: Int = 0,

        @SerializedName("total_pages")
        val totalPages: Int = 0,

        @SerializedName("results")
        val results: List<Movie>? = null,

        @SerializedName("total_results")
        val totalResults: Int = 0) : Parcelable {

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.createTypedArrayList(Movie.CREATOR),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(page)
        writeInt(totalPages)
        writeTypedList(results)
        writeInt(totalResults)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MovieResponse> = object : Parcelable.Creator<MovieResponse> {
            override fun createFromParcel(source: Parcel): MovieResponse = MovieResponse(source)
            override fun newArray(size: Int): Array<MovieResponse?> = arrayOfNulls(size)
        }
    }
}