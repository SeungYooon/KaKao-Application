package com.example.kakaoapplication.ui.kakao

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaoapplication.R
import com.example.kakaoapplication.data.model.Document
import com.example.kakaoapplication.databinding.ActivityKakaoBinding
import com.example.kakaoapplication.ui.detail.DetailActivity.Companion.startActivityWithTransition
import com.example.kakaoapplication.viewmodel.KakaoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_kakao.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class KakaoActivity : AppCompatActivity(), KakaoAdapter.OnClickListener {
    private lateinit var binding: ActivityKakaoBinding
    private lateinit var adapter: KakaoAdapter
    private var queryInput = ""
    private val viewModel: KakaoViewModel by viewModels()
    private var page = 1
    private val list = ArrayList<Document>()
    private val snackBar: Snackbar by lazy { Snackbar.make(view, R.string.loading_message, Snackbar.LENGTH_SHORT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showSearchText()
        setupSearchView()
        setAdapter()
        setScrollListener()
        observeImage()
        observeResult()
    }

    @SuppressLint("CheckResult")
    private fun setupSearchView() {
        binding.searchView.setQuery("", false)

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { subscriber.onNext(it) }
                    binding.searchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    hideSearchText()
                    hideEmptyResult()
                    hideRecyclerView()

                    if (newText.isNullOrEmpty()) {
                        hideProgressBar()
                        showSearchText()
                    } else {
                        showProgressBar()
                        queryInput = newText
                        subscriber.onNext(queryInput)
                    }
                    return false
                }
            })
        })
            .debounce(1000, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { query -> query.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { query ->
                hideProgressBar()
                updateList()
                page = 1
                fetchImage(query, page)
            }
    }

    private fun setAdapter() {
        adapter = KakaoAdapter(list, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun setScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!binding.recyclerView.canScrollVertically(1)) {
                    showSnackBar()
                    page++
                    fetchImage(queryInput, page)

                    Completable.complete()
                        .delay(2000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            hideSnackBar()
                            loadMorePage(adapter)
                        }
                        .subscribe()
                }
            }
        })
    }

    private fun loadMorePage(adapter: RecyclerView.Adapter<KakaoAdapter.KaKaoViewHolder>) {
        adapter.notifyItemRangeInserted(page * 30, 30)
    }

    private fun observeImage() {
        viewModel.imageLiveData.observe(this, { response ->
            list.addAll(response.documents)
        })
    }

    private fun observeResult() {
        viewModel.resultLiveData.observe(this, { flag ->
            when (flag) {
                true -> {
                    showEmptyResult()
                    hideRecyclerView()
                }
                false -> {
                    hideEmptyResult()
                    showRecyclerView()
                }
            }
        })
    }

    private fun updateList() {
        list.clear()
        adapter.notifyDataSetChanged()
    }

    private fun fetchImage(query: String, page: Int) {
        viewModel.fetchImages(query, page, 30)
    }

    private fun showRecyclerView() {
        binding.recyclerView.isVisible = true
    }

    private fun hideRecyclerView() {
        binding.recyclerView.isVisible = false
    }

    private fun showSearchText() {
        binding.textStartSearch.isVisible = true
    }

    private fun hideSearchText() {
        binding.textStartSearch.isVisible = false
    }

    private fun showEmptyResult() {
        binding.textNoResult.isVisible = true
    }

    private fun hideEmptyResult() {
        binding.textNoResult.isVisible = false
    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }

    private fun showSnackBar() {
        snackBar.show()
    }

    private fun hideSnackBar() {
        snackBar.dismiss()
    }

    override fun onClick(imageView: ImageView, document: Document) {
        startActivityWithTransition(this@KakaoActivity, imageView, document)
    }
}