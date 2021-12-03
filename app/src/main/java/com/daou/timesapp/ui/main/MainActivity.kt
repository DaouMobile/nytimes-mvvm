package com.daou.timesapp.ui.main

import androidx.fragment.app.Fragment
import com.daou.timesapp.R
import com.daou.timesapp.databinding.ActivityMainBinding
import com.daou.timesapp.ui.clip.ClipFragment
import com.daou.timesapp.ui.common.BaseActivity
import com.daou.timesapp.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel: MainViewModel by viewModel()
    private val fragmentList: List<Fragment> by lazy {
        listOf(
            HomeFragment(),
            ClipFragment()
        )
    }

    override fun onCreate(binding: ActivityMainBinding) {
        with(binding) {
            viewPager.adapter = MainViewPagerAdapter(this@MainActivity, fragmentList)
            viewPager.offscreenPageLimit = 3
            viewPager.setCurrentItem(0, false)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.label_home)
                    1 -> tab.text = getString(R.string.label_clip)
                }
            }.attach()
        }
    }

    override fun setBindingVariables(binding: ActivityMainBinding) {}

    override fun subscribeViewModel(viewModel: MainViewModel) {
        with(viewModel) {

        }
    }
}