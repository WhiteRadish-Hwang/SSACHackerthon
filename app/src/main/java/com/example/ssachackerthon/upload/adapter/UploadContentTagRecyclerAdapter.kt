package com.example.ssachackerthon.upload.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ssachackerthon.R
import com.example.ssachackerthon.feed.model.FeedRecyclerItems
import com.example.ssachackerthon.upload.model.UploadGridRecyclerItems

class UploadContentTagRecyclerAdapter(val context: Context?, var itemList: ArrayList<String>): RecyclerView.Adapter<UploadContentTagRecyclerAdapter.ItemViewHolder>() {

    interface MyUploadTagItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyUploadTagItemClickListener

    fun setMyUploadTagItemClickListener(itemClickListener: MyUploadTagItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val tag: TextView = itemView.findViewById(R.id.upload_content_recycler_tag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadContentTagRecyclerAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upload_content_tag_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UploadContentTagRecyclerAdapter.ItemViewHolder, position: Int) {
        val items = itemList[position]

        holder.tag.text = items

    }

    override fun getItemCount(): Int = itemList.size

}