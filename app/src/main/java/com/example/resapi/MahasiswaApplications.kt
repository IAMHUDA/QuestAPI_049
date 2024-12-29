package com.example.resapi

import android.app.Application
import com.example.resapi.containerapp.AppContainer
import com.example.resapi.containerapp.MahasiswaContainer

class MahasiswaApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}