package com.example.kakaoapplication.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kakaoapplication.data.model.ImageResponse
import com.example.kakaoapplication.data.service.DaumService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class KakaoViewModel @ViewModelInject constructor(private val service: DaumService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    private val _imageLiveData = MutableLiveData<ImageResponse>()
    val imageLiveData: LiveData<ImageResponse> get() = _imageLiveData

    private val _resultLiveData = MutableLiveData<Boolean>()
    val resultLiveData: LiveData<Boolean> get() = _resultLiveData

    fun fetchImages(query: String?, page: Int, size: Int) {
        addDisposable(
            service.loadImages(query, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ image ->
                    if (image.documents.isNotEmpty()) {
                        _resultLiveData.value = false
                        _imageLiveData.postValue(image)
                    }
                    if (image.meta.total_count == 0) {
                        _resultLiveData.value = true
                    }
                }, { e ->
                    _resultLiveData.value = true
                    Log.e(TAG, e.message.toString())
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        const val TAG = "KakaoViewModel"
    }
}