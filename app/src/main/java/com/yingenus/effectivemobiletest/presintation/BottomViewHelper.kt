package com.yingenus.effectivemobiletest.presintation

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.yingenus.core.viewutils.findView
import com.yingenus.effectivemobiletest.R

class BottomViewHelper constructor(val view: ViewGroup){

    enum class ItemType{
        SHOP, CART, LIKED, ACCOUNT
    }

    private data class Item(val id: Int,val icon: Int, val title : Int, val item : ItemType)

    private companion object{
        val itemsList: List<Item> =
            listOf(
                Item(R.id.shop,R.drawable.shop, R.string.explore,ItemType.SHOP),
                Item(R.id.like,R.drawable.liked, R.string.liked,ItemType.LIKED),
                Item(R.id.cart,R.drawable.cart, R.string.cart,ItemType.CART),
                Item(R.id.account,R.drawable.account,R.string.account,ItemType.ACCOUNT)
            )
    }

    private val itemViews : List<Pair<ItemType, View>>
    private var onItemClicked : ((ItemType) -> Unit)? = null

    init {
        val container = view.findView(LinearLayout::class.java)!!
        val views = itemsList.map {
            val view =
                container.findViewById<View>(it.id)
            initView(view,it)
            it.item to view
        }
        itemViews = views
    }


    fun setClickListener(on :(ItemType) -> Unit){
        onItemClicked = on
    }

    fun selectItem(item : ItemType){
        itemViews.forEach {
            if (item == it.first) selectView(it.second, true)
            else selectView(it.second, false)
        }
        view.requestLayout()
    }

    fun hide(hide : Boolean){
        view.visibility = if (hide) View.GONE else View.VISIBLE
        view.requestLayout()
    }

    private fun selectView(v : View, isSelected : Boolean){
        v.findViewById<ImageView>(R.id.icon).visibility = if (isSelected) View.GONE else View.VISIBLE
        v.findViewById<View>(R.id.round).visibility = if (isSelected) View.VISIBLE else View.GONE
        v.findViewById<TextView>(R.id.title).visibility = if (isSelected) View.VISIBLE else View.GONE
    }

    private fun initView(v : View, item :Item){
        v.findViewById<ImageView>(R.id.icon).setImageResource(item.icon)
        v.findViewById<TextView>(R.id.title).text = view.context.getString(item.title)
    }


}