package br.com.tairoroberto.nextel

import android.content.Context
import android.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        setUpSearchView(menu)
        return true
    }

    private fun setUpSearchView(menu: Menu) {
        val searchView = MenuItemCompat.getActionView(menu.findItem(R.id.search)) as? SearchView
        searchView?.apply {
            setIconifiedByDefault(false)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    // presenter.onSearchTextSubmitted(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    //presenter.onSearchTextSubmitted(newText)
                    return true
                }

            })
            MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search),
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            return  super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun hideSoftKeyboard() {
        val view = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE)
                    as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showSoftKeyboard() {
        val view = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(view, 0)
        }
    }
}
