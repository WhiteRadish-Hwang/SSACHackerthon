package com.example.ssachackerthon.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssachackerthon.R
import com.example.ssachackerthon.home.model.HomeRecyclerItems

class HomeRecyclerAdapter(val context: Context?, var itemList: ArrayList<HomeRecyclerItems>): RecyclerView.Adapter<HomeRecyclerAdapter.ItemViewHolder>() {

    interface MyHomeItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyHomeItemClickListener

    fun setMyHomeItemClickListener(itemClickListener: MyHomeItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val date: TextView = itemView.findViewById(R.id.home_recycler_text_date)
        val content: TextView = itemView.findViewById(R.id.home_recycler_text_content)
        val image: ImageView = itemView.findViewById(R.id.home_recycler_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeRecyclerAdapter.ItemViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.image).load(items.img).placeholder(R.drawable.cal_thumbs_up).into(holder.image)

        holder.date.text = items.date
        holder.content.text = items.content
    }

    override fun getItemCount(): Int = itemList.size

}