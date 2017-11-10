package com.tairoroberto.nextel.home.presenter

import android.util.Log
import com.tairoroberto.nextel.extension.showSnackBarError
import com.tairoroberto.nextel.home.contract.HomeContract
import com.tairoroberto.nextel.home.model.MovieDetail
import com.tairoroberto.nextel.home.model.HomeModel
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

    override fun manipulatePetshopsResponse(petshopsResponse: PetshopsResponse) {
        Log.i("LOG", "petshops ${petshopsResponse.petshops}")

        if (petshopsResponse.success) {
            view?.showMoviesList(petshopsResponse.petshops)
        }
    }

    override fun updateMovie(petShop: MovieDetail?) {
        petShop?.favorite = true
        model?.updateMovie(petShop as MovieDetail)
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