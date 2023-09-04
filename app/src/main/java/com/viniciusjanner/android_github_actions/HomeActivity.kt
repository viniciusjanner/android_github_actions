package com.viniciusjanner.android_github_actions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen
        runBlocking {
            installSplashScreen()
            delay(2000)
        }

        // Home Screen
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
