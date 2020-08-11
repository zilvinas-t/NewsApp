package com.zilvinas.newsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zilvinas.newsapp.R
import com.zilvinas.newsapp.model.Article
import com.zilvinas.newsapp.util.getProgressDrawable
import com.zilvinas.newsapp.util.loadImage
import kotlinx.android.synthetic.main.article_item.view.*

class ArticlesListAdapter(
    private val articles: ArrayList<Article>,
    var onItemClick: ((Article) -> Unit)? = null): RecyclerView.Adapter<ArticlesListAdapter.ArticleViewHolder>() {

    fun updateArticles(newArticles: ArrayList<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
    )

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.article_image
        private val title = view.article_title
        private val date = view.article_date
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(article: Article) {
            title.text = article.title
            date.text = article.dateTime.toString()
            image.loadImage(article.image, progressDrawable)

            itemView.setOnClickListener {
                onItemClick?.invoke(article)
            }
        }
    }
}