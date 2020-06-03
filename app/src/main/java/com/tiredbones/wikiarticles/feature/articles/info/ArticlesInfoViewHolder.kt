package com.tiredbones.wikiarticles.feature.articles.info

import androidx.recyclerview.widget.RecyclerView
import com.tiredbones.wikiarticles.databinding.ItemArticleInfoBinding
import com.tiredbones.wikiarticles.entities.ArticleDetails

class ArticlesInfoViewHolder(
    private val binding: ItemArticleInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(item: ArticleDetails?) {
    binding.item = item
  }
}
