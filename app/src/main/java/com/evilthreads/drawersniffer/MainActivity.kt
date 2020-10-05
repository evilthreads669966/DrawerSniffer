package com.evilthreads.drawersniffer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        bootService(this){
            service = MyService::class
        }
    }
}