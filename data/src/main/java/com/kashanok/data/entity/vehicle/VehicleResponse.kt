package com.kashanok.cleancodeproject.data.entity

import com.google.gson.annotations.SerializedName

internal data class VehicleResponse(
    @SerializedName("poiList")
    val poiList: List<VehiclePoiResponse>? = null
)