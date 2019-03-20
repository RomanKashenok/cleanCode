package com.kashanok.domain.usecase.vehicle

import com.kashanok.domain.entity.vehicle.Vehicle
import io.reactivex.Single

interface VehicleGetUseCase {
    fun get(): Single<List<Vehicle>>
}