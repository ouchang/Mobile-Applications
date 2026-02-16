package com.example.gallery

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    companion object {
        var images = ArrayList<Image>()
        var templateDesc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"
    }

    var titles : ArrayList<String> = arrayListOf("Show", "Add")

    lateinit var tabLayout : TabLayout
    lateinit var viewPager : ViewPager2
    lateinit var viewPagerAdapter : ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        viewPagerAdapter = ViewPagerAdapter(this)


        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(
            tabLayout!!,
            viewPager!!
        ) { tab: TabLayout.Tab, position: Int -> tab.text = titles[position] }.attach()


        Log.i("pic", "URI: " + getUriString(R.drawable.pic1))

        if(savedInstanceState != null) {
            images = savedInstanceState.getParcelableArrayList("Images")!!
        } else {
            images.add(Image(templateDesc, getUriString(R.drawable.pic1), 3.5f))
            images.add(Image(templateDesc, getUriString(R.drawable.pic2), 5.0f))
            images.add(Image(templateDesc, getUriString(R.drawable.pic3), 3.2f))
            images.add(Image(templateDesc, getUriString(R.drawable.pic4), 4.5f))
            images.add(Image(templateDesc, getUriString(R.drawable.pic5), 4.8f))
            images.add(Image(templateDesc, getUriString(R.drawable.pic6), 3.3f))
            images.add(Image(templateDesc, getUriString(R.drawable.pic7), 4.2f))
            images.add(Image(templateDesc, getUriString(R.drawable.pic8), 3.9f))
            images.add(Image(templateDesc, getUriString(R.drawable.pic9), 4.0f))
        }
    }



    fun getUriString(id: Int) : String {
        return Uri.parse("android.resource://" + this.packageName + "/" + id).toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("Images", images)
        super.onSaveInstanceState(outState)
    }
}