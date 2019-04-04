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

//    fun getVehicles(coords: Pair<CoordinateParam, CoordinateParam>, url: String): Single<List<Vehicle>> {
//        val vehiclesList =
//            vehicleDao
//                .get()
//                .flatMap { it ->
//                    if (!it.isNullOrEmpty()) {
//                        Single.just(it)
//                    } else {
//                        remoteRequest(coords, url)
//                    }
//                }.map { vehicleResponse ->
//                    vehicleResponse.map { it.responseToEntity() }
//                }
//        return vehiclesList
//    }

//    private fun remoteRequest(coords: Pair<CoordinateParam, CoordinateParam>, url: String)
//            : Single<List<VehiclePoiResponse>?> {
//
//        return requestData(coords, url).map { it.poiList }
//
//        return Single.create<VehicleResponse> { subscriber ->
//            try {
//                val response: Single<VehicleResponse> = requestData(coords, url)
//                vehicleDao.delete()
//                response.poiList?.let { vehicles ->
//                    vehicleDao.insert(vehicles)
//                }
//                subscriber.onSuccess(response)
//            } catch (e: Exception) {
//                subscriber.onError(e)
//            }
//        }.map { it.poiList }
//    }

    @Throws(Exception::class)
    public fun requestData(coords: Pair<CoordinateParam, CoordinateParam>/*, url: String*/): Single<VehicleResponse> {
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
//        val requestUrl = VehicleHttpUrlBuilder.buildFetchVehiclesUrl(coords, url) ?: throw AppException()
//        val client = VehicleHttpClientBuilder.getHttpClient()
//
//        val request = Request.Builder().url(requestUrl).build()
//        val response = client.newCall(request).execute()
//        val body = response.body()?.string() ?: throw Exception("Response body empty")
//        return Gson().fromJson(body, VehicleResponse::class.java)
    }
}