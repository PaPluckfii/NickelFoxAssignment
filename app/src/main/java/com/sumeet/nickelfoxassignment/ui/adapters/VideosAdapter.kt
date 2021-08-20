package com.sumeet.nickelfoxassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.nickelfoxassignment.data.remote.model.Item
import com.sumeet.nickelfoxassignment.databinding.ItemVideoPreviewBinding

class VideosAdapter() : RecyclerView.Adapter<VideosAdapter.VideosViewHolder>() {

    inner class VideosViewHolder(private val binding: ItemVideoPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Item?) {
            binding.apply {
                Glide.with(this.root).load(item?.snippet?.thumbnails?.default)
                dummyText.text = item?.snippet?.title
                this.root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item!!)
                    }
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id?.videoId == oldItem.id?.videoId
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val binding = ItemVideoPreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Item) -> Unit)? = null

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }

}