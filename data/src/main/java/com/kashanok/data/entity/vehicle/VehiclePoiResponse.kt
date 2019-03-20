package com.kashanok.cleancodeproject.data.entity

import com.google.gson.annotations.SerializedName

internal data class VehiclePoiResponse(

	@SerializedName("coordinate")
	val coordinate: CoordinateResponse,

	@SerializedName("fleetType")
	val fleetType: String,

	@SerializedName("heading")
	val heading: Double,

	@SerializedName("id")
	val id: Int
)