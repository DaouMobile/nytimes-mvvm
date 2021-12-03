package com.daou.timesapp.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daou.timesapp.R
import com.daou.timesapp.databinding.ListItemHomeBinding
import com.daou.timesapp.ui.home.model.ArticleViewData

class HomeListAdapter(private val homeViewModel: HomeViewModel) :
    ListAdapter<ArticleViewData, HomeListAdapter.ArticleViewHolder>(Differ()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleViewHolder(
        ListItemHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        if (position == itemCount - 1) {
            homeViewModel.searchMore()
        }
        if (position < itemCount) {
            holder.bind(getItem(position))
        }
    }

    inner class ArticleViewHolder(private val binding: ListItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleViewData) {
            with(binding) {
                viewmodel = homeViewModel
                article = item
                if (item.thumbnailUrl.isEmpty()) {
                    Glide.with(binding.root)
                        .load(R.drawable.ic_launcher_foreground)
                        .override(400, 400)
                        .fitCenter()
                        .into(ivThumbnail)
                } else {
                    Glide.with(binding.root)
                        .load(Uri.parse(item.thumbnailUrl))
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .override(400, 400)
                        .fitCenter()
                        .into(ivThumbnail)
                }

            }
        }
    }
}

private class Differ : DiffUtil.ItemCallback<ArticleViewData>() {
    override fun areItemsTheSame(oldItem: ArticleViewData, newItem: ArticleViewData): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ArticleViewData, newItem: ArticleViewData): Boolean =
        oldItem == newItem

}