package com.kashanok.cleancode.presentation.screen.vehicle

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kashanok.cleancode.R
import com.kashanok.cleancode.presentation.base.BaseMvvmActivity
import com.kashanok.cleancode.presentation.screen.vehicle.list.VehicleListFragment
import com.kashanok.cleancode.presentation.screen.vehicle.list.VehicleListViewModel
import com.kashanok.cleancode.presentation.screen.vehicle.list.VehicleListViewModelFactory
import com.kashanok.domain.entity.vehicle.Vehicle
import kotlinx.android.synthetic.main.fragment_vehicle_list.*

class VehicleMapActivity : BaseMvvmActivity<VehicleListViewModel>(), OnMapReadyCallback {

    companion object {

        private const val ZOOM_FACTOR = 12f
        private const val ZOOM_ANIMATE_SPEED = 1300
    }

    override fun provideViewModel(): VehicleListViewModel {
        return ViewModelProviders.of(this, VehicleListViewModelFactory())
            .get(VehicleListViewModel::class.java)
    }

    override fun provideLayoutId(): Int {
        return R.layout.activity_vehicle_map
    }

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if (savedInstanceState == null) initList()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap?.let {
            it.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            it.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }

        viewModel
            .vehicleClicked
            .observe(this, object : Observer<Vehicle> {
            override fun onChanged(vehicle: Vehicle?) {
                if (vehicle != null) {
                    moveToVehicle(vehicle)
                    hideBottomSheet()
                }
            }
        })
    }

    fun moveToVehicle(vehicle: Vehicle) {
        val coordinates = LatLng(vehicle.coordinateParam.lat, vehicle.coordinateParam.long)
        mMap?.let {
            it.clear()
            it.addMarker(MarkerOptions().position(coordinates).title(vehicle.heading.toString()))
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, ZOOM_FACTOR), ZOOM_ANIMATE_SPEED, null)
        }
    }

    fun hideBottomSheet() {
        val behavior = BottomSheetBehavior.from(bottomSheetLayout)
        (bottomSheetLayout.parent as CoordinatorLayout).post {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initList() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, VehicleListFragment())
        transaction.commit()
    }
}
