package com.example.ssachackerthon.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.ssachackerthon.R
import com.example.ssachackerthon.base.BaseFragment
import com.example.ssachackerthon.calendar.EventDecorator
import com.example.ssachackerthon.databinding.FragmentHomeBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import java.util.*

class HomeFragment:BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = binding.calendar

        calendar.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(2017, 0, 1))
            .setMaximumDate(CalendarDay.from(2030, 11, 31))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()
        calendar.topbarVisible = false
        calendar.setWeekDayLabels(R.array.week_day)

        val calendarDayList: ArrayList<CalendarDay> = ArrayList()
//        calendarDayList.add(CalendarDay.today())

        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            if (date !in calendarDayList) {
                calendarDayList.add(date)
                binding.calendar.addDecorators(EventDecorator(Color.RED, calendarDayList, context!!))
            } else {
                calendarDayList.remove(date)
                binding.calendar.removeDecorators()
            }
            Log.e("TAG", "onCreate: $calendarDayList")
        }
    }
}