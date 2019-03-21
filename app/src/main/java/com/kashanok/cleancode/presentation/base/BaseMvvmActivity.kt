package com.kashanok.cleancode.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

abstract class BaseMvvmActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: VM
    protected abstract fun provideViewModel(): VM

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        viewModel = provideViewModel()
        setContentView(provideLayoutId())
    }
}