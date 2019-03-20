package com.kashanok.domain.repository.vehicle

import com.kashanok.domain.entity.vehicle.CoordinateParam
import com.kashanok.domain.entity.vehicle.Vehicle
import io.reactivex.Single

interface VehicleRepository {
    fun fetch(coords: Pair<CoordinateParam, CoordinateParam>) : Single<List<Vehicle>>
}