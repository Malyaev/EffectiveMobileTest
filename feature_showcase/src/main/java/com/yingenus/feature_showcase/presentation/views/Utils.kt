package com.yingenus.feature_showcase.presentation.views

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.yingenus.feature_showcase.domain.dto.Location

internal class LocationHelper(val autoCompleteTextView: AutoCompleteTextView){

    private var locationList: List<Location> = emptyList()
    private var onSelected : ((Location) -> Unit)? = null

    fun init(context: Context){
        autoCompleteTextView.setAdapter(ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line))
    }

    fun setLocations(locations : List<Location>){
        locationList = emptyList()
        val adapter = autoCompleteTextView.adapter as ArrayAdapter<String>
        adapter.clear()
        adapter.addAll(locations.map { it.name })
        autoCompleteTextView.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                onSelected?.let { on ->
                    getSelectedLocation()?.let { on.invoke(it) }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }
        }
    }

    fun selectLocation(location: Location){
        if (!locationList.any { it.name == location.name }){
            locationList = locationList + location
        }
        autoCompleteTextView.setText(location.name)
    }

    fun getSelectedLocation():Location?{
        val locationName = autoCompleteTextView.text.toString()
        return locationList.find { it.name == locationName }
    }

    fun onItemSelected(on : (Location) -> Unit){
        onSelected = on
    }

}