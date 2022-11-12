package com.yingenus.effectivemobiletest.presintation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yingenus.core.colors.resolveColorAttr
import com.yingenus.effectivemobiletest.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.splash_layout) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.navigationBarColor = requireContext().resolveColorAttr(androidx.appcompat.R.attr.colorPrimary)
        lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(R.id.splash_fragment_to_main)
        }
    }

}