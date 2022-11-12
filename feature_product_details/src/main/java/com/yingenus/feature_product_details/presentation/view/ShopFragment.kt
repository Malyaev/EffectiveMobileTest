package com.yingenus.feature_product_details.presentation.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.yingenus.core.textutils.convertPrise
import com.yingenus.feature_product_details.R
import com.yingenus.feature_product_details.presentation.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class ShopFragment : Fragment(R.layout.shop_fragment) {

    private val productViewModel : ProductViewModel by viewModels(
        ownerProducer = {
            requireParentFragment()
        }
    )

    private var cpu : TextView? = null
    private var camera : TextView? = null
    private var ram : TextView? = null
    private var storage : TextView? = null

    private var colorChipGroup : ChipGroup? = null
    private var storageChipGroup : ChipGroup? = null

    private var prise : TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        cpu = view.findViewById(R.id.cpu_info)
        camera = view.findViewById(R.id.camera_info)
        ram = view.findViewById(R.id.ram_info)
        storage = view.findViewById(R.id.storage_info)
        prise = view.findViewById(R.id.prise)

        colorChipGroup = view.findViewById(R.id.color_chip)
        colorChipGroup!!.setOnCheckedStateChangeListener { group, checkedIds ->
            val view = group.findViewById<View>(checkedIds.first())
            productViewModel.setSelectedColor(view.tag as Int)
        }
        storageChipGroup = view.findViewById(R.id.storage_chip)
        storageChipGroup!!.setOnCheckedStateChangeListener { group, checkedIds ->
            val view = group.findViewById<View>(checkedIds.first())
            productViewModel.setSelectedStorageSize(view.tag as String)
        }

        view.findViewById<View>(R.id.add_button).setOnClickListener {
            productViewModel.addToChart()
        }

        subscribeToViewModel()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cpu = null
        camera = null
        ram = null
        storage = null
        colorChipGroup = null
        storageChipGroup = null
        prise = null
    }

    private fun subscribeToViewModel(){
        lifecycleScope.launchWhenStarted {
            productViewModel.cpu.onEach { cpu!!.text = it }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.camera.onEach { camera!!.text = it }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.ssd.onEach { storage!!.text = it }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.sd.onEach { ram!!.text = it }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.prise.onEach {
                prise!!.text = it.convertPrise("$",2,"us")
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.colors.onEach {
                updateColors(it)
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.capacity.onEach { updateSizes(it)}.collect()
        }
    }

    private fun updateColors(colors : List<Int>){
        colorChipGroup!!.removeAllViews()
        if (colors.isNotEmpty()){
            val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            colors.onEach {
                val view = inflater.inflate(R.layout.color_chip, colorChipGroup,false) as Chip
                view.chipBackgroundColor = ColorStateList.valueOf(it)
                view.id = it
                view.tag = it
                colorChipGroup!!.addView(view)
            }
            colorChipGroup!!.check(colors.first())
            productViewModel.setSelectedColor(colors.first())
        }
    }

    private fun updateSizes (sizes: List<String>){
        storageChipGroup!!.removeAllViews()
        if (sizes.isNotEmpty()){
            val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            sizes.onEach {
                val view = inflater.inflate(R.layout.storage_chip, storageChipGroup,false) as Chip
                view.text = it
                view.id = it.hashCode()
                view.tag = it
                storageChipGroup!!.addView(view)
            }
            storageChipGroup!!.check(sizes.first().hashCode())
            productViewModel.setSelectedStorageSize(sizes.first())
        }
    }


}