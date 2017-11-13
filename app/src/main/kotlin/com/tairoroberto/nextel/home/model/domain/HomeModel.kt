package com.tairoroberto.nextel.home.model.domain

import android.util.Log
import com.tairoroberto.nextel.R
import com.tairoroberto.nextel.base.api.ApiUtils
import com.tairoroberto.nextel.home.contract.HomeContract
import com.tairoroberto.nextel.home.model.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync

/**
 * Created by tairo on 12/11/17.
 */
class HomeModel(private val presenter: HomeContract.Presenter) : HomeContract.Model {
    override fun listMovies() {
        ApiUtils.getApiService()?.getMovies("5", presenter.getContext()?.getString(R.string.api_v3_auth))
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateMovieResponse(it)
                }, { error ->
                    Log.i("LOG", " Error: ${error.message}")
                    presenter.showError(error.message as String)
                })
    }

    override fun searchMovie(query: String) {
        ApiUtils.getApiService()?.search(query, presenter.getContext()?.getString(R.string.api_v3_auth))
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateMovieResponse(it)
                }, { error ->
                    Log.i("LOG", "Error: ${error.message}")
                    presenter.showError(error.message as String)
                })
    }

    override fun listMoviesFromBD() {

        presenter.getActivity().doAsync {
            AppDatabase.getInstance(presenter.getContext()).movieDAO().loadAllMovies().observeForever {
                presenter.getActivity()?.runOnUiThread {
                    presenter.manipulateMovieResponseDB(it)
                }
            }
        }
    }
}
