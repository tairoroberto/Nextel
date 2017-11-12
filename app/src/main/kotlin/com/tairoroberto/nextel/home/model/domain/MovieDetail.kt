package com.tairoroberto.nextel.home.model.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieDetail(@SerializedName("original_language")
                       val originalLanguage: String = "",

                       @SerializedName("imdb_id")
                       val imdbId: String = "",

                       @SerializedName("video")
                       val video: Boolean = false,

                       @SerializedName("title")
                       var title: String = "",

                       @SerializedName("backdrop_path")
                       val backdropPath: String = "",

                       @SerializedName("revenue")
                       val revenue: Int = 0,

                       @SerializedName("genres")
                       val genres: List<GenresItem>? = null,

                       @SerializedName("popularity")
                       val popularity: Double = 0.0,

                       @SerializedName("production_countries")
                       val productionCountries: List<ProductionCountriesItem>? = null,

                       @SerializedName("id")
                       var id: Int = 0,

                       @SerializedName("vote_count")
                       val voteCount: Int = 0,

                       @SerializedName("budget")
                       val budget: Int = 0,

                       @SerializedName("overview")
                       val overview: String = "",

                       @SerializedName("original_title")
                       var originalTitle: String = "",

                       val runtime: Int = 0,

                       @SerializedName("poster_path")
                       var posterPath: String = "",

                       @SerializedName("spoken_languages")
                       val spokenLanguages: List<SpokenLanguagesItem>? = null,

                       @SerializedName("production_companies")
                       val productionCompanies: List<ProductionCompaniesItem>? = null,

                       @SerializedName("release_date")
                       var releaseDate: String = "",

                       @SerializedName("vote_average")
                       var voteAverage: Double = 0.0,

                       @SerializedName("belongs_to_collection")
                       val belongsToCollection: BelongsToCollection? = null,

                       @SerializedName("tagline")
                       val tagline: String = "",

                       @SerializedName("adult")
                       val adult: Boolean = false,

                       @SerializedName("homepage")
                       val homepage: String = "",

                       @SerializedName("status")
                       val status: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.createTypedArrayList(GenresItem),
            parcel.readDouble(),
            parcel.createTypedArrayList(ProductionCountriesItem),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.createTypedArrayList(SpokenLanguagesItem),
            parcel.createTypedArrayList(ProductionCompaniesItem),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readParcelable(BelongsToCollection::class.java.classLoader),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(originalLanguage)
        parcel.writeString(imdbId)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeString(title)
        parcel.writeString(backdropPath)
        parcel.writeInt(revenue)
        parcel.writeTypedList(genres)
        parcel.writeDouble(popularity)
        parcel.writeTypedList(productionCountries)
        parcel.writeInt(id)
        parcel.writeInt(voteCount)
        parcel.writeInt(budget)
        parcel.writeString(overview)
        parcel.writeString(originalTitle)
        parcel.writeInt(runtime)
        parcel.writeString(posterPath)
        parcel.writeTypedList(spokenLanguages)
        parcel.writeTypedList(productionCompanies)
        parcel.writeString(releaseDate)
        parcel.writeDouble(voteAverage)
        parcel.writeParcelable(belongsToCollection, flags)
        parcel.writeString(tagline)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(homepage)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieDetail> {
        override fun createFromParcel(parcel: Parcel): MovieDetail {
            return MovieDetail(parcel)
        }

        override fun newArray(size: Int): Array<MovieDetail?> {
            return arrayOfNulls(size)
        }
    }
}
