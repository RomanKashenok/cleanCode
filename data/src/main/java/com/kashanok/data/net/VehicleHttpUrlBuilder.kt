package com.kashanok.data.net

import com.kashanok.domain.entity.vehicle.CoordinateParam
import okhttp3.HttpUrl

object VehicleHttpUrlBuilder {
    private val P1LAT = "p1Lat"
    private val P2LAT = "p2Lat"
    private val P1LON = "p1Lon"
    private val P2LON = "p2Lon"

    fun buildFetchVehiclesUrl(coords: Pair<CoordinateParam, CoordinateParam>, url: String): HttpUrl? {
        val httpUrlBuilder = HttpUrl.parse(url)?.newBuilder()
        httpUrlBuilder?.addQueryParameter(P1LAT, coords.first.lat.toString())
        httpUrlBuilder?.addQueryParameter(P1LON, coords.first.long.toString())
        httpUrlBuilder?.addQueryParameter(P2LAT, coords.second.lat.toString())
        httpUrlBuilder?.addQueryParameter(P2LON, coords.second.long.toString())

        return httpUrlBuilder?.build()
    }
}