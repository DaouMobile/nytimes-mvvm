package com.daou.timesapp.ui.clip

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daou.timesapp.R
import com.daou.timesapp.databinding.ListItemClipBinding
import com.daou.timesapp.ui.clip.model.ClipArticleViewData

class ClipListAdapter(private val clipViewModel: ClipViewModel) :
    ListAdapter<ClipArticleViewData, ClipListAdapter.ArticleViewHolder>(Differ()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleViewHolder(
        ListItemClipBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ArticleViewHolder(private val binding: ListItemClipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ClipArticleViewData) {
            with(binding) {
                viewmodel = clipViewModel
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

private class Differ : DiffUtil.ItemCallback<ClipArticleViewData>() {
    override fun areItemsTheSame(
        oldItem: ClipArticleViewData,
        newItem: ClipArticleViewData
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ClipArticleViewData,
        newItem: ClipArticleViewData
    ): Boolean =
        oldItem == newItem

}