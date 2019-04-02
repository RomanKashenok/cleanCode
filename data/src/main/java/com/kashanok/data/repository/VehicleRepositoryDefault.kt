package com.kashanok.data.repository

import com.kashanok.data.rest.VehicleService
import com.kashanok.domain.entity.exception.AppException
import com.kashanok.domain.entity.exception.AppExceptionType
import com.kashanok.domain.entity.vehicle.CoordinateParam
import com.kashanok.domain.entity.vehicle.Vehicle
import com.kashanok.domain.repository.vehicle.VehicleRepository
import io.reactivex.Single
import java.net.SocketTimeoutException

class VehicleRepositoryDefault(private val url: String, private val service: VehicleService) : VehicleRepository {

    override fun fetch(coords: Pair<CoordinateParam, CoordinateParam>): Single<List<Vehicle>> {

        return service.getVehicles(coords, url).onErrorResumeNext {
            when (it) {
                is SocketTimeoutException -> {
                    Single.error(AppException(AppExceptionType.NO_INTERNET))
                }
                else -> {
                    Single.error(AppException(AppExceptionType.UNKNOWN))
                }
            }
        }
    }
}