package com.tiredbones.wikiarticles.feature.articles.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.tiredbones.wikiarticles.R
import com.tiredbones.wikiarticles.entities.ArticleDetails

class ArticlesInfoAdapter : RecyclerView.Adapter<ArticlesInfoViewHolder>() {

  private val differ = AsyncListDiffer(this, ArticlesAsyncDiffUtil())

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesInfoViewHolder =
      ArticlesInfoViewHolder(
          DataBindingUtil.inflate(
              LayoutInflater.from(parent.context),
              R.layout.item_article_info,
              parent,
              false
          )
      )

  override fun onBindViewHolder(holder: ArticlesInfoViewHolder, position: Int) {
    holder.bind(differ.currentList[position])
  }

  override fun getItemCount() = differ.currentList.size

  fun updateList(newList: List<ArticleDetails>) {
    differ.submitList(newList)
  }
}
