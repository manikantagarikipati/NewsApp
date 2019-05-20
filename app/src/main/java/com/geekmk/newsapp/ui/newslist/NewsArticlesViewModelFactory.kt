package com.geekmk.newsapp.ui.newslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


class NewsArticlesViewModelFactory @Inject constructor(
    private val newsArticlesViewModel: NewsArticlesViewModel) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(NewsArticlesViewModel::class.java)) {
      return newsArticlesViewModel as T
    }
    throw IllegalArgumentException("Unknown class name")
  }
}