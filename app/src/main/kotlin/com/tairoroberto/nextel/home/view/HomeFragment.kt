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
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.tairoroberto.nextel.R
import com.tairoroberto.nextel.base.extension.isConected
import com.tairoroberto.nextel.base.extension.showProgress
import com.tairoroberto.nextel.base.extension.showSnackBarError
import com.tairoroberto.nextel.detail.view.DetailActivity
import com.tairoroberto.nextel.home.contract.HomeContract
import com.tairoroberto.nextel.home.model.domain.Movie
import com.tairoroberto.nextel.home.model.domain.MovieDetail
import com.tairoroberto.nextel.home.presenter.HomePresenter

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeContract.View, OnClick {

    private val presenter: HomeContract.Presenter = HomePresenter()
    private var listMovies: ArrayList<Movie>? = ArrayList()
    private var adapter: HomeRecyclerAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var progress: ProgressBar? = null
    private var withoutData: TextView? = null

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
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000
            activity?.window?.sharedElementExitTransition = changeBounds
        }

        val view: View? = inflater.inflate(R.layout.fragment_list_movies, container, false)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView = view?.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view?.findViewById(R.id.swipeRefreshLayout)
        progress = view?.findViewById(R.id.progress)
        withoutData = view?.findViewById(R.id.withoutData)

        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        adapter = HomeRecyclerAdapter(activity, listMovies, this)
        recyclerView?.adapter = adapter
        swipeRefreshLayout?.setOnRefreshListener({
            loadMovies()
        })

        showProgress(true)
        loadMovies()

        return view
    }

    private fun loadMovies() {
        if (activity?.isConected() == true) {
            presenter.loadMovies()
        } else {
            presenter.loadMoviesFromBD()
        }
    }

    override fun showProgress(b: Boolean) {
        activity?.showProgress(recyclerView, progress, b)
    }

    override fun showSnackBarError(str: String) {
        activity?.showSnackBarError(recyclerView,str)
    }

    override fun showMoviesList(movies: List<Movie>?) {
        val listMovie: ArrayList<Movie> = ArrayList()

        movies?.forEach {
            listMovie.add(it)
        }

        withoutData?.visibility = GONE
        if (listMovie.isEmpty()) {
            withoutData?.visibility = VISIBLE
        }

        adapter?.update(listMovie)
        swipeRefreshLayout?.isRefreshing = false
        showProgress(false)
    }

    override fun onItemClick(movie: Movie, imageView: ImageView) {
        val movieDetail = MovieDetail()
        movieDetail.id = movie.idMovie
        movieDetail.title = movie.title
        movieDetail.originalTitle = movie.originalTitle
        movieDetail.posterPath = movie.posterPath
        movieDetail.releaseDate = movie.releaseDate
        movieDetail.voteAverage = movie.voteAverage

        val options: ActivityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context as Activity, Pair.create(imageView, "movie"))

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("movieDetail", movieDetail)
        context.startActivity(intent, options.toBundle())
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
