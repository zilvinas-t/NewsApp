package com.zilvinas.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zilvinas.newsapp.model.ApiData
import io.reactivex.disposables.CompositeDisposable

class ArticlesViewModel: ViewModel() {

    val apiData = MutableLiveData<ApiData>()
    val loading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    fun refresh() {
        fetchArticles()
    }

    private fun fetchArticles() {
        //TODO: articles retrieving logic here
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}