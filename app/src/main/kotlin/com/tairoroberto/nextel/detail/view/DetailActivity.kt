package com.tairoroberto.nextel.detail.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Images
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.transition.ChangeBounds
import android.view.Menu
import com.tairoroberto.nextel.R
import com.tairoroberto.nextel.base.extension.loadImage
import com.tairoroberto.nextel.base.extension.showSnackBarError
import com.tairoroberto.nextel.detail.contract.DetailContract
import com.tairoroberto.nextel.detail.presenter.DetailPresenter
import com.tairoroberto.nextel.home.model.domain.MovieDetail
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private var shareActionProvider: ShareActionProvider? = null
    private var presenter: DetailContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        setAnimation()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        presenter = DetailPresenter()
        presenter?.attachView(this)

        imageView.isDrawingCacheEnabled = true
        val movieDetail = intent.getParcelableExtra<MovieDetail>("movieDetail")

        presenter?.loadMovie(movieDetail.id)
        showMovie(movieDetail)

        fab.setOnClickListener { showAlertDialog(movieDetail) }
    }

    private fun setAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000
            window.sharedElementExitTransition = changeBounds
        }
    }

    private fun showAlertDialog(movieDetail: MovieDetail) {
        alert {
            title = "Vote"
            message = "Vote for this movie is disable :( \nThe actual note for this movie is \n${movieDetail.voteAverage}"

            yesButton {
                title = "OK"
            }
        }.show()
    }


    override fun showMovie(movieDetail: MovieDetail?) {
        imageView.loadImage(getString(R.string.images_url, movieDetail?.posterPath), progressImage)

        toolbar_layout.title = movieDetail?.title
        textViewName.text = movieDetail?.originalTitle
        textViewOpen.text = movieDetail?.voteCount.toString()
        textViewOverview.text = movieDetail?.overview
        textViewReleaseDate.text = movieDetail?.releaseDate
        textViewRate.text = movieDetail?.voteAverage.toString()

        genres.text = getString(R.string.genres, movieDetail?.genres?.joinToString(" - ") { it.name })
        homePage.text = getString(R.string.home_page, movieDetail?.homepage)
        original_language.text = getString(R.string.original_languages, movieDetail?.originalLanguage)
        production_companies.text = getString(R.string.companies, movieDetail?.productionCompanies?.joinToString(" \n") { it.name })
        production_countries.text = getString(R.string.production_countries, movieDetail?.productionCountries?.joinToString(" \n") { it.name })
        spoken_languages.text = getString(R.string.spoken_languages, movieDetail?.spokenLanguages?.joinToString(" - ") { it.name })
    }

    override fun showSnackBarError(msg: String) {
        showSnackBarError(fab, msg)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_detail, menu)
        val shareItem = menu.findItem(R.id.menu_share)

        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE)
        } else {
            setShareIntent()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty()) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setShareIntent()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setShareIntent() {
        if (shareActionProvider != null) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"

            val bitmap = imageView.drawingCache

            val bitmapPath = Images.Media.insertImage(contentResolver, bitmap, "image_movieDetail", null)
            val bitmapUri = Uri.parse(bitmapPath)

            shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "${textViewName.text} \n\n ${textViewOverview.text}")
            shareActionProvider?.setShareIntent(shareIntent)
        }
    }

    override fun getContext(): Context {
        return this
    }

    override fun getActivity(): Activity? {
        return this
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    companion object {
        val WRITE_EXTERNAL_STORAGE = 2
    }
}
