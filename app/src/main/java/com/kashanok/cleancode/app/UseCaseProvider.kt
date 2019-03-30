package com.kashanok.cleancode.app

import com.kashanok.cleancode.BuildConfig
import com.kashanok.data.repository.VehicleRepositoryDefault
import com.kashanok.domain.usecase.vehicle.VehicleGetUseCase
import com.kashanok.domain.usecase.vehicle.VehicleGetUseCaseDefault
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object UseCaseProvider {

    fun provideGetVehicleuseCase(): VehicleGetUseCase {
        return VehicleGetUseCaseDefault(
            getWorkScheduler(),
            getPostScheduler(),
            VehicleRepositoryDefault(
                VehicleApplication.instance.applicationContext,
                BuildConfig.API_ENDPOINT
            )
        )
    }

    private fun getWorkScheduler() = Schedulers.io()

    private fun getPostScheduler() = AndroidSchedulers.mainThread()

}