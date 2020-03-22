package com.example.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.net.Uri
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.Menu
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import com.bumptech.glide.Glide;
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val GALLERY_REQUEST = 1
    val SAVED_RESULT = "saveResult"
    var imageURI: Uri? = null
    var angle = 0.0f
    val IAdress = "https://images.app.goo.gl/VXWqmYu3v3FcfdAA8"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            imageURI = savedInstanceState.getParcelable(SAVED_RESULT)
            imageView.setImageURI(imageURI)
        }

        gallery.setOnClickListener {

            val photoPickerIntent: Intent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        gallery.setOnLongClickListener {
            Glide
                .with(this)
                .load(IAdress)
                .into(imageView)

            true

        }

        RotateRight.setOnClickListener {
            angle += 90
            imageView.animate().rotation(angle)
        }
        RotateLeft.setOnClickListener {
            angle -= 90
            imageView.animate().rotation(angle)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == Activity.RESULT_OK) {
                val selectedImageURI: Uri = imageReturnedIntent?.data!!
                imageURI = selectedImageURI
                imageView.setImageURI(selectedImageURI)
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imageURI = savedInstanceState.getParcelable(SAVED_RESULT)
        imageView.setImageURI(imageURI)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SAVED_RESULT, imageURI)
    }
}
