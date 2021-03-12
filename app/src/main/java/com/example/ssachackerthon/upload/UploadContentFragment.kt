package com.example.ssachackerthon.upload

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssachackerthon.MainActivity
import com.example.ssachackerthon.R
import com.example.ssachackerthon.base.BaseFragment
import com.example.ssachackerthon.databinding.FragmentUploadContentBinding
import com.example.ssachackerthon.upload.adapter.UploadContentImgRecyclerAdapter
import com.example.ssachackerthon.upload.adapter.UploadContentTagRecyclerAdapter
import com.example.ssachackerthon.upload.adapter.UploadGridRecyclerAdapter
import kotlin.collections.ArrayList

class UploadContentFragment:BaseFragment<FragmentUploadContentBinding>(FragmentUploadContentBinding::bind, R.layout.fragment_upload_content){
    companion object {
        const val imageListKey = "imageListKey"
    }

    private val TAG = "TAG"

    private var imageList = ArrayList<String>()
    val tagList = ArrayList<String>()
    lateinit var uploadTagRecyclerAdapter: UploadContentTagRecyclerAdapter
    lateinit var uploadImgRecyclerAdapter: UploadContentImgRecyclerAdapter


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageList = arguments?.get(imageListKey) as ArrayList<String>

        Log.d(TAG, "imageList: $imageList")

        binding.uploadBackArrow.setOnClickListener{
            (activity as MainActivity).onBackPressed()
        }

        setRecyclerAdapter()


    }


    fun setRecyclerAdapter() {
        // 태그 리사이클러 어댑터
        uploadTagRecyclerAdapter = UploadContentTagRecyclerAdapter(context, tagList)
        binding.uploadContentTagRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            // 메인 리사이클러 아이템클릭 리스터
            uploadTagRecyclerAdapter.let {
                it.setMyUploadTagItemClickListener(object :
                        UploadContentTagRecyclerAdapter.MyUploadTagItemClickListener {
                    override fun onItemClick(position: Int) {


                    }
                })

            }

            setHasFixedSize(true)
            adapter = uploadTagRecyclerAdapter
        }

        // 이미지 리사이클러 어댑터
        uploadImgRecyclerAdapter = UploadContentImgRecyclerAdapter(context, imageList)
        binding.uploadContentImgRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            // 메인 리사이클러 아이템클릭 리스터
            uploadImgRecyclerAdapter.let {
                it.setMyUploadImgItemClickListener(object :
                        UploadContentImgRecyclerAdapter.MyUploadImgItemClickListener {
                    override fun onItemClick(position: Int) {


                    }
                })

            }

            setHasFixedSize(true)
            adapter = uploadImgRecyclerAdapter
        }

    }


}