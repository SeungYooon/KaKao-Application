package com.example.kakaoapplication.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.kakaoapplication.data.model.Document
import com.example.kakaoapplication.databinding.ActivityDetailBinding
import com.example.kakaoapplication.util.GlideApp

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val document by lazy { intent.getParcelableExtra(KEY) as Document? }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetailImage()
        setBackButton()
    }

    private fun setBackButton() {
        binding.imageBack.setOnClickListener { onBackPressed() }
    }

    private fun setDetailImage() {
        GlideApp.with(this)
            .load(document?.image_url)
            .override(document!!.width, document!!.height)
            .into(binding.imageDetail)
    }

    companion object {
        private const val KEY = "image"

        fun startActivityWithTransition(
            activity: Activity,
            imageView: ImageView,
            document: Document
        ) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(KEY, document)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, imageView, imageView.transitionName
            )
            activity.startActivity(intent, options.toBundle())
        }
    }
}