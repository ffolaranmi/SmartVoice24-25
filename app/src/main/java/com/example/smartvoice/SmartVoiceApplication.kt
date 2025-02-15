package com.example.smartvoice

import android.app.Application
import com.example.smartvoice.data.AppContainer
import com.example.smartvoice.data.AppDataContainer

class SmartVoiceApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
