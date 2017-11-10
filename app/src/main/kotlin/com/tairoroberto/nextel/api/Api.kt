package br.com.tairoroberto.nextel.api

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by tairo on 11/10/17 12:23 AM.
 */
interface Api {

    @GET("/discover")
    fun getMovies(@Query("vote_average.gte") voteAverage: String = "5", @Query("api_key") apiKey: String): Observable<List<String>>

    @GET("/search/movie")
    fun search(@Query("query") query: String, @Query("api_key") apiKey: String): Observable<List<String>>
}