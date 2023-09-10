package com.viniciusjanner.android_github_actions.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.viniciusjanner.android_github_actions.App
import com.viniciusjanner.android_github_actions.R
import com.viniciusjanner.android_github_actions.databinding.ActivityHomeBinding
import com.viniciusjanner.android_github_actions.prefs.ThemeMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen
        runBlocking {
            when (Build.VERSION.SDK_INT) {
                in 1..22 -> {
                    delay(1700)
                    setTheme(R.style.Theme_Home)
                }
                else -> {
                    installSplashScreen()
                    delay(1700)
                }
            }
        }

        // Home Screen
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initInstances()
        initObservers()
        initListeners()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    @Suppress("MaxLineLength")
    private fun initInstances() {
        val dataStoreManager = App.dataStoreManager
        viewModel = ViewModelProvider(this, HomeViewModelFactory(this.application, dataStoreManager))[HomeViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.themeLiveData.observe(this@HomeActivity) { themeMode ->
            val isCheck = (themeMode == ThemeMode.DARK)

            binding.switchTheme.apply {
                isChecked = isCheck
                text = getString(if (isCheck) R.string.home_mode_dark else R.string.home_mode_light)
            }

            val nightMode = if (isCheck) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(nightMode)
            delegate.applyDayNight()
        }
    }

    private fun initListeners() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            val themeMode = if (isChecked) ThemeMode.DARK else ThemeMode.LIGHT
            viewModel.setTheme(themeMode)
        }
    }
}
