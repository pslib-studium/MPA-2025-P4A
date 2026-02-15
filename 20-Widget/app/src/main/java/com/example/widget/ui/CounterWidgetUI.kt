package com.example.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.action.clickable
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle

/**
 * Komponenta UI pro widget počítadla bez překrývání
 * Počítadlo je zobrazeno nahoře a tlačítka + a - jsou pod ním
 */
@Composable
fun CounterWidgetUILayout(counter: Int) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(GlanceTheme.colors.background)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Počítadlo - zobrazení hodnoty
        CounterDisplay(counter = counter)

        // Prostor mezi počítadlem a tlačítky
        Text(
            text = "",
            modifier = GlanceModifier.padding(vertical = 8.dp)
        )

        // Tlačítka + a -
        CounterButtons()
    }
}

/**
 * Komponenta zobrazující hodnotu počítadla
 */
@Composable
fun CounterDisplay(counter: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = GlanceModifier
            .fillMaxWidth()
            .clickable(onClick = actionStartActivity<com.example.widget.MainActivity>())
    ) {
        Text(
            text = "Counter",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = GlanceTheme.colors.onBackground
            ),
            modifier = GlanceModifier.padding(bottom = 4.dp)
        )

        Text(
            text = "$counter",
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = GlanceTheme.colors.primary
            ),
            modifier = GlanceModifier.padding(8.dp)
        )
    }
}

/**
 * Komponenta s tlačítky pro zvýšení a snížení hodnoty
 */
@Composable
fun CounterButtons() {
    Row(
        modifier = GlanceModifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            text = "-",
            onClick = actionRunCallback<DecreaseAction>(),
            modifier = GlanceModifier
                .width(56.dp)
                .height(44.dp)
        )

        Spacer(modifier = GlanceModifier.width(24.dp))

        Button(
            text = "+",
            onClick = actionRunCallback<IncreaseAction>(),
            modifier = GlanceModifier
                .width(56.dp)
                .height(44.dp)
        )
    }
}


// Action Callbacks pro změnu hodnoty počítadla
class IncreaseAction : ActionCallback {
    override suspend fun onAction(
        context: android.content.Context,
        glanceId: androidx.glance.GlanceId,
        parameters: androidx.glance.action.ActionParameters
    ) {
        com.example.widget.updateCounterValue(context, glanceId, 1)
    }
}

class DecreaseAction : ActionCallback {
    override suspend fun onAction(
        context: android.content.Context,
        glanceId: androidx.glance.GlanceId,
        parameters: androidx.glance.action.ActionParameters
    ) {
        com.example.widget.updateCounterValue(context, glanceId, -1)
    }
}
