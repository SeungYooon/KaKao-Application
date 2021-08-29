package com.example.kakaoapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kakaoapplication.data.model.ImageResponse
import com.example.kakaoapplication.repository.DaumRepository
import com.example.kakaoapplication.util.BaseSchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KakaoViewModel @Inject constructor(
    private val daumRepository: DaumRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    private val _imageList = MutableLiveData<ImageResponse>()
    val imageList: LiveData<ImageResponse> get() = _imageList

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> get() = _isEmptyList

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    suspend fun fetchImages(query: String?, page: Int, size: Int) {
        viewModelScope.launch {
            daumRepository.loadImages(query, page, size).let { image ->
                if (image.documents.isNotEmpty()) {
                    _isEmptyList.postValue(false)
                    _imageList.postValue(image)
                }
                if (image.meta.total_count == 0) {
                    _isEmptyList.value = true
                }
            }
        }
    }

    /*fun fetchImages(query: String?, page: Int, size: Int) {
        addDisposable(
            daumRepository.loadImages(query, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ image ->
                    if (image.documents.isNotEmpty()) {
                        _isEmptyList.value = false
                        _imageList.value = image
                    }
                    if (image.meta.total_count == 0) {
                        _isEmptyList.value = true
                    }
                }, { e ->
                    _isError.value = true
                    e.printStackTrace()
                })
        )
    }*/

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}