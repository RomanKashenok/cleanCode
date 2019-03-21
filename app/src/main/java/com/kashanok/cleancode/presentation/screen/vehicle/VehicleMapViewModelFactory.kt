package com.kashanok.cleancode.presentation.screen.vehicle

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kashanok.cleancode.app.UseCaseProvider

class VehicleMapViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VehicleMapViewModel(UseCaseProvider.provideGetVehicleuseCase()) as T
    }
}