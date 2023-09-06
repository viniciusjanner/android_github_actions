package com.viniciusjanner.android_github_actions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.viniciusjanner.android_github_actions.R
import com.viniciusjanner.android_github_actions.databinding.ActivityHomeBinding
import com.viniciusjanner.android_github_actions.prefs.DataStoreManager
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen
        installSplashScreen()

        // Home Screen
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        initInstances()
        checkThemeMode()
        initListeners()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initInstances() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        dataStoreManager = DataStoreManager(this)
    }

    private fun checkThemeMode() {
        binding.apply {
            viewModel.getTheme.observe(this@HomeActivity) { isDarkMode ->
                when (isDarkMode) {
                    true -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        switchTheme.run {
                            isChecked = true
                            text = getString(R.string.home_mode_dark)
                        }
                    }
                    false -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        switchTheme.run {
                            isChecked = false
                            text = getString(R.string.home_mode_light)
                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                when (isChecked) {
                    true -> viewModel.setTheme(true)
                    false -> viewModel.setTheme(false)
                }
            }
        }
    }
}
