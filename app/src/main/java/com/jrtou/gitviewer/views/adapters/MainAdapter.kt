package com.jrtou.gitviewer.views.adapters

import com.jrtou.gitviewer.api.GitHubData
import com.jrtou.gitviewer.databinding.ItemRvTrendRepoBinding

class MainAdapter : BaseBindingAdapter<GitHubData.TrendItem, ItemRvTrendRepoBinding>() {
    companion object {
        private const val TAG = "MainAdapter"
    }

    override fun onBindHolder(binding: ItemRvTrendRepoBinding, item: GitHubData.TrendItem) {
        binding.trend = item
//        binding.executePendingBindings()
    }
}