package com.evilthreads.drawersniffer

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.candroid.bootlaces.BootService
import com.evilthreads.drawersnifferlib.DrawerSniffer
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MyService: BootService(){
    init {
        lifecycleScope.launchWhenCreated {
            DrawerSniffer.subscribe{ notif ->
                Log.d("DRAWER SNIFFER", notif.toString())
            }
        }
    }
}