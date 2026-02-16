package com.example.gallery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ShowImageFragment : Fragment() {
    companion object {
        lateinit var recyclerView: RecyclerView
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
        var ratingStr : String = data?.getStringExtra("tag")!!
        var position : Int = data?.getIntExtra("Position", -1)!!
        Log.i("rating", "RESULT RATING_STR: " + ratingStr)
        Toast.makeText(requireActivity(), ratingStr, Toast.LENGTH_SHORT).show()

        val item : Image = MainActivity.images[position]
        item.rating = ratingStr.toFloat()
        resetRecycleView()
    }

    var resultLauncherShowDetail = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        val data = result.data
        var ratingStr : String = data?.getStringExtra("tag")!!
        var position : Int = data?.getIntExtra("Position", -1)!!
        Log.i("rating", "RESULT RATING_STR: " + ratingStr)
        Toast.makeText(requireContext(), ratingStr, Toast.LENGTH_SHORT).show()

        val item : Image = MainActivity.images[position]
        item.rating = ratingStr.toFloat()
        sortImages()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_show_image, container, false)

        recyclerView = view?.findViewById(R.id.recycleView)!!
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = GalleryAdapter(MainActivity.images, requireContext()) {position : Int ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            val item = MainActivity.images[position]
            Log.i("rating", "MAIN POSITION: " + position)
            with(intent) {
                putExtra("Item", item)
                putExtra("Position", position)
            }

            resultLauncher.launch(intent)
        }

        return view
    }



    fun sortImages() {
        MainActivity.images.sortByDescending { it.rating }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    fun resetRecycleView() {
        recyclerView.adapter = GalleryAdapter(MainActivity.images, requireContext()) { position : Int ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            val item = MainActivity.images[position]
            Log.i("rating", "MAIN POSITION: " + position)
            with(intent) {
                putExtra("Item", item)
                putExtra("Position", position)
            }
            //startActivity(intent)
            resultLauncherShowDetail.launch(intent)
        }

        sortImages()
    }
}