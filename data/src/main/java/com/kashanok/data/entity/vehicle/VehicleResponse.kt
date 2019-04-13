package com.kashanok.cleancodeproject.data.entity

import com.google.gson.annotations.SerializedName

data class VehicleResponse(
    @SerializedName("poiList")
    val poiList: List<VehiclePoiResponse>? = null
)