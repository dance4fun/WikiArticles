package com.tiredbones.wikiarticles.extensions

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tiredbones.wikiarticles.base.BaseViewModel


inline fun <reified T : BaseViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T =
    ViewModelProviders.of(this, viewModelFactory).get(T::class.java)

fun FragmentActivity.openUrl(url: String) {
  val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
  startActivity(browserIntent)
}
