package com.example.widget.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.appwidget.updateAll
import com.example.widget.CounterWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.content.edit

/**
 * Hlavní obrazovka aplikace s počítadlem
 * Zobrazuje hodnotu počítadla a tlačítka pro zvýšení/snížení
 * Bez překrývání prvků - počítadlo nahoře, tlačítka pod ním
 */
@Composable
fun CounterScreen(
    initialCounter: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var counter by remember { mutableIntStateOf(initialCounter) }

    // Funkce pro uložení hodnoty do SharedPreferences a aktualizaci widgetu
    fun updateCounter(newValue: Int) {
        counter = newValue

        // Uložení do SharedPreferences
        val sharedPrefs = context.getSharedPreferences("counter_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit { putInt("counter", newValue) }

        // Aktualizace widgetu
        CoroutineScope(Dispatchers.IO).launch {
            CounterWidget().updateAll(context)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Label pro počítadlo
        Text(
            text = "Counter",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Zobrazení hodnoty počítadla - velké číslo
        Text(
            text = "$counter",
            style = MaterialTheme.typography.displayLarge,
            fontSize = 64.sp,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // Řádek s tlačítky - bez překrývání
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            // Tlačítko pro snížení
            Button(
                onClick = { updateCounter(counter - 1) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "−", fontSize = 32.sp)
            }

            // Tlačítko pro zvýšení
            Button(
                onClick = { updateCounter(counter + 1) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "+", fontSize = 32.sp)
            }
        }

        // Informace o widgetu
        Text(
            text = "Add widget to home screen to see it in action!",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

