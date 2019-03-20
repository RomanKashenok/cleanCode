package com.kashanok.cleancodeproject.data.entity

import com.kashanok.domain.entity.vehicle.CoordinateParam
import com.kashanok.domain.entity.vehicle.FleetType
import com.kashanok.domain.entity.vehicle.Vehicle

internal fun VehiclePoiResponse.responseToEntity() =
    Vehicle(id, coordinate.responseToEntity(), FleetType.fromString(fleetType), heading)


internal fun CoordinateResponse.responseToEntity() = CoordinateParam(latitude, longitude)