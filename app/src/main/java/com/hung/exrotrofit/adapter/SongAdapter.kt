package com.hung.exrotrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hung.exrotrofit.databinding.SongBinding
import com.hung.exrotrofit.model.Music
import com.hung.exrotrofit.onclick.ItemClick
import com.hung.exrotrofit.utils.Constants.BASE_URL

class SongAdapter(var onClick: ItemClick) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
    private var binding: SongBinding? = null


    private var differCallBack = object : DiffUtil.ItemCallback<Music>() {
        override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem.source == newItem.source
        }

        override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.ViewHolder {
        binding = SongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: SongAdapter.ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private var binding: SongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.apply {
                tvTitle.text = music.title
                tvAlbum.text = music.album
                Glide.with(ivItemImage).load(BASE_URL + music.image).into(ivItemImage)
                tvPlay.setOnClickListener {
                    onClick.onItemClick(adapterPosition)
                }
            }

        }
    }

}