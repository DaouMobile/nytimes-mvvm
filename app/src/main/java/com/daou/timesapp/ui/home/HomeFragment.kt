package com.daou.timesapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.daou.timesapp.R
import com.daou.timesapp.databinding.FragmentHomeBinding
import com.daou.timesapp.ui.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModel()
    private lateinit var homeListAdapter: HomeListAdapter
    private lateinit var keywordListAdapter: KeywordListAdapter

    override fun setBindingVariables() {
        with(binding) {
            homeViewModel = viewModel
            etKeyword.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.onClickSearch()
                        binding.etKeyword.clearFocus()
                        return true
                    }
                    return false
                }
            })
            etKeyword.setOnFocusChangeListener { _, hasFocus ->
                viewModel.setSearchMode(hasFocus)
            }
        }
    }

    override fun subscribeViewModel() {
        with(viewModel) {
            searchList.observe(viewLifecycleOwner) {
                homeListAdapter.submitList(it)
            }
            keywordList.observe(viewLifecycleOwner) {
                keywordListAdapter.submitList(it)
            }
            clickSearchItem.observe(viewLifecycleOwner) {
                showLink(it)
            }
            clearEditFocus.observe(viewLifecycleOwner) {
                binding.etKeyword.clearFocus()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        homeListAdapter = HomeListAdapter(viewModel)
        keywordListAdapter = KeywordListAdapter(viewModel)
        binding.rvSearchList.adapter = homeListAdapter
        binding.rvKeywordList.adapter = keywordListAdapter
    }

    private fun showLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(link)
        }
        startActivity(intent)
    }
}