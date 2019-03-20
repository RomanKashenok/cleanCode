package com.kashanok.domain.usecase.vehicle

import com.kashanok.domain.entity.vehicle.CoordinateParam
import com.kashanok.domain.entity.vehicle.Vehicle
import com.kashanok.domain.repository.vehicle.VehicleRepository
import io.reactivex.Scheduler
import io.reactivex.Single

class VehicleGetUseCaseDefault(
    private val workScheduler: Scheduler,
    private val postScheduler: Scheduler,
    private val vehicleRepository: VehicleRepository
): VehicleGetUseCase {
    override fun get(): Single<List<Vehicle>> {
        val coordinates = Pair(CoordinateParam(2.5, 2.6), CoordinateParam(2.9, 2.3))

        return vehicleRepository.fetch(coordinates)
            .subscribeOn(workScheduler)
            .observeOn(postScheduler)
    }
}