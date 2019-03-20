package com.kashanok.cleancodeproject.data.entity

import com.google.gson.annotations.SerializedName

internal data class CoordinateResponse(

	@SerializedName("latitude")
	val latitude: Double,

	@SerializedName("longitude")
	val longitude: Double
)