package com.kashanok.cleancode.presentation.base

import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {
    private val lazyDisposable = lazy (LazyThreadSafetyMode.NONE) {
        CompositeDisposable()
    }

    protected val disposableBag by lazyDisposable

    override fun onCleared() {
        super.onCleared()
        Log.d(this::class.java.toString(), "OnCleared()")
        if(lazyDisposable.isInitialized()) {
            disposableBag.dispose()
        }
    }
}
