package com.zilvinas.newsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zilvinas.newsapp.R
import com.zilvinas.newsapp.viewmodel.ArticlesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
    }
}