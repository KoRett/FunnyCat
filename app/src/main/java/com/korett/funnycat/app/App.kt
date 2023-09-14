package com.korett.funnycat.app

import android.app.Application
import com.korett.funnycat.di.AppComponent
import com.korett.funnycat.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(this).build()
    }
}