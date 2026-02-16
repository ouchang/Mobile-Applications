package com.example.gallery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var titles : ArrayList<String> = arrayListOf("Show", "Add")
    var fragments : ArrayList<Fragment> = arrayListOf(ShowImageFragment(), AddImageFragment())

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return ShowImageFragment()
            1 -> return AddImageFragment()
        }

        return ShowImageFragment()
    }

    fun getMyFragments():  ArrayList<Fragment>{
        return fragments
    }

    fun modifyMyFragments(fragment : Fragment, position: Int) {
        fragments.remove(fragments.get(position))
        fragments.add(fragment)
    }
}