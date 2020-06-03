package com.tiredbones.wikiarticles.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.placeholderOf
import com.tiredbones.wikiarticles.BuildConfig
import com.tiredbones.wikiarticles.R

@BindingAdapter("image")
fun ImageView.setImage(imageUrl: String?) {
  setVisibility(imageUrl != null)
  Glide.with(this)
      .load(imageUrl)
      .apply(placeholderOf(R.drawable.image_placeholder))
      .into(this)
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean) {
  visibility = if (isVisible) View.VISIBLE else View.GONE
}
