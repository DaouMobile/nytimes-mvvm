package com.daou.timesapp.ui.common

import androidx.recyclerview.widget.DiffUtil

class StringDiffer : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

}