package com.tairoroberto.nextel.home.view


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tairoroberto.nextel.home.contract.DetailContract
import com.tairoroberto.nextel.home.model.Movie
import com.tairoroberto.nextel.home.presenter.HomePresenter
import com.tairoroberto.nextel.detail.DetailActivity
import com.tairoroberto.nextel.R
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), DetailContract.View, OnClick {

    private val presenter: DetailContract.Presenter = HomePresenter()
    var listPetshops: ArrayList<Movie>? = ArrayList()
    var adapter: HomeRecyclerAdapter? = null
    var recyclerViewPets: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // presenter.attachView(this)
    }

    override fun onDestroy() {
        //presenter.detachView()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View? = inflater.inflate(R.layout.fragment_list_petshops, container, false)

        val layoutManager = LinearLayoutManager(activity)
        recyclerViewPets = view?.findViewById(R.id.recyclerViewPets)
        swipeRefreshLayout = view?.findViewById(R.id.swipeRefreshLayout)

        recyclerViewPets?.layoutManager = layoutManager
        recyclerViewPets?.setHasFixedSize(true)

        presenter.loadPetshops()

        adapter = HomeRecyclerAdapter(activity as Context, listPetshops, this)
        recyclerViewPets?.adapter = adapter

        swipeRefreshLayout?.setOnRefreshListener({
            presenter.loadPetshops()
        })

        return view
    }

    override fun showPetshopsList(petshops: ArrayList<Movie>) {
        adapter?.update(petshops)
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onItemClick(petShop: Movie) {
        activity?.startActivity<DetailActivity>("petShop" to petShop)
    }

    override fun updateList(petShop: Movie) {
        Toast.makeText(activity, "${petShop.name} adicionado aos favoritos :)", Toast.LENGTH_SHORT).show()
    }
}
