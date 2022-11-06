package com.yingenus.api_network.di

import android.content.Context

interface NetworkDependency {
    fun getContext(): Context
}