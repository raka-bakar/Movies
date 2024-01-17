package com.raka.movies.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raka.movies.databinding.ItemTagBinding

class TagsAdapter(private val listTags: List<String?>) :
    RecyclerView.Adapter<TagsAdapter.TagsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsHolder {
        val v = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagsHolder(v)
    }

    override fun getItemCount(): Int {
        return listTags.size
    }

    override fun onBindViewHolder(holder: TagsHolder, position: Int) {
        listTags[position]?.let { holder.bind(it) }
    }

    inner class TagsHolder(private val binding: ItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            binding.tvTag.text = tag
        }
    }
}