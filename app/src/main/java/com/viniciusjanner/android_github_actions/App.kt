package com.viniciusjanner.android_github_actions

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.viniciusjanner.android_github_actions.prefs.DataStoreManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class App : Application() {

    companion object {
        lateinit var dataStoreManager: DataStoreManager
    }

    override fun onCreate() {
        super.onCreate()
        dataStoreManager = DataStoreManager(this@App)

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("App", "coroutineExceptionHandler : throwable = ${throwable.message}")
            throwable.printStackTrace()
        }

        val coroutineContext: CoroutineContext = Dispatchers.Default + coroutineExceptionHandler

        CoroutineScope(coroutineContext).launch {
            dataStoreManager.getTheme().collect {
                AppCompatDelegate.setDefaultNightMode(
                    if (it) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }
    }
}
