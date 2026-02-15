package com.example.widget

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.currentState
import androidx.glance.state.PreferencesGlanceStateDefinition

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.widget.ui.CounterWidgetUILayout

val COUNTER_KEY = intPreferencesKey("counter")

class CounterWidget : GlanceAppWidget() {

    override val stateDefinition = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            val counter = prefs[COUNTER_KEY] ?: 0

            CounterWidgetUILayout(counter = counter)
        }
    }
}

// Pomocná funkce pro aktualizaci hodnoty počítadla
suspend fun updateCounterValue(context: Context, glanceId: GlanceId, delta: Int) {
    updateAppWidgetState(context, glanceId) { prefs ->
        val currentValue = prefs[COUNTER_KEY] ?: 0
        prefs[COUNTER_KEY] = currentValue + delta
    }

    CounterWidget().update(context, glanceId)
}

class CounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = CounterWidget()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onUpdate(
        context: Context,
        appWidgetManager: android.appwidget.AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val sharedPrefs = context.getSharedPreferences("counter_prefs", Context.MODE_PRIVATE)
        val counterValue = sharedPrefs.getInt("counter", 0)

        coroutineScope.launch {
            appWidgetIds.forEach { appWidgetId ->
                val manager = androidx.glance.appwidget.GlanceAppWidgetManager(context)
                val glanceId = manager.getGlanceIdBy(appWidgetId)

                updateAppWidgetState(context, glanceId) { prefs ->
                    prefs[COUNTER_KEY] = counterValue
                }

                CounterWidget().update(context, glanceId)
            }
        }
    }
}
