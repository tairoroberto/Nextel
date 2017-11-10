package com.tairoroberto.nextel.home.view


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tairoroberto.nextel.home.contract.HomeContract
import com.tairoroberto.nextel.home.model.MovieDetail
import com.tairoroberto.nextel.home.presenter.HomePresenter
import com.tairoroberto.nextel.detail.DetailActivity
import com.tairoroberto.nextel.R
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeContract.View, OnClick {
    override fun getContext(): Context {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getActivity(): Activity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAtivity(): FragmentActivity? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val presenter: HomeContract.Presenter = HomePresenter()
    var listMovies: ArrayList<MovieDetail>? = ArrayList()
    var adapter: HomeRecyclerAdapter? = null
    var recyclerViewMovies: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View? = inflater.inflate(R.layout.fragment_list_movies, container, false)

        val layoutManager = LinearLayoutManager(activity)
        recyclerViewMovies = view?.findViewById(R.id.recyclerViewPets)
        swipeRefreshLayout = view?.findViewById(R.id.swipeRefreshLayout)

        recyclerViewMovies?.layoutManager = layoutManager
        recyclerViewMovies?.setHasFixedSize(true)

        presenter.loadMovies()

        adapter = HomeRecyclerAdapter(activity as Context, listMovies, this)
        recyclerViewMovies?.adapter = adapter

        swipeRefreshLayout?.setOnRefreshListener({
            presenter.loadMovies()
        })

        return view
    }

    override fun showMoviesList(petshops: ArrayList<MovieDetail>) {
        adapter?.update(petshops)
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onItemClick(petShop: MovieDetail) {
        activity?.startActivity<DetailActivity>("petShop" to petShop)
    }

    override fun updateList(movie: MovieDetail) {
        Toast.makeText(activity, "${movie.originalTitle} adicionado aos favoritos :)", Toast.LENGTH_SHORT).show()
    }
}
