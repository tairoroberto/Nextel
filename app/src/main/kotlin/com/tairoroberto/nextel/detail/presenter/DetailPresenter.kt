package com.tairoroberto.nextel.detail.presenter

import android.content.Context
import android.util.Log
import com.tairoroberto.nextel.base.extension.showSnackBarError
import com.tairoroberto.nextel.detail.contract.DetailContract
import com.tairoroberto.nextel.detail.model.DetailModel
import com.tairoroberto.nextel.home.model.domain.MovieDetail
import kotlinx.android.synthetic.main.fragment_list_movies.*

/**
 * Created by tairo on 12/12/17.
 */
class DetailPresenter : DetailContract.Presenter {

    private var view: DetailContract.View? = null
    private var model: DetailContract.Model? = null

    override fun attachView(view: DetailContract.View) {
        this.view = view
        this.model = DetailModel(this)
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadMovie(id: Int) {
        model?.searchMovieById(id)
    }

    override fun manipulateMovieResponse(movieDetail: MovieDetail?) {
        Log.i("LOG", "movie ${movieDetail?.title}")

        view?.showMovie(movieDetail)
    }

    override fun showError(str: String) {
        view?.showSnackBarError(str)
    }

    override fun getContext(): Context? {
        return view?.getContext()
    }
}