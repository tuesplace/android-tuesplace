package com.mobile.tuesplace

import android.app.Application
import android.content.Intent
import com.caregility.uhe.authentication.AuthenticationManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TuesplaceApplication : Application() {

    companion object {
        lateinit var instance: TuesplaceApplication
            private set
    }

    private lateinit var authenticationManager: AuthenticationManager

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

       // authenticationManager = AuthenticationManager(instance, getCurrentLoggedUser())
        startKoin {
            androidLogger()
            androidContext(this@TuesplaceApplication)
            modules(TuesplaceModules)
        }
    }

    /**
     * Retrieves a token for the current user
     *
     *  @return the stored token, if available, for the current logged user, if there is one. Null otherwise
     */
    fun getCurrentUserToken(): String? {
        return authenticationManager.peekTokenForUserWithName(instance, getCurrentLoggedUser())
    }

    /**
     * Retrieves a refresh token for the current user
     *
     *  @return the stored token, if available, for the current logged user, if there is one. Null otherwise
     */
    fun getCurrentUserPassword(): String? {
        return authenticationManager.peekRefreshTokenUserWithName(instance, getCurrentLoggedUser())
    }

    fun provideLoginIntent(): Intent {
        // TODO MAKE LOGIN ACTIVITY
        return Intent(this, MainActivity::class.java)
    }

    private fun getCurrentLoggedUser(): String? {
        //return mPreferences.getString(KEY_USERNAME, null)
        return null
    }
}