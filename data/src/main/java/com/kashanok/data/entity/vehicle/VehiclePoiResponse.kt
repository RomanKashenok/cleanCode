package com.kashanok.cleancodeproject.data.entity

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

const val vehicleTableName = "vehicle"
const val coordinateTableName = "coordinate"
const val vehicleTableId = "id"

@Entity(tableName = vehicleTableName)
data class VehiclePoiResponse(

	@SerializedName("coordinate")
    @Embedded
	val coordinate: CoordinateResponse,

	@SerializedName("fleetType")
	val fleetType: String,

	@SerializedName("heading")
	val heading: Double,

	@SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = vehicleTableId)
    val id: Int
)