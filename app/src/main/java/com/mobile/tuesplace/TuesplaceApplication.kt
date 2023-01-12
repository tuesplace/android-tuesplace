package com.mobile.tuesplace

import android.app.Application
import android.content.Intent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TuesplaceApplication : Application() {

    private lateinit var authenticationManager: AuthenticationManager

    companion object {
        lateinit var instance: TuesplaceApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        authenticationManager = AuthenticationManager(instance, provideLoginIntent())
        startKoin {
            androidLogger()
            androidContext(this@TuesplaceApplication)
            modules(TuesplaceModules)
        }
    }

    fun provideLoginIntent(): Intent {
        return Intent(this, MainActivity::class.java)
    }

//    fun createAccount(userName: String, token: String, password: String) {
//        authenticationManager.createAccount(userName, token, password)
//    }
}