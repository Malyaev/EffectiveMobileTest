package com.yingenus.feature_product_details.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yingenus.feature_product_details.presentation.view.ShopFragment

internal class ProductInfoAdapter(parent : Fragment) : FragmentStateAdapter(parent) {
    companion object{
        const val SHOP = 0;
        const val DETAILS = 1;
        const val FEATURE = 2;
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ShopFragment()
            else -> throw RuntimeException()
        }
    }
}