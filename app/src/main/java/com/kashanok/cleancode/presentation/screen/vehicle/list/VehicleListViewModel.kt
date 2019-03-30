package com.kashanok.cleancode.presentation.screen.vehicle.list

import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import com.kashanok.cleancode.app.VehicleApplication
import com.kashanok.cleancode.presentation.base.BaseViewModel
import com.kashanok.domain.entity.exception.AppException
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
            if(it is AppException) {
                performExceptionTypeAction(it)
            } else {

            }
            vehicleState.value = VehicleState.Error(it)
        })
        disposableBag.add(disposable)
    }

    private fun performExceptionTypeAction(it: AppException) {
        Toast.makeText(VehicleApplication.instance.applicationContext, "Exception occured: $it", Toast.LENGTH_LONG).show()
    }

    fun vehicleClick(vehicle: Vehicle) {
        vehicleClicked.value = vehicle
    }

}