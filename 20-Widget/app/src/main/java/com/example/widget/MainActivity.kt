package com.example.widget

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.glance.appwidget.updateAll
import com.example.widget.ui.theme.WidgetTheme
import com.example.widget.ui.CounterScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val sharedPrefsName = "counter_prefs"
    private val counterKey = "counter"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Načtení hodnoty z SharedPreferences
        val sharedPrefs = getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        val initialCounter = sharedPrefs.getInt(counterKey, 0)
        
        // Synchronizace Glance stavu s SharedPreferences při startu
        CoroutineScope(Dispatchers.IO).launch {
            CounterWidget().updateAll(this@MainActivity)
        }
        
        setContent {
            WidgetTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterScreen(
                        initialCounter = initialCounter,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

