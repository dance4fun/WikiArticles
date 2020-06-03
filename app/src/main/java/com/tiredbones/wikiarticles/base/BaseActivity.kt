package com.tiredbones.wikiarticles.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tiredbones.wikiarticles.WikiApplication
import com.tiredbones.wikiarticles.R
import com.tiredbones.wikiarticles.extensions.subscribe
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    WikiApplication.applicationComponent.inject(this)
    super.onCreate(savedInstanceState)
  }

  protected fun showError(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
  }
}
