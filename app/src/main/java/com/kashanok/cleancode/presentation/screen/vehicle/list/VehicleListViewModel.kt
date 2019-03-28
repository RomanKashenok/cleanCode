package com.kashanok.cleancode.presentation.screen.vehicle.list

import android.arch.lifecycle.MutableLiveData
import com.kashanok.cleancode.presentation.base.BaseViewModel
import com.kashanok.domain.entity.vehicle.Vehicle
import com.kashanok.domain.usecase.vehicle.VehicleGetUseCase
import io.reactivex.subjects.BehaviorSubject

class VehicleListViewModel(vehiclesListUseCase: VehicleGetUseCase) : BaseViewModel() {

//    val vehicleState = BehaviorSubject.create<VehicleState>()
    val vehicleState = MutableLiveData<VehicleState>()

    val vehicleClicked = MutableLiveData<Vehicle>()

//    init {
//        vehicleState.onNext(VehicleState.Progress)
//        val disposable = vehiclesListUseCase.get().subscribe({
//            vehicleState.onNext(VehicleState.Done(it))
//        }, {
//            vehicleState.onNext(VehicleState.Error(it))
//        })
//        disposableBag.add(disposable)
//    }

    init {
        vehicleState.value = VehicleState.Progress
        val disposable = vehiclesListUseCase.get().subscribe({
            vehicleState.value = VehicleState.Done(it)
        }, {
            vehicleState.value = VehicleState.Error(it)
        })
        disposableBag.add(disposable)
    }

    fun vehicleClick(vehicle: Vehicle) {
        vehicleClicked.value = vehicle
    }

}