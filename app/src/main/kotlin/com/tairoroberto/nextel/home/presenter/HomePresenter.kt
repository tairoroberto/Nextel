package com.tairoroberto.nextel.home.presenter

import android.util.Log
import com.tairoroberto.nextel.home.contract.DetailContract
import com.tairoroberto.nextel.home.model.Movie
import com.tairoroberto.nextel.home.model.HomeModel

/**
 * Created by tairo on 8/15/17.
 */
class HomePresenter : DetailContract.Presenter {

    private var view: DetailContract.View? = null
    private var model: DetailContract.Model? = null
    override fun attachView(view: DetailContract.View) {
        this.view = view
        this.model = HomeModel(this)
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadPetshops() {
        model?.listarPetshops()
    }

    override fun manipulatePetshopsResponse(petshopsResponse: PetshopsResponse) {
        Log.i("LOG", "petshops ${petshopsResponse.petshops}")

        if (petshopsResponse.success) {
            view?.showPetshopsList(petshopsResponse.petshops)
        }
    }

    override fun updatePetshop(petShop: Movie?) {
        petShop?.favorite = true
        model?.updatePetshop(petShop as Movie)
    }

    override fun manipulateUpdatePetshopResponse(updatePetShopResponse: UpdatePetShopResponse) {
        if (updatePetShopResponse.success) {
            view?.updateList(updatePetShopResponse.petShop)
        } else {
            view?.getActivity()?.showSnackBarError(view?.getActivity()?.recyclerViewPets!!, "Falha ao adicionar favorito :( ")
        }
    }

    override fun showError(str: String) {
        view?.getActivity()?.showSnackBarError(view?.getActivity()?.recyclerViewPets!!, str)
    }
}