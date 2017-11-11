package com.tairoroberto.nextel.home.model

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

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.createTypedArrayList(Movie),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(page)
        parcel.writeInt(totalPages)
        parcel.writeTypedList(results)
        parcel.writeInt(totalResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieResponse> {
        override fun createFromParcel(parcel: Parcel): MovieResponse {
            return MovieResponse(parcel)
        }

        override fun newArray(size: Int): Array<MovieResponse?> {
            return arrayOfNulls(size)
        }
    }
}