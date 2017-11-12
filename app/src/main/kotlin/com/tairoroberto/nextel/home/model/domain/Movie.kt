package com.tairoroberto.nextel.home.model.domain

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity(tableName = "movies")
data class Movie(

        @ColumnInfo(name = "overview")
        @SerializedName("overview")
        var overview: String = "",

        @ColumnInfo(name = "original_language")
        @SerializedName("original_language")
        var originalLanguage: String = "",

        @ColumnInfo(name = "original_title")
        @SerializedName("original_title")
        var originalTitle: String = "",

        @ColumnInfo(name = "video")
        @SerializedName("video")
        var video: Boolean = false,

        @ColumnInfo(name = "title")
        @SerializedName("title")
        var title: String = "",

        @ColumnInfo(name = "poster_path")
        @SerializedName("poster_path")
        var posterPath: String = "",

        @ColumnInfo(name = "backdrop_path")
        @SerializedName("backdrop_path")
        var backdropPath: String? = "",

        @ColumnInfo(name = "release_date")
        @SerializedName("release_date")
        var releaseDate: String = "",

        @ColumnInfo(name = "vote_average")
        @SerializedName("vote_average")
        var voteAverage: Double = 0.0,

        @ColumnInfo(name = "popularity")
        @SerializedName("popularity")
        var popularity: Double = 0.0,

        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        var idMovie: Int = 0,

        @ColumnInfo(name = "adult")
        @SerializedName("adult")
        var adult: Boolean = false,

        @ColumnInfo(name = "vote_count")
        @SerializedName("vote_count")
        var voteCount: Int = 0) : Parcelable {

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
                parcel.writeInt(idMovie)
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