package com.kashanok.cleancode.app

import com.kashanok.cleancode.BuildConfig
import com.kashanok.data.repository.VehicleRepositoryDefault
import com.kashanok.data.rest.VehicleService
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
                BuildConfig.API_ENDPOINT,
                VehicleService(VehicleApplication.instance.applicationContext),
                        VehicleApplication.instance.applicationContext
            )
        )
    }

    private fun getWorkScheduler() = Schedulers.io()

    private fun getPostScheduler() = AndroidSchedulers.mainThread()

}