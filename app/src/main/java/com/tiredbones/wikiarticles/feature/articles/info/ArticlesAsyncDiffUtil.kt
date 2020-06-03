package com.tiredbones.wikiarticles.feature.articles.info

import androidx.recyclerview.widget.DiffUtil
import com.tiredbones.wikiarticles.entities.ArticleDetails

class ArticlesAsyncDiffUtil : DiffUtil.ItemCallback<ArticleDetails>() {

  override fun areItemsTheSame(oldItem: ArticleDetails, newItem: ArticleDetails): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: ArticleDetails, newItem: ArticleDetails): Boolean = oldItem == newItem

}
