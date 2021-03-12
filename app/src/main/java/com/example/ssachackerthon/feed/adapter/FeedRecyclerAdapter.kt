package com.example.ssachackerthon.feed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ssachackerthon.R
import com.example.ssachackerthon.feed.FeedFragment
import com.example.ssachackerthon.feed.model.FeedRecyclerItems

class FeedRecyclerAdapter(val context: Context?, var itemList: ArrayList<FeedRecyclerItems>): RecyclerView.Adapter<FeedRecyclerAdapter.ItemViewHolder>() {

    interface MyFeedItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyFeedItemClickListener

    fun setMyFeedItemClickListener(itemClickListener: MyFeedItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val vp: ViewPager2 = itemView.findViewById(R.id.feed_recycler_vp)
        val vp_indicator1: ImageView = itemView.findViewById(R.id.feed_recycler_indicator_1)
        val vp_indicator2: ImageView = itemView.findViewById(R.id.feed_recycler_indicator_2)
        val vp_indicator3: ImageView = itemView.findViewById(R.id.feed_recycler_indicator_3)
        val dateAgo: TextView = itemView.findViewById(R.id.feed_recycler_text_date_ago)
        val content: TextView = itemView.findViewById(R.id.feed_recycler_text_content)
        val replCount: TextView = itemView.findViewById(R.id.feed_recycler_text_repl_count)
        val tagEdit: EditText = itemView.findViewWithTag(R.id.feed_recycler_edit_tag)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedRecyclerAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    var hashTagList = ArrayList<String>()
    var start = 0
    var end = 0

    override fun onBindViewHolder(holder: FeedRecyclerAdapter.ItemViewHolder, position: Int) {
        val items = itemList[position]

        var hashTag = ""

        holder.dateAgo.text = items.dateAgo
        holder.content.text = items.content
        holder.replCount.text = items.replCount

        hashTag = holder.tagEdit.text.toString()
        tagDivider(hashTag)

        val pagerAdapter = FeedImageSlidePagerAdapter(FeedFragment.getInstance(), hashTagList)
        holder.vp.adapter = pagerAdapter

        holder.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                holder.vp_indicator1.setImageResource(R.drawable.indicator_circle)
                holder.vp_indicator1.setImageResource(R.drawable.indicator_circle)
                holder.vp_indicator1.setImageResource(R.drawable.indicator_circle)

                when (position) {
                    0 -> holder.vp_indicator1.setImageResource(R.drawable.indicator_circle_current)
                    1 -> holder.vp_indicator2.setImageResource(R.drawable.indicator_circle_current)
                    2 -> holder.vp_indicator3.setImageResource(R.drawable.indicator_circle_current)
                }

            }
        })

    }

    fun tagDivider(tagString: String) {
        var pivot = 0
        var tag = ""

        for (i in tagString.indices) {
            if ('#' == tagString[i]) {
                start = i
            } else if (i != 0 && '#' == tagString[i]) {
                end = i - 1
            }

            if (pivot < end) {
                pivot = end
                for (i in start..end) {
                    tag += tagString[i]
                }
                hashTagList.add(tag)
                tag = ""
            }
        }

    }

    override fun getItemCount(): Int = itemList.size

}