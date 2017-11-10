package br.com.tairoroberto.lovedogs.base

import android.app.Activity
import android.content.Context

/**
 * Created by tairo on 11/10/17.
 */
class BaseMVP {

    interface View {
        fun getContext(): Context
        fun getActivity(): Activity
    }

    interface Presenter<in V> {
        fun attachView(view: V)
        fun detachView()
    }
}