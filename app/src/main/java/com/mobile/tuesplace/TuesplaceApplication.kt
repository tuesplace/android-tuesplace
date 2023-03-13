package com.mobile.tuesplace

import android.app.Application
import android.content.Intent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TuesplaceApplication : Application() {

    companion object {
        lateinit var instance: TuesplaceApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger()
            androidContext(this@TuesplaceApplication)
            modules(TuesplaceModules)
        }
    }

    fun provideLoginIntent(): Intent {
        return Intent(this, MainActivity::class.java)
    }
}