package com.example.portalteknologi.about

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.portalteknologi.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "About"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Picasso.get()
            .load(R.drawable.my_pic)
            .placeholder(R.drawable.image_12).fit().into(profile_image)

        btnDicoding.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.dicoding.com/users/155021")
            startActivity(intent)
        }

        btnGitHub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/sizoune")
            startActivity(intent)
        }

        btnLinkedIN.setOnClickListener {
            val linkedId = "mwildani"
            var intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@$linkedId"))
            val packageManager: PackageManager = this.packageManager
            val list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            if (list.isEmpty()) {
                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.linkedin.com/profile/view?id=$linkedId")
                )
            }
            startActivity(intent)
        }

        btnWhatsApp.setOnClickListener {
            val number = "+6285393416312"
            val url = "https://api.whatsapp.com/send?phone=$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }
}
