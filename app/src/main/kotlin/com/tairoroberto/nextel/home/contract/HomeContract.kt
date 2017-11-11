package com.tairoroberto.nextel.home.contract

import br.com.tairoroberto.lovedogs.base.BaseMVP
import com.tairoroberto.nextel.home.model.Movie
import com.tairoroberto.nextel.home.model.MovieDetail
import com.tairoroberto.nextel.home.model.MovieResponse

/**
 * Created by tairo on 8/15/17.
 */
class HomeContract {

    interface Model {
        fun listMovies()
        fun searchMovie(query: String)
    }

    interface View : BaseMVP.View {
        fun showMoviesList(movies: List<Movie>?)
        fun updateList(movieDetail: MovieDetail)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun loadMovies()
        fun manipulateMovieResponse(movieResponse: MovieResponse)
        fun showError(str: String)
        fun searchMovies(query: String)
    }
}