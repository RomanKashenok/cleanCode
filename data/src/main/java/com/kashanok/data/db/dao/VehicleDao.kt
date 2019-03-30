package com.kashanok.data.db.dao

import android.arch.persistence.room.*
import com.kashanok.cleancodeproject.data.entity.VehiclePoiResponse
import com.kashanok.cleancodeproject.data.entity.VehicleResponse
import com.kashanok.cleancodeproject.data.entity.vehicleTableId
import com.kashanok.cleancodeproject.data.entity.vehicleTableName
import com.kashanok.data.db.AppDatabase
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface VehicleDao {

    @Query("SELECT * FROM $vehicleTableName")
    fun get(): Single<List<VehiclePoiResponse>>

    @Query
        ("SELECT * FROM $vehicleTableName WHERE $vehicleTableId = :id LIMIT 1")
    fun getById(id: Int): Single<VehiclePoiResponse>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vehicles: List<VehiclePoiResponse>): Unit

    @Query("DELETE FROM $vehicleTableName")
    fun delete(): Unit
}