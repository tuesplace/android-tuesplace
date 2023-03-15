package com.mobile.tuesplace.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.tuesplace.EMPTY_STRING
import com.mobile.tuesplace.dataStore
import com.mobile.tuesplace.session.SessionManager
import kotlinx.coroutines.launch

class SettingsViewModel(private val context: Context) : ViewModel() {

    private val sessionManager = SessionManager.getInstance(dataStore = context.dataStore)
    fun deleteTokensData() {
        viewModelScope.launch {
            deleteSavedTokens()
            sessionManager.setTokens()
        }
    }

    private fun deleteSavedTokens() {
        viewModelScope
            .launch {
                sessionManager.setAuthEntities(EMPTY_STRING, EMPTY_STRING)
            }
    }
}