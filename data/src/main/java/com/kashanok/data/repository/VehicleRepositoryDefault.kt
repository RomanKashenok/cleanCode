package com.kashanok.data.repository

import android.content.Context
import com.kashanok.cleancodeproject.data.entity.responseToEntity
import com.kashanok.data.db.AppDatabase
import com.kashanok.data.rest.VehicleService
import com.kashanok.domain.entity.exception.AppException
import com.kashanok.domain.entity.exception.AppExceptionType
import com.kashanok.domain.entity.vehicle.CoordinateParam
import com.kashanok.domain.entity.vehicle.Vehicle
import com.kashanok.domain.repository.vehicle.VehicleRepository
import io.reactivex.Single
import java.net.SocketTimeoutException

class VehicleRepositoryDefault(
    private val url: String,
    private val service: VehicleService,
    private val context: Context
) : VehicleRepository {

    private val vehicleDao = AppDatabase.create(context).getVehicleDao()

    override fun fetch(coords: Pair<CoordinateParam, CoordinateParam>): Single<List<Vehicle>> {

        return vehicleDao.get().flatMap {
            if (!it.isNullOrEmpty()) {
                Single.just(it)
            } else {
                service.requestData(coords).map { vehicles -> vehicles.poiList }
            }
        }.map { vehicleResponse ->
            vehicleResponse.map { it.responseToEntity() }
        }.onErrorResumeNext {
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