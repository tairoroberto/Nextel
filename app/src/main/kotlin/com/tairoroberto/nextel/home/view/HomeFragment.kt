package com.tairoroberto.nextel.home.view


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.transition.ChangeBounds
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.tairoroberto.nextel.R
import com.tairoroberto.nextel.detail.view.DetailActivity
import com.tairoroberto.nextel.home.contract.HomeContract
import com.tairoroberto.nextel.home.model.Movie
import com.tairoroberto.nextel.home.model.MovieDetail
import com.tairoroberto.nextel.home.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_list_movies.*
import kotlinx.android.synthetic.main.movie_item.*
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeContract.View, OnClick {

    private val presenter: HomeContract.Presenter = HomePresenter()
    var listMovies: ArrayList<Movie>? = ArrayList()
    var adapter: HomeRecyclerAdapter? = null
    var recyclerView: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val changeBounds =  ChangeBounds()
            changeBounds.duration = 2000
            activity?.window?.sharedElementExitTransition = changeBounds
        }

        val view: View? = inflater.inflate(R.layout.fragment_list_movies, container, false)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView = view?.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view?.findViewById(R.id.swipeRefreshLayout)

        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

        presenter.loadMovies()

        adapter = HomeRecyclerAdapter(activity, listMovies, this)
        recyclerView?.adapter = adapter

        swipeRefreshLayout?.setOnRefreshListener({
            presenter.loadMovies()
        })

        return view
    }

    override fun showMoviesList(movies: List<Movie>?) {
        val listMovie: ArrayList<Movie> = ArrayList()

        movies?.forEach {
            listMovie.add(it)
        }

        adapter?.update(listMovie)
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onItemClick(movie: Movie) {

        val options: ActivityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity as Activity,
                Pair.create(imageView, "movie"))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("movie_id", movie.id)
            intent.putExtras(options.toBundle())
            startActivity(intent)
        } else {
            activity?.startActivity<DetailActivity>("movie_id" to movie.id)
        }
    }

    override fun updateList(movieDetail: MovieDetail) {
        Toast.makeText(activity, "${movieDetail.originalTitle} adicionado aos favoritos :)", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
        setUpSearchView(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpSearchView(menu: Menu?) {

        val searchView = MenuItemCompat.getActionView(menu?.findItem(R.id.search)) as SearchView
        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.searchMovies(query)
                Log.i("LOG", "onQueryTextSubmit: $query")
                hideSoftKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //presenter.onSearchTextSubmitted(newText)
                Log.i("LOG", "onQueryTextChange: $newText")
                return true
            }

        })
        MenuItemCompat.setOnActionExpandListener(menu?.findItem(R.id.search),
                object : MenuItemCompat.OnActionExpandListener {
                    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                        searchView.requestFocus()
                        showSoftKeyboard()
                        return true
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                        hideSoftKeyboard()
                        return true
                    }
                })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            return super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun hideSoftKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showSoftKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(view, 0)
        }
    }

    override fun getContext(): Context {
        return activity as Context
    }
}
