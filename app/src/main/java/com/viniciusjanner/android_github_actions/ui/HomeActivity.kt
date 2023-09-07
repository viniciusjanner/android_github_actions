package com.viniciusjanner.android_github_actions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.viniciusjanner.android_github_actions.App
import com.viniciusjanner.android_github_actions.R
import com.viniciusjanner.android_github_actions.databinding.ActivityHomeBinding
import com.viniciusjanner.android_github_actions.prefs.ThemeMode

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen
        installSplashScreen()

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
            val isChecked = (themeMode == ThemeMode.DARK)

            binding.switchTheme.isChecked = isChecked
            binding.switchTheme.text = getString(if (isChecked) R.string.home_mode_dark else R.string.home_mode_light)

            val nightMode = if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
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
