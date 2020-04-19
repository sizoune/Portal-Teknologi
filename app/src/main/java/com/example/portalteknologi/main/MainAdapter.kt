package com.example.portalteknologi.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.portalteknologi.R
import com.example.portalteknologi.convertStringToEEEDDMMMYYYY
import com.example.portalteknologi.data.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.view.*

class MainAdapter(
    private val context: Context,
    private val dataNews: List<Article>,
    private val clickListener: (Article) -> Unit
) : RecyclerView.Adapter<MainAdapter.NewsHolder>() {

    class NewsHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItemtoView(
            data: Article,
            clickListener: (Article) -> Unit
        ) {
            containerView.tanggal.text = data.publishedAt
            containerView.title.text = data.title
            containerView.tanggal.text = data.publishedAt?.let { convertStringToEEEDDMMMYYYY(it) }
            Picasso.get().load(data.urlToImage).placeholder(R.drawable.image_12).fit()
                .into(containerView.image)
            containerView.setOnClickListener { clickListener(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            LayoutInflater.from(context).inflate(
                R.layout.news_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bindItemtoView(dataNews[position], clickListener)
    }
}