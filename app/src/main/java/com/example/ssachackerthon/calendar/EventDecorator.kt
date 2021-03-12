package com.example.ssachackerthon.calendar


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ssachackerthon.R
import com.example.ssachackerthon.base.ApplicationClass
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan


@SuppressLint("UseCompatLoadingForDrawables")
class EventDecorator(private val color: Int, dates: Collection<CalendarDay>, context: Context) : DayViewDecorator {

    private val drawable: Drawable = context.resources.getDrawable(R.drawable.cal_thumbs_up)
    private val dates: HashSet<CalendarDay> = HashSet(dates)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun decorate(view: DayViewFacade) {
        val addingBitmap = BitmapFactory.decodeResource(ApplicationClass.instance.resources, R.drawable.cal_thumbs_up)
        val img = BitmapDrawable(ApplicationClass.instance.resources, addingBitmap)
        val imgs = arrayOf<Drawable>(img)
        val layerDrawable = LayerDrawable(imgs)
        layerDrawable.setLayerInset(0,8,25,8,-8)
        view.setBackgroundDrawable(layerDrawable);
//        view.addSpan(DotSpan(5f, color)); // 날자밑에 점
    }

}