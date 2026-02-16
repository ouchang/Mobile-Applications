package com.example.gallery

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    var oldRating : Float = 0f
    var currRating : Float = 0f
    lateinit var item : Image


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        item = intent.getParcelableExtra("Item", Image::class.java)!!

        val imageFragment : View = findViewById(R.id.imageFragment)
        val infoFragment : View = findViewById(R.id.infoFragment)
        val imageView : ImageView = imageFragment.findViewById(R.id.imageView)
        val descTextView : TextView = infoFragment.findViewById(R.id.descTextView)
        val ratingBar : RatingBar = infoFragment.findViewById(R.id.ratingBar)
        val ratingTextView : TextView = infoFragment.findViewById(R.id.ratingTextView)

        currRating = item.rating
        imageView.setImageURI(Uri.parse(item.imagePath))
        descTextView.text = item.desc
        ratingTextView.text = item.rating.toString()
        ratingBar.rating = item.rating
    }
}