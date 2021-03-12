package com.example.ssachackerthon.upload.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.ssachackerthon.R
import com.example.ssachackerthon.feed.FeedFragment
import com.example.ssachackerthon.feed.model.FeedRecyclerItems
import com.example.ssachackerthon.upload.model.UploadGridRecyclerItems

class UploadContentImgRecyclerAdapter(val context: Context?, var itemList: ArrayList<String>): RecyclerView.Adapter<UploadContentImgRecyclerAdapter.ItemViewHolder>() {

    interface MyUploadImgItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyUploadImgItemClickListener

    fun setMyUploadImgItemClickListener(itemClickListener: MyUploadImgItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val image: ImageView = itemView.findViewById(R.id.upload_content_recycler_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadContentImgRecyclerAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upload_content_img_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UploadContentImgRecyclerAdapter.ItemViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.image).load(items).into(holder.image)

    }

    override fun getItemCount(): Int = itemList.size

}