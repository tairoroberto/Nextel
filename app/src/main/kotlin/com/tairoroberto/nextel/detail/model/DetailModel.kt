package com.tairoroberto.nextel.detail.model

import android.util.Log
import com.tairoroberto.nextel.base.api.ApiUtils
import com.tairoroberto.nextel.detail.contract.DetailContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tairo on 8/15/17.
 */
class DetailModel(private val presenter: DetailContract.Presenter) : DetailContract.Model{

    override fun searchMovieById(id : Int) {
        ApiUtils.getApiService()?.searchById(id,"6413f09c0280e9b760d6d91b2933f43f")
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