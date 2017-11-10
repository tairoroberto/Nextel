package com.tairoroberto.nextel.home.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(@SerializedName("original_language")
                 val originalLanguage: String = "",

                       @SerializedName("imdb_id")
                 val imdbId: String = "",

                       val video: Boolean = false,

                       val title: String = "",

                   @SerializedName("backdrop_path")
                 val backdropPath: String = "",

                       val revenue: Int = 0,

                       val genres: List<GenresItem>? = null,

                       val popularity: Double = 0.0,

                       @SerializedName("production_countries")
                 val productionCountries: List<ProductionCountriesItem>? = null,

                       val id: Int = 0,

                       @SerializedName("vote_count")
                 val voteCount: Int = 0,

                       val budget: Int = 0,

                       val overview: String = "",

                       @SerializedName("original_title")
                 val originalTitle: String = "",

                       val runtime: Int = 0,

                       @SerializedName("poster_path")
                 val posterPath: String = "",

                       @SerializedName("spoken_languages")
                 val spokenLanguages: List<SpokenLanguagesItem>? = null,

                       @SerializedName("production_companies")
                 val productionCompanies: List<ProductionCompaniesItem>? = null,

                       @SerializedName("release_date")
                 val releaseDate: String = "",

                       @SerializedName("vote_average")
                 val voteAverage: Double = 0.0,

                       @SerializedName("belongs_to_collection")
                 val belongsToCollection: BelongsToCollection? = null,

                       val tagline: String = "",

                       val adult: Boolean = false,

                       val homepage: String = "",

                       val status: String = "")