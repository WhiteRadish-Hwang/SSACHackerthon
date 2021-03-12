package com.example.ssachackerthon.feed

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.ssachackerthon.R
import com.example.ssachackerthon.base.BaseFragment
import com.example.ssachackerthon.calendar.EventDecorator
import com.example.ssachackerthon.databinding.FragmentFeedBinding
import com.example.ssachackerthon.databinding.FragmentHomeBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import java.util.*

class FeedFragment:BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::bind, R.layout.fragment_feed) {

    companion object{
        fun getInstance() = FeedFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}