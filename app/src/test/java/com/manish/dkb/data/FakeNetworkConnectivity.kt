package com.manish.dkb.data

import com.manish.dkb.utils.NetworkConnectivity

class FakeNetworkConnectivity: NetworkConnectivity {
    override fun isNetworkAvailable(): Boolean {
        return true
    }
}