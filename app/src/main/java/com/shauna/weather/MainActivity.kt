package com.shauna.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shauna.weather.ui.OverviewFragment
import com.shauna.weather.utils.NavigationUtil

class MainActivity : AppCompatActivity() {
    private val navigation: NavigationUtil = NavigationUtil.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.findFragmentById(R.id.modal_container)
        navigation.setFragmentManager(supportFragmentManager)
        NavigationUtil.getInstance().pushOrPopFragment(OverviewFragment(), 0, true)
    }
}