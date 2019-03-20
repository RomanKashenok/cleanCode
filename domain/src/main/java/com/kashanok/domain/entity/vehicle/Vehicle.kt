package com.kashanok.domain.entity.vehicle

import com.kashanok.domain.entity.DomainEntity

data class Vehicle(val id: Int, val coordinateParam: CoordinateParam, val fleetType: FleetType, val heading: Double) :
    DomainEntity