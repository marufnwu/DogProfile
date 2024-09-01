package com.maruf.jetnews

import android.app.Application
import com.maruf.jetnews.data.AppContainer
import com.maruf.jetnews.data.AppContainerImpl

class App : Application() {
    companion object {
        const val JETNEWS_APP_URI = "https://developer.android.com/jetnews"
    }

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}