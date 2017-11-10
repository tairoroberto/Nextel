package com.tairoroberto.nextel.api

import com.tairoroberto.nextel.home.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by tairo on 11/10/17 12:23 AM.
 */
interface Api {

    @GET("/discover")
    fun getMovies(@Query("vote_average.gte") voteAverage: String , @Query("api_key") apiKey: String): Observable<MovieResponse>

    @GET("/search/movie")
    fun search(@Query("query") query: String, @Query("api_key") apiKey: String): Observable<MovieResponse>

    @GET("/movie/{id}")
    fun searchById(@Path("id") movieId: Int, @Query("api_key") apiKey: String): Observable<MovieResponse>
}