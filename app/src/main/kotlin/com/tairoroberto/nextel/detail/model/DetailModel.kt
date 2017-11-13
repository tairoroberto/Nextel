package com.tairoroberto.nextel.detail.model

import android.util.Log
import com.tairoroberto.nextel.R
import com.tairoroberto.nextel.base.api.ApiUtils
import com.tairoroberto.nextel.detail.contract.DetailContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tairo on 12/12/17.
 */
class DetailModel(private val presenter: DetailContract.Presenter) : DetailContract.Model{

    override fun searchMovieById(id : Int) {
        ApiUtils.getApiService()?.searchById(id, presenter.getContext()?.getString(R.string.api_v3_auth))
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