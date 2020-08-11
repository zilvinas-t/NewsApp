package com.zilvinas.newsapp.di

import com.zilvinas.newsapp.viewmodel.ArticlesViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(viewModel: ArticlesViewModel)
}