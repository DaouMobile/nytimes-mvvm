package com.daou.timesapp.ui.clip

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.daou.timesapp.R
import com.daou.timesapp.databinding.FragmentClipBinding
import com.daou.timesapp.ui.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClipFragment: BaseFragment<FragmentClipBinding, ClipViewModel>(R.layout.fragment_clip) {
    override val viewModel: ClipViewModel by viewModel()
    private lateinit var clipAdapter: ClipListAdapter

    override fun setBindingVariables() {
        binding.clipViewModel = viewModel
    }

    override fun subscribeViewModel() {
        with(viewModel) {
            articleList.observe(viewLifecycleOwner) {
                clipAdapter.submitList(it)
            }
            clickSearchItem.observe(viewLifecycleOwner) {
                showLink(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getClippedArticles()
    }

    private fun initSearchAdapter() {
        clipAdapter = ClipListAdapter(viewModel)
        binding.rvArticleList.adapter = clipAdapter
    }

    private fun showLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(link)
        }
        startActivity(intent)
    }
}