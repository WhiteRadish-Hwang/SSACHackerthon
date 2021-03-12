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

class UploadGridRecyclerAdapter(val context: Context?, var itemList: ArrayList<String>): RecyclerView.Adapter<UploadGridRecyclerAdapter.ItemViewHolder>() {

    interface MyUploadItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyUploadItemClickListener

    fun setMyUploadItemClickListener(itemClickListener: MyUploadItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val image: ImageView = itemView.findViewById(R.id.upload_grid_recycler_img)
        val frame: FrameLayout = itemView.findViewById(R.id.upload_grid_recycler_transparent_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadGridRecyclerAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upload_grid_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UploadGridRecyclerAdapter.ItemViewHolder, position: Int) {
        val items = itemList[position]
        Glide.with(holder.image).load(items).into(holder.image)

//        holder.itemView.setOnClickListener {
//            holder.itemView.isSelected = !holder.itemView.isSelected
//            when (holder.itemView.isSelected) {
//                true -> holder.frame.visibility = View.VISIBLE
//                false -> holder.frame.visibility = View.GONE
//            }
//        }

    }

    override fun getItemCount(): Int = itemList.size

}