package com.tairoroberto.nextel.home.presenter

import android.util.Log
import com.tairoroberto.nextel.base.extension.showSnackBarError
import com.tairoroberto.nextel.home.contract.HomeContract
import com.tairoroberto.nextel.home.model.HomeModel
import com.tairoroberto.nextel.home.model.MovieResponse
import kotlinx.android.synthetic.main.fragment_list_movies.*

/**
 * Created by tairo on 8/15/17.
 */
class HomePresenter : HomeContract.Presenter {

    private var view: HomeContract.View? = null
    private var model: HomeContract.Model? = null
    override fun attachView(view: HomeContract.View) {
        this.view = view
        this.model = HomeModel(this)
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadMovies() {
        model?.listMovies()
    }

    override fun manipulateMovieResponse(movieResponse: MovieResponse) {
        Log.i("LOG", "movie ${movieResponse.results}")

        view?.showMoviesList(movieResponse.results)
    }

    override fun showError(str: String) {
        view?.getActivity()?.showSnackBarError(view?.getActivity()?.recyclerView!!, str)
    }

    override fun searchMovies(query: String) {
        model?.searchMovie(query)
    }
}