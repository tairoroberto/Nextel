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
import com.tairoroberto.nextel.home.model.MovieDetail
import com.tairoroberto.nextel.R
import com.tairoroberto.nextel.base.extension.loadImage
import com.tairoroberto.nextel.detail.contract.DetailContract
import com.tairoroberto.nextel.detail.presenter.DetailPresenter
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*


class DetailActivity : AppCompatActivity(), DetailContract.View {

    private var movieDetail: MovieDetail? = null
    private var shareActionProvider: ShareActionProvider? = null
    private var presenter: DetailContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000
            window.sharedElementExitTransition = changeBounds
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        presenter = DetailPresenter()
        presenter?.attachView(this)

        imageView.isDrawingCacheEnabled = true

        val movieId = intent.getIntExtra("movie_id", 0)
        presenter?.loadMovie(movieId)
    }


    override fun showMovie(movieDetail: MovieDetail?) {
        imageView.loadImage(movieDetail?.posterPath)

        textViewName.text = movieDetail?.originalTitle
        textViewOpen.text = movieDetail?.voteCount.toString()
        textViewOverview.text = movieDetail?.overview
        textViewReleaseDate.text = movieDetail?.releaseDate
        textViewRate.text = movieDetail?.voteAverage.toString()
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
        when(requestCode){
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, "${movieDetail?.title} \n\n ${movieDetail?.overview}")
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
