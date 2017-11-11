package com.tairoroberto.nextel.home.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("overview")
        val overview: String = "",

        @SerializedName("original_language")
        val originalLanguage: String = "",

        @SerializedName("original_title")
        val originalTitle: String = "",

        @SerializedName("video")
        val video: Boolean = false,

        @SerializedName("title")
        val title: String = "",

        @SerializedName("poster_path")
        val posterPath: String = "",

        @SerializedName("backdrop_path")
        val backdropPath: String? = "",

        @SerializedName("release_date")
        val releaseDate: String = "",

        @SerializedName("vote_average")
        val voteAverage: Double = 0.0,

        @SerializedName("popularity")
        val popularity: Double = 0.0,

        @SerializedName("id")
        val id: Int = 0,

        @SerializedName("adult")
        val adult: Boolean = false,

        @SerializedName("vote_count")
        val voteCount: Int = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(overview)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeString(title)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeString(releaseDate)
        parcel.writeDouble(voteAverage)
        parcel.writeDouble(popularity)
        parcel.writeInt(id)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeInt(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}