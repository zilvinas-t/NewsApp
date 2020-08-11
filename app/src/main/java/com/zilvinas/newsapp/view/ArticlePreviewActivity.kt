package com.zilvinas.newsapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.gson.Gson
import com.zilvinas.newsapp.R
import com.zilvinas.newsapp.model.Article
import com.zilvinas.newsapp.util.getProgressDrawable
import com.zilvinas.newsapp.util.loadImage
import kotlinx.android.synthetic.main.activity_article_preview.*
import java.lang.Exception

class ArticlePreviewActivity : AppCompatActivity() {

    private lateinit var article: Article
    private lateinit var progressDrawable: CircularProgressDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_preview)

        setToolbar()

        if(getArticleDetails()) {
            setArticleContent()
            loadArticleUrl()
        }
    }

    private fun setToolbar() {
        article_details_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        article_details_toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun setArticleContent() {
        article_details_title.text = article.title
        article_details_description.text = article.description
        article_details_author.text = article.author
        article_details_date.text = article.dateTime.toString()

        progressDrawable = getProgressDrawable(this)
        article_details_image.loadImage(article.image, progressDrawable)
    }

    private fun getArticleDetails(): Boolean {
        try {
            val encodedArticle = intent.getStringExtra(getString(R.string.article_json))
            article = Gson().fromJson(encodedArticle, Article::class.java)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun loadArticleUrl() {
        url_button.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
        }
    }
}