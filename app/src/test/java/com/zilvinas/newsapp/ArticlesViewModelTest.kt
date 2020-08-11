package com.zilvinas.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zilvinas.newsapp.model.ApiData
import com.zilvinas.newsapp.model.Article
import com.zilvinas.newsapp.model.ArticlesApi
import com.zilvinas.newsapp.viewmodel.ArticlesViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ArticlesViewModelTest {

    @get: Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var articlesApi: ArticlesApi

    @InjectMocks
    var articlesViewModel = ArticlesViewModel()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    private var testSingle: Single<ApiData>? = null

    @Test
    fun getApiDataSuccess() {
        val articles = arrayListOf(getArticle())
        val apiData = ApiData("ok", 38, articles)

        testSingle = Single.just(apiData)

        Mockito.`when`(articlesApi.getArticles()).thenReturn(testSingle)

        articlesViewModel.refresh()

        Assert.assertEquals("ok", articlesViewModel.apiData.value?.status)
        Assert.assertEquals(38, articlesViewModel.apiData.value?.totalResults)
        Assert.assertEquals(1, articlesViewModel.apiData.value?.articles?.size)
        Assert.assertEquals(false, articlesViewModel.isError.value)
        Assert.assertEquals(false, articlesViewModel.loading.value)
    }

    @Test
    fun getApiDataFailure() {
        testSingle = Single.error(Throwable())

        Mockito.`when`(articlesApi.getArticles()).thenReturn(testSingle)

        articlesViewModel.refresh()

        Assert.assertEquals(null, articlesViewModel.apiData.value?.status)
        Assert.assertEquals(null, articlesViewModel.apiData.value?.totalResults)
        Assert.assertEquals(null, articlesViewModel.apiData.value?.articles)

        Assert.assertEquals(true, articlesViewModel.isError.value)
        Assert.assertEquals(false, articlesViewModel.loading.value)
    }

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate}
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate}
    }

    private fun getArticle(): Article {
        return Article(
            "image",
            "title",
            "description",
            Calendar.getInstance().time,
            "author",
            "url"
        )
    }
}