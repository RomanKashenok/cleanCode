package com.kashanok.cleancode.presentation.screen.vehicle.list.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kashanok.cleancode.R
import com.kashanok.cleancode.presentation.screen.vehicle.list.VehicleListViewModel
import com.kashanok.domain.entity.vehicle.Vehicle

class VehicleListAdapter (
    private val context: Context,
    private val viewModel: VehicleListViewModel
): RecyclerView.Adapter<VehicleListItemHolder>() {

    private var vehicles: List<Vehicle> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListItemHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.vehicle_list_item, parent, false)
        return VehicleListItemHolder(view)
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: VehicleListItemHolder, position: Int) {
        holder.type.text = vehicles[position].fleetType.toString()
        holder.heading.text = vehicles[position].heading.toString()
        holder.coordinates.text = "${vehicles[position].coordinateParam.lat}, ${vehicles[position].coordinateParam.long}"
        holder.cardView.setOnClickListener{
            viewModel.vehicleClick(vehicles[position])

        }
    }

    fun setNewItems(items: List<Vehicle>) {
        vehicles = items
        notifyDataSetChanged()
    }
}