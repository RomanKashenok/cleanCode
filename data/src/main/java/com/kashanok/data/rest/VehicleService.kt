package com.kashanok.data.rest

import android.content.Context
import com.kashanok.cleancodeproject.data.entity.VehiclePoiResponse
import com.kashanok.cleancodeproject.data.entity.VehicleResponse
import com.kashanok.cleancodeproject.data.entity.responseToEntity
import com.kashanok.data.db.AppDatabase
import com.kashanok.data.net.provideApi
import com.kashanok.domain.entity.vehicle.CoordinateParam
import com.kashanok.domain.entity.vehicle.Vehicle
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class VehicleService(context: Context) {

    private val vehicleDao = AppDatabase.create(context).getVehicleDao()
    private val api = provideApi()
    private var disposable: Disposable? = null

    @Throws(Exception::class)
    fun requestData(coords: Pair<CoordinateParam, CoordinateParam>/*, url: String*/): Single<VehicleResponse> {
        val vehicleResponse = api.getVehicle(coords.first.lat, coords.first.long, coords.second.lat, coords.second.long)

        disposable = vehicleResponse
            .subscribe{response -> run {
                disposable?.let {
                    if (!it.isDisposed) {
                        it.dispose()
                    }
                }
            }
                vehicleDao.insert(response.poiList ?: arrayListOf())
            }


        return vehicleResponse
    }
}