package com.kashanok.cleancodeproject.data.entity

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

const val vehicleCoordinatesTableName = "coordinates"
const val vehicleCoordinatesTableId = "coord_id"

//@Entity(tableName = vehicleCoordinatesTableName)
//@Entity(tableName = vehicleCoordinatesTableName,
//    foreignKeys = @ForeignKey(entity = Company.class,
//        parentColumns = "id",
//childColumns = "company_id",
//onDelete = ForeignKey.NO_ACTION))
data class CoordinateResponse(

//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = vehicleCoordinatesTableId)
//    val id: Int,

	@SerializedName("latitude")
	val latitude: Double,

	@SerializedName("longitude")
	val longitude: Double
)