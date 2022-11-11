package com.yingenus.effectivemobiletest.presintation

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yingenus.effectivemobiletest.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.splash_layout) {
    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(R.id.splash_fragment_to_main)
        }
    }
}