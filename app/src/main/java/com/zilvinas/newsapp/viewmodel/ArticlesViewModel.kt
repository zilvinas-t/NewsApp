package com.zilvinas.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zilvinas.newsapp.di.DaggerApiComponent
import com.zilvinas.newsapp.model.ApiData
import com.zilvinas.newsapp.model.ArticlesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticlesViewModel: ViewModel() {

    @Inject
    lateinit var articlesApi: ArticlesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    val apiData = MutableLiveData<ApiData>()
    val loading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    fun refresh() {
        fetchArticles()
    }

    private fun fetchArticles() {
        loading.value = true
        disposable.add(articlesApi.getArticles()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<ApiData>() {
                override fun onSuccess(response: ApiData) {
                    loading.value = false
                    isError.value = false
                    apiData.value = response
                }

                override fun onError(e: Throwable) {
                    loading.value = false
                    isError.value = true
                }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}