package com.kashanok.cleancode.presentation.screen.vehicle.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kashanok.cleancode.app.UseCaseProvider

class VehicleListViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VehicleListViewModel(UseCaseProvider
            .provideGetVehicleuseCase()) as T
    }
}