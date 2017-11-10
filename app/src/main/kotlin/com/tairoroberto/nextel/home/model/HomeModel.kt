package com.tairoroberto.nextel.home.model

import android.util.Log
import com.tairoroberto.nextel.api.ApiUtils
import com.tairoroberto.nextel.home.contract.DetailContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.schedulers.Schedulers

/**
 * Created by tairo on 8/15/17.
 */
class HomeModel(private val presenter: DetailContract.Presenter) : DetailContract.Model{

    override fun listarPetshops() {
        ApiUtils.getApiService()?.search("","")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulatePetshopsResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showError(error.message as String)
                })
    }

    override fun updatePetshop(petShop: Movie) {
        ApiUtils.getApiService()?.getMovies("", "")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateUpdatePetshopResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showError("Falha na comunicação :(")
                })
    }
}