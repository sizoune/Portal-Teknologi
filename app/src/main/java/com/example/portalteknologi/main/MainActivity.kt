package com.example.portalteknologi.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portalteknologi.R
import com.example.portalteknologi.about.AboutActivity
import com.example.portalteknologi.data.Apifactory
import com.example.portalteknologi.data.model.Article
import com.example.portalteknologi.detail.DetailNews
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val newsService = Apifactory.newsApi
    private var dataArticle: List<Article>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Indonesia Tech News"
        recyclerView.layoutManager = LinearLayoutManager(this)
        println(savedInstanceState == null)
        if (savedInstanceState != null) {
            println("ada data saveinstance")
            dataArticle = savedInstanceState.getParcelableArrayList("article")
            if (dataArticle != null) {
                println("ada data artikel")
                setAdapterData(dataArticle as java.util.ArrayList<Article>)
            }
        } else {
            init()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_about) {
            startActivity(Intent(this, AboutActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


    private fun init() {
        getData()
        swipe_refresh.setOnRefreshListener {
            dataArticle = null
            getData()
        }
        bt_retry.setOnClickListener {
            showFailedLayout(false)
            getData()
        }
    }

    private fun getData() {
        if (dataArticle.isNullOrEmpty()) {
            swipe_refresh.isRefreshing = true
            GlobalScope.launch(Dispatchers.Main) {
                val newsTechRequest = newsService.getIndonesianTechNews("id", "technology")
                try {
                    val response = newsTechRequest.await()
                    if (response.isSuccessful) {
                        val newsResponse = response.body()
                        val topTechNews = newsResponse?.articles
                        println(topTechNews?.size)
                        swipeProgress(false)
                        if (!topTechNews.isNullOrEmpty()) {
                            dataArticle = topTechNews
                            setAdapterData(topTechNews)
                        }
                    } else {
                        showFailedLayout(true)
                        swipeProgress(false)
                        println("Respon gagal")
                        Log.e("ResonponseFailed", response.errorBody().toString())
                    }
                } catch (e: Exception) {
                    swipeProgress(false)
                    showFailedLayout(true)
                    Log.e("ResponseException", e.localizedMessage)
                }
            }
        }

    }

    private fun swipeProgress(show: Boolean) {
        if (!show) {
            swipe_refresh.isRefreshing = show
            return
        }
        swipe_refresh.post { swipe_refresh.isRefreshing = show }
    }

    private fun setAdapterData(listArticle: List<Article>) {
        recyclerView.adapter =
            MainAdapter(
                this@MainActivity,
                listArticle
            ) {
                val intent = Intent(this@MainActivity, DetailNews::class.java)
                intent.putExtra("article", it)
                startActivity(intent)
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (dataArticle != null) {
            outState.putParcelableArrayList("article", ArrayList(dataArticle!!))
        }
        super.onSaveInstanceState(outState)
    }

    private fun showFailedLayout(show: Boolean) {
        if (show) {
            layoutFailed.visibility = View.VISIBLE
            swipe_refresh.visibility = View.GONE
        } else {
            layoutFailed.visibility = View.GONE
            swipe_refresh.visibility = View.VISIBLE
        }
    }
}
