package com.daou.timesapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daou.timesapp.databinding.ListItemKeywordBinding
import com.daou.timesapp.ui.common.StringDiffer

class KeywordListAdapter(private val homeViewModel: HomeViewModel) :
    ListAdapter<String, KeywordListAdapter.KeyWordViewHolder>(StringDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyWordViewHolder =
        KeyWordViewHolder(
            ListItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: KeyWordViewHolder, position: Int) {
        getItem(position)?.let { if (it.isNotBlank()) holder.bind(it) }
    }

    inner class KeyWordViewHolder(private val binding: ListItemKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.viewmodel = homeViewModel
            binding.keyword = item
        }
    }
}