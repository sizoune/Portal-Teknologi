package com.example.portalteknologi.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.Annotation
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.example.portalteknologi.R
import com.example.portalteknologi.convertStringToEEEDDMMMYYYY
import com.example.portalteknologi.data.model.Article
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_news.*

class DetailNews : AppCompatActivity() {
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        article = intent.getParcelableExtra("article")
        println(article)
        txtTitle.text = article.title
        if (article.author != null && !article.author.equals(""))
            txtAuthor.text = article.author
        else
            txtAuthor.text = article.source.name
        txtDate.text = article.publishedAt?.let { convertStringToEEEDDMMMYYYY(it) }
        txtDesc.text = article.description?.toSpanned()
        Picasso.get().load(article.urlToImage).placeholder(R.drawable.image_12).fit()
            .into(detailImage)
        initTExtViewAsLink()

    }

    private fun initTExtViewAsLink() {
        val fullText = getText(R.string.baca_artikel) as SpannedString
        val spannableString = SpannableString(fullText)

        val annotations = fullText.getSpans(0, fullText.length, Annotation::class.java)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(article.url)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

        }
        annotations?.find { it.value == "artikel_link" }?.let {
            spannableString.apply {
                setSpan(
                    clickableSpan,
                    fullText.getSpanStart(it),
                    fullText.getSpanEnd(it),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(this@DetailNews, R.color.colorPrimary)
                    ),
                    fullText.getSpanStart(it),
                    fullText.getSpanEnd(it),
                    0
                )
            }
        }

        txtLink.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    fun String.toSpanned(): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(this)
        }
    }
}
