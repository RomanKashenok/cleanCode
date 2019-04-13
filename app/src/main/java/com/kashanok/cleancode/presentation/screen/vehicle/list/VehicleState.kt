package com.kashanok.cleancode.presentation.screen.vehicle.list

import com.kashanok.domain.entity.vehicle.Vehicle

sealed class VehicleState {

    object Progress : VehicleState()
    class Done(val vehicleList: List<Vehicle>) : VehicleState()
    class Error(val throwable: Throwable) : VehicleState()

}