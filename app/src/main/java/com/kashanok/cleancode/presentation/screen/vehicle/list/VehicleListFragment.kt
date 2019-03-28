package com.kashanok.cleancode.presentation.screen.vehicle.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.kashanok.cleancode.R
import com.kashanok.cleancode.app.common.ItemOffsetDecoration
import com.kashanok.cleancode.presentation.base.BaseMvvmFragment
import com.kashanok.cleancode.presentation.screen.vehicle.list.recycler.VehicleListAdapter
import kotlinx.android.synthetic.main.fragment_vehicle_list.*

class VehicleListFragment : BaseMvvmFragment<VehicleListViewModel>() {

    override fun provideLayoutId() = R.layout.fragment_vehicle_list

    override fun provideViewModel(): VehicleListViewModel {
        return ViewModelProviders.of(activity!!, VehicleListViewModelFactory())
            .get(VehicleListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvVehicles.layoutManager = LinearLayoutManager(requireContext())
        rvVehicles.adapter = VehicleListAdapter(requireContext(), viewModel)

        rvVehicles.addItemDecoration(getItemDecoration())

        val behavior = BottomSheetBehavior.from(bottomSheetLayout)
        view.post {

            arrowImageView.setOnClickListener {
                when (behavior.state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    }
                    else -> {
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            }

            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(view: View, offset: Float) {
                }

                override fun onStateChanged(view: View, newState: Int) {
                    var drawable: Drawable? = when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            resources.getDrawable(R.drawable.ic_up_arrow)
                        }
                        else -> {
                            resources.getDrawable(R.drawable.ic_down_arrow)
                        }
                    }
                    arrowImageView.setImageDrawable(drawable)
                }
            })

            behavior.peekHeight = resources.getDimensionPixelSize(R.dimen.dimen_52dp)
            behavior.isFitToContents = false
        }
        observeVehicleState()
    }


    override fun initView() {
        observeVehicleState()
    }

    private fun observeVehicleState() {
        viewModel
            .vehicleState
            .observe(this, object : Observer<VehicleState> {
                override fun onChanged(state: VehicleState?) {
                    if (state != null) processState(state)
                }
            })
    }

    private fun processState(state: VehicleState) {
        when (state) {
            is VehicleState.Progress -> {
                //SHOW PROGRESS
            }
            is VehicleState.Done -> {
                val list = state.vehicleList
                Toast.makeText(context, "Received list size: ${list.size}", Toast.LENGTH_LONG).show()
                (rvVehicles.adapter as VehicleListAdapter).setNewItems(list)
                // PUT INTO ADAPTER
            }
            is VehicleState.Error -> {
                Toast.makeText(context, "Error: ${state.throwable.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getItemDecoration(): ItemOffsetDecoration {
        return ItemOffsetDecoration(
            requireContext(),
            R.dimen.dimen_52dp,
            R.dimen.dimen_9dp,
            R.dimen.dimen_9dp,
            R.dimen.dimen_9dp,
            R.dimen.dimen_0dp,
            R.dimen.dimen_27dp
        )
    }
}