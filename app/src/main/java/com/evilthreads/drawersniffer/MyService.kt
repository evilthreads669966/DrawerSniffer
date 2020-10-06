package com.evilthreads.drawersniffer

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.candroid.bootlaces.LifecycleBootService
import com.evilthreads.drawersnifferlib.DrawerSniffer
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MyService: LifecycleBootService(){
    init {
        lifecycleScope.launchWhenCreated {
            DrawerSniffer.subscribe{ notif ->
                Log.d("DRAWER SNIFFER", notif.toString())
            }
        }
    }
}