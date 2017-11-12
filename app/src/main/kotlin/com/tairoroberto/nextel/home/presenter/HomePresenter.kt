package com.tairoroberto.nextel.home.presenter

import android.app.Activity
import android.content.Context
import android.util.Log
import com.tairoroberto.nextel.base.extension.showSnackBarError
import com.tairoroberto.nextel.home.contract.HomeContract
import com.tairoroberto.nextel.home.model.AppDatabase
import com.tairoroberto.nextel.home.model.domain.HomeModel
import com.tairoroberto.nextel.home.model.domain.Movie
import com.tairoroberto.nextel.home.model.domain.MovieResponse
import kotlinx.android.synthetic.main.fragment_list_movies.*
import org.jetbrains.anko.doAsync

/**
 * Created by tairo on 8/15/17.
 */
class HomePresenter : HomeContract.Presenter {

    private var view: HomeContract.View? = null
    private var model: HomeContract.Model? = null


    override fun attachView(view: HomeContract.View) {
        this.view = view
        this.model = HomeModel(this)
        model = HomeModel(this)
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadMovies() {
        model?.listMovies()
    }

    override fun manipulateMovieResponse(movieResponse: MovieResponse) {
        Log.i("LOG", "movie ${movieResponse.results}")

        view?.getActivity()?.doAsync {
            AppDatabase.getInstance(view?.getContext()).movieDAO().insertAll(movieResponse.results)
        }

        view?.showMoviesList(movieResponse.results)
    }

    override fun manipulateMovieResponseDB(movies: List<Movie>?) {
        Log.i("LOG", "movie $movies")

        view?.showMoviesList(movies)
    }

    override fun showError(str: String) {
        view?.showSnackBarError(str)
        view?.showProgress(false)
    }

    override fun searchMovies(query: String) {
        model?.searchMovie(query)
    }

    override fun loadMoviesFromBD() {
        model?.listMoviesFromBD()
    }

    override fun getContext(): Context? {
        return view?.getContext()
    }

    override fun getActivity(): Activity? {
        return view?.getActivity()
    }
}