package com.tairoroberto.nextel.home.contract

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.content.Context
import br.com.tairoroberto.lovedogs.base.BaseMVP
import com.tairoroberto.nextel.home.model.domain.Movie
import com.tairoroberto.nextel.home.model.domain.MovieResponse

/**
 * Created by tairo on 12/12/17.
 */
class HomeContract {

    interface Model {
        fun listMovies()
        fun searchMovie(query: String)
        fun listMoviesFromBD()
    }

    interface View : BaseMVP.View {
        fun showMoviesList(movies: List<Movie>?)
        fun showProgress(b: Boolean)
        fun showSnackBarError(str: String)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun loadMovies()
        fun manipulateMovieResponse(movieResponse: MovieResponse)
        fun manipulateMovieResponseDB(movies: List<Movie>?)
        fun showError(str: String)
        fun searchMovies(query: String)
        fun loadMoviesFromBD()
        fun getContext(): Context?
        fun getActivity(): Activity?
    }
}