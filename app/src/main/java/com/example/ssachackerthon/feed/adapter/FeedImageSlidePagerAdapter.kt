package com.example.ssachackerthon.feed.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ssachackerthon.feed.FeedFragment
import com.example.ssachackerthon.feed.FeedImageSlideFragment

class FeedImageSlidePagerAdapter (fragment: Fragment, var itemList: ArrayList<String>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = itemList.size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FeedImageSlideFragment(itemList[position])
            1 -> FeedImageSlideFragment(itemList[position])
            else -> FeedImageSlideFragment(itemList[itemList.size-1])
        }
    }
}