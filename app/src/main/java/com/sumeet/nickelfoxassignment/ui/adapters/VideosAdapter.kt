package com.sumeet.nickelfoxassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.nickelfoxassignment.data.remote.model.Item
import com.sumeet.nickelfoxassignment.databinding.ItemVideoPreviewBinding
import com.sumeet.nickelfoxassignment.util.OnItemClickListener

class VideosAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<VideosAdapter.VideosViewHolder>() {

    inner class VideosViewHolder(private val binding: ItemVideoPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Item?) {
            binding.apply {
                tvTitle.text = item?.snippet?.title
                tvChannel.text = item?.snippet?.channelTitle
            }
            Glide.with(binding.root).load(item?.snippet?.thumbnails?.high?.url).into(binding.ivThumbnail)
            Glide.with(binding.root).load(item?.snippet?.thumbnails?.default?.url).into(binding.ivChannelPic)
            binding.root.setOnClickListener{
                item?.id?.videoId?.let { it1 -> onItemClickListener.onItemClicked(it1) }
            }
        }
    }

    /**
     * Diff Util Callback to compare old and new list
     */
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

}