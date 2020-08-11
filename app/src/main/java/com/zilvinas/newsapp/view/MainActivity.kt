package com.zilvinas.newsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.zilvinas.newsapp.R
import com.zilvinas.newsapp.viewmodel.ArticlesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticlesViewModel
    private val articlesAdapter = ArticlesListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
        viewModel.refresh()

        swipeRefresh_layout.setOnRefreshListener {
            articles_recycler_view.visibility = View.GONE
            articles_loading_bar.visibility = View.VISIBLE
            swipeRefresh_layout.isRefreshing = false
            viewModel.refresh()
        }

        articles_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articlesAdapter
        }

        articlesAdapter.onItemClick = { article ->
            //TODO: implement article details activity preview here
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        load_error_text.visibility = View.GONE
        viewModel.apiData.observe(this, Observer { apiData ->
            apiData?.let {
                articles_recycler_view.visibility = View.VISIBLE
                articles_loading_bar.visibility = View.GONE
                articlesAdapter.updateArticles(it.articles!!)
            }
        })

        viewModel.isError.observe(this, Observer { isError ->
            isError?.let {
                if(it) {
                    load_error_text.visibility = View.VISIBLE
                    articles_recycler_view.visibility = View.GONE
                    articles_loading_bar.visibility = View.GONE
                } else
                    load_error_text.visibility = View.GONE
            }
        })
    }
}