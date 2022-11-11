package com.yingenus.feature_showcase.presentation.views

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.yingenus.feature_showcase.domain.dto.Location

internal class AutoCompleteTextViewHelper<T>(val autoCompleteTextView: AutoCompleteTextView,private val toString : (T) -> String){

    private var itemList : List<T> = emptyList()
    private var onSelected : ((T) -> Unit)? = null
    private var onNotSelected : (() -> Unit)? = null

    fun init(context: Context){
        autoCompleteTextView.setAdapter(ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line))
        autoCompleteTextView.setOnFocusChangeListener { view, b ->
            if (b) autoCompleteTextView.showDropDown()
        }
        autoCompleteTextView.onItemClickListener = object : OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                onSelected?.let { on ->
                    getSelectedItem()?.let { on.invoke(it) }
                }
                clearFocus()
            }
        }
    }

    fun setItems(items : List<T>){
        Log.d("AutoCompleteTextViewHelper", "set ${items.size} items to ${autoCompleteTextView.toString()}")
        itemList = items
        val adapter = autoCompleteTextView.adapter as ArrayAdapter<String>
        adapter.clear()
        adapter.addAll(itemList.map { toString.invoke(it) })
        adapter.notifyDataSetChanged()
    }

    fun selectItem(item: T){
        if (!itemList.any { toString.invoke(it) == toString.invoke(item) }){
            itemList = itemList + item
        }
        autoCompleteTextView.setText(toString.invoke(item))
    }

    fun getSelectedItem():T?{
        val line = autoCompleteTextView.text.toString()
        return itemList.find { toString.invoke(it) == line }
    }

    fun onItemSelected(on : (T) -> Unit){
        onSelected = on
    }

    private fun clearFocus(){
        autoCompleteTextView.clearFocus()
        val parent = autoCompleteTextView.parent
        if (parent is TextInputLayout) parent.clearFocus()

    }

}