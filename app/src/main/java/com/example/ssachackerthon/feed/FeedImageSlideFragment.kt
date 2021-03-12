package com.example.ssachackerthon.feed

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.ssachackerthon.R
import com.example.ssachackerthon.base.BaseFragment
import com.example.ssachackerthon.databinding.FeedImgSlideSrcBinding

class FeedImageSlideFragment(val image: String?): BaseFragment<FeedImgSlideSrcBinding>(FeedImgSlideSrcBinding::bind, R.layout.feed_img_slide_src) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(binding.feedVpImg).load(image).into(binding.feedVpImg)

    }
}