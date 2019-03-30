package com.kashanok.data.repository

import android.content.Context
import com.google.gson.Gson
import com.kashanok.cleancodeproject.data.entity.VehiclePoiResponse
import com.kashanok.cleancodeproject.data.entity.VehicleResponse
import com.kashanok.cleancodeproject.data.entity.responseToEntity
import com.kashanok.data.BuildConfig
import com.kashanok.data.db.AppDatabase
import com.kashanok.domain.entity.exception.AppException
import com.kashanok.domain.entity.exception.AppExceptionType
import com.kashanok.domain.entity.vehicle.CoordinateParam
import com.kashanok.domain.entity.vehicle.Vehicle
import com.kashanok.domain.repository.vehicle.VehicleRepository
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class VehicleRepositoryDefault(context: Context, private val url: String) : VehicleRepository {

    private val P1LAT = "p1Lat"
    private val P2LAT = "p2Lat"
    private val P1LON = "p1Lon"
    private val P2LON = "p2Lon"
    private val vehicleDao = AppDatabase.create(context).getVehicleDao()

    override fun fetch(coords: Pair<CoordinateParam, CoordinateParam>): Single<List<Vehicle>> {
        val singleListVehiclePoi =
            vehicleDao
                .get()
                .flatMap { it ->
                    if (!it.isNullOrEmpty()) {
                        Single.just(it)
                    } else {
                        remoteRequest(coords)
                    }
                }.map { vehicleResponse ->
                    vehicleResponse.map { it.responseToEntity() }
                }

        return singleListVehiclePoi
    }

    @Throws(Exception::class)
    private fun requestData(coords: Pair<CoordinateParam, CoordinateParam>): VehicleResponse {
        val httpUrlBuilder = HttpUrl.parse(url)?.newBuilder()
        httpUrlBuilder?.addQueryParameter(P1LAT, coords.first.lat.toString())
        httpUrlBuilder?.addQueryParameter(P1LON, coords.first.long.toString())
        httpUrlBuilder?.addQueryParameter(P2LAT, coords.second.lat.toString())
        httpUrlBuilder?.addQueryParameter(P2LON, coords.second.long.toString())

        httpUrlBuilder?.let {
            val clientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            clientBuilder.connectTimeout(5, TimeUnit.SECONDS)
            val client: OkHttpClient = clientBuilder.build()

            val request = Request.Builder()
                .url(it.build())
                .build()

            val response = client.newCall(request).execute()
            val body = response.body()?.string() ?: throw Exception("Response body empty")
            return Gson().fromJson(body, VehicleResponse::class.java)

        }
        throw Exception("httpUrlBuilder creating Exception")
    }

    private fun remoteRequest(coords: Pair<CoordinateParam, CoordinateParam>): Single<List<VehiclePoiResponse>?> {
        return Single.create<VehicleResponse> { subscriber ->
            try {
                val response: VehicleResponse = requestData(coords)
                vehicleDao.delete()
                response.poiList?.let { vehicles ->
                    vehicleDao.insert(vehicles)
                }
                subscriber.onSuccess(response)
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }.map { it.poiList }.onErrorResumeNext {
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