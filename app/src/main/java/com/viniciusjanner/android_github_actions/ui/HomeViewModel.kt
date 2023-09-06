package com.viniciusjanner.android_github_actions.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.viniciusjanner.android_github_actions.prefs.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application):AndroidViewModel(application) {

    private val dataStore = DataStoreManager(application)

    val getTheme = dataStore.getTheme().asLiveData(Dispatchers.IO)

    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            dataStore.setTheme(isDarkMode)
        }
    }
}
