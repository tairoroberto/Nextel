package com.tairoroberto.nextel.home.contract

import android.support.v4.app.FragmentActivity
import br.com.tairoroberto.lovedogs.base.BaseMVP
import com.tairoroberto.nextel.home.model.MovieDetail
import com.tairoroberto.nextel.home.model.MovieResponse

/**
 * Created by tairo on 8/15/17.
 */
class HomeContract {

    interface Model {
        fun listMovies()
        fun searchMovie(query: String)
        fun searchMovieById(id : Int)
    }

    interface View : BaseMVP.View {
        fun getAtivity() : FragmentActivity?
        fun showMoviesList(movies: ArrayList<MovieDetail>)
        fun updateList(movieDetail: MovieDetail)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun loadMovies()
        fun manipulateMovieResponse(movieResponse: MovieResponse)
        fun showError(str: String)
        fun updateMovie(movieDetail: MovieDetail?)
        fun manipulateUpdateMovieResponse(updateResponse: String)
    }
}