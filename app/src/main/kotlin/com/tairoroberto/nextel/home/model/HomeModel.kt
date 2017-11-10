package com.tairoroberto.nextel.home.model

import android.util.Log
import com.tairoroberto.nextel.api.ApiUtils
import com.tairoroberto.nextel.home.contract.HomeContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tairo on 8/15/17.
 */
class HomeModel(private val presenter: HomeContract.Presenter) : HomeContract.Model{

    override fun listMovies() {
        ApiUtils.getApiService()?.getMovies("","")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateMovieResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showError(error.message as String)
                })
    }

    override fun searchMovie(query : String) {
        ApiUtils.getApiService()?.search("","")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateMovieResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showError(error.message as String)
                })
    }


    override fun searchMovieById(id : Int) {
        ApiUtils.getApiService()?.searchById(id,"")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateMovieResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showError(error.message as String)
                })
    }

}