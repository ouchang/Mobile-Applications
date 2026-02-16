package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView


class InfoFragment : Fragment() {
    lateinit var ratingBar: RatingBar
    lateinit var ratingTextView: TextView
    lateinit var submitButton: Button
    lateinit var ratingStr : String
    var position : Int = -2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_info, container, false)

        var myActivity = activity as DetailActivity
        position = myActivity.intent.getIntExtra("Position", -1)

        ratingBar = view?.findViewById(R.id.ratingBar)!!
        ratingTextView = view?.findViewById(R.id.ratingTextView)!!
        submitButton = view?.findViewById(R.id.submitButton)!!

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingStr = ratingBar.rating.toString()
            ratingTextView.text = ratingStr
            Log.i("rating", "RATING_STR: " + ratingStr + " TEXT_VIEW: " + ratingTextView.text)
            true
        }

        submitButton.setOnClickListener {
            var myIntent = Intent()
            myIntent.putExtra("tag", ratingStr)
            myIntent.putExtra("Position", position)
            Log.i("rating", "SUBMIT RATING_STR: " + ratingStr + " TEXT_VIEW: " + ratingTextView.text)
            requireActivity().setResult(Activity.RESULT_OK, myIntent)
            requireActivity().finish()
        }

        // Inflate the layout for this fragment
        return view
    }


}