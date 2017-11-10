package com.tairoroberto.nextel.detail

import android.Manifest
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
import android.telephony.PhoneNumberUtils
import android.transition.ChangeBounds
import android.view.Menu
import com.tairoroberto.nextel.extension.loadImage
import com.tairoroberto.nextel.home.model.MovieDetail
import com.tairoroberto.nextel.R
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*
import java.util.*


class DetailActivity : AppCompatActivity() {

    private var petShop: MovieDetail? = null
    private var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000
            window.sharedElementExitTransition = changeBounds
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        petShop = savedInstanceState?.getParcelable("petshop")

        if (intent.extras != null) {
            petShop = intent.extras.getParcelable("petShop")
        }

        imageViewPetShop.isDrawingCacheEnabled = true
        imageViewPetShop.loadImage(petShop?.imageUrl)

        textViewAddress.text = petShop?.address
        textViewOpen.text = petShop?.open
        textViewClose.text = petShop?.close
        textViewDescription.text = petShop?.description

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textViewPhone.text = PhoneNumberUtils.formatNumber(petShop?.phone, Locale.getDefault().country)
        }

        textViewPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${petShop?.phone}")
            startActivity(intent)
        }

        imageViewPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${petShop?.phone}")
            startActivity(intent)
        }

        fab.setOnClickListener {
            val favorite = petShop?.favorite as Boolean

            petShop?.favorite = !favorite
        }
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

            val bitmap = imageViewPetShop.drawingCache

            val bitmapPath = Images.Media.insertImage(contentResolver, bitmap, "image_petshop", null)
            val bitmapUri = Uri.parse(bitmapPath)

            shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "${petShop?.name} \n\n ${petShop?.address}")
            shareActionProvider?.setShareIntent(shareIntent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("petshop", petShop)
    }

    companion object {
        val WRITE_EXTERNAL_STORAGE = 2
    }
}
