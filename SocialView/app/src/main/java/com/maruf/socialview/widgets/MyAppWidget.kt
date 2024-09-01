package com.maruf.socialview.widgets

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import com.maruf.socialview.MainActivity

private const val TAG = "MyAppWidget"
class MyAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            // create your AppWidget here
            MyContent()
        }
    }

    override val stateDefinition: GlanceStateDefinition<WidgetData>? = null


    @Composable
    private fun MyContent() {

        val widgetData = remember { WidgetData.getInstance() }

        Log.d(TAG, "MyContent: ${widgetData.views}")


        Column(
            modifier = GlanceModifier.fillMaxSize()
                .background(GlanceTheme.colors.background),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Row {
                    Text(text = widgetData.views)
                }
            }
        }
    }
}