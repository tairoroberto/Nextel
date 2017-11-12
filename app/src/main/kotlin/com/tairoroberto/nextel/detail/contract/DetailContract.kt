package com.tairoroberto.nextel.detail.contract

import br.com.tairoroberto.lovedogs.base.BaseMVP
import com.tairoroberto.nextel.home.model.domain.MovieDetail

/**
 * Created by tairo on 8/15/17.
 */
class DetailContract {

    interface Model {
        fun searchMovieById(id : Int)
    }

    interface View : BaseMVP.View {
        fun showMovie(movieDetail: MovieDetail?)
        fun showSnackBarError(msg:String)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun loadMovie(id: Int)
        fun manipulateMovieResponse(movieDetail: MovieDetail?)
        fun showError(str: String)
    }
}