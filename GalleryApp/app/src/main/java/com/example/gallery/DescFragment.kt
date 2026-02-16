package com.example.gallery

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import java.io.File
import java.io.IOException


class DescFragment : Fragment() {
    lateinit var applyButton : Button
    lateinit var descEditText : EditText
    lateinit var takeImageButton: Button
    lateinit var imageView : ImageView

    var REQUEST_IMAGE_CAPTURE : Int = 100

    //var imagePath = Uri.parse("android.resource://com.example.gallery/" + R.drawable.pic1).toString()
    var imagePath = ""
    lateinit var imageUri : Uri
    lateinit var imageDesc : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_desc, container, false)

        applyButton = view?.findViewById(R.id.applyButton)!!
        descEditText = view?.findViewById(R.id.descEditText)!!
        takeImageButton = view?.findViewById(R.id.takeImageButton)!!

        var imageFragment = parentFragmentManager.findFragmentById(R.id.imageFragment)
        imageView = imageFragment!!.requireView().findViewById(R.id.imageView)

        view?.findViewById<Button>(R.id.takeImageButton)!!.setOnClickListener {
            val addImage : AddImageActivity = AddImageActivity()
            onTakeImageClick(it)
        }

        view?.findViewById<Button>(R.id.applyButton)!!.setOnClickListener {
            val addImage : AddImageActivity = AddImageActivity()
            onApplyClick(it)
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            imageView.setImageURI(Uri.parse(imagePath))
            imageView.tag = Uri.parse(imagePath).toString()
            applyButton.isEnabled = true
            val file = File(imagePath)
            imageUri = Uri.fromFile(file)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onTakeImageClick(view : View) {
        //temporary
        //Toast.makeText(activity, "TAKE A PIC", Toast.LENGTH_SHORT).show()

        val takeImageIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var imageFile : File? = null

        try {
            imageFile = createImageFile()
        } catch(e : IOException) {
            Toast.makeText(activity, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        if(imageFile != null) {
            try {
                var imageUri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID+".provider", imageFile)
                takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(takeImageIntent, REQUEST_IMAGE_CAPTURE)
            } catch(e : ActivityNotFoundException) {
                Toast.makeText(activity, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        applyButton.isEnabled = true
    }

    fun onApplyClick(view : View) {
        imageDesc = descEditText.text.toString()

        MainActivity.images.add(Image(imageDesc, imageUri.toString(), 3.5f))
        ShowImageFragment.recyclerView.adapter?.notifyDataSetChanged()

        imageView.setImageURI(getUri(R.drawable.image_not_found))
        descEditText.setText("Write here your description...")
    }

    fun createImageFile() : File? {
        val time = System.currentTimeMillis()
        val fileName : String = time.toString()
        val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/GalleryPhotos")

        if(!storageDir.exists()) {
            storageDir.mkdir()
        }

        val image = File.createTempFile(
            "pic_$fileName",
            ".jpg",
            storageDir
        )

        imagePath = image.absolutePath
        return image
    }


    fun getUri(id: Int) : Uri {
        return Uri.parse("android.resource://" + requireActivity().packageName + "/" + id)
    }
}