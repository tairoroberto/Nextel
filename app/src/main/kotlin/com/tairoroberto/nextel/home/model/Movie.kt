package com.tairoroberto.nextel.home.model

import com.google.gson.annotations.SerializedName

data class Movie(val overview: String = "",
                 @SerializedName("original_language")
                       val originalLanguage: String = "",
                 @SerializedName("original_title")
                       val originalTitle: String = "",
                 val video: Boolean = false,
                 val title: String = "",
                 @SerializedName("poster_path")
                       val posterPath: String = "",
                 @SerializedName("backdrop_path")
                       val backdropPath: String? = "",
                 @SerializedName("release_date")
                       val releaseDate: String = "",
                 @SerializedName("vote_average")
                       val voteAverage: Int = 0,
                 val popularity: Double = 0.0,
                 val id: Int = 0,
                 val adult: Boolean = false,
                 @SerializedName("vote_count")
                       val voteCount: Int = 0)