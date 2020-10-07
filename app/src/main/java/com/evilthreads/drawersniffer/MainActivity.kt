package com.evilthreads.drawersniffer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.candroid.bootlaces.LifecycleBootService
import com.candroid.bootlaces.bootService
import com.evilthreads.drawersnifferlib.DrawerSniffer
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : AppCompatActivity() {
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(!DrawerSniffer.hasPermission(this))
            DrawerSniffer.requestPermission(this)
        val payload = suspend{
            DrawerSniffer.subscribe(this){ notif ->
                Log.d("DRAWER SNIFFER", notif.toString())
            }
        }
        bootService(this, payload){
            service = MyService::class
        }
    }
}

class MyService: LifecycleBootService()