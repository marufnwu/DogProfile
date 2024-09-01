package com.maruf.socialview

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.maruf.socialview.ui.theme.SocialViewTheme
import com.maruf.socialview.widgets.MyAppWidget
import com.maruf.socialview.widgets.WidgetData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialViewTheme {
                // A surface container using the 'background' color from the theme
                MaterialTheme(

                ) {
                    Greeting("Android", context = this)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, context : Context) {
    Button(
        modifier = modifier,
       onClick = {
           CoroutineScope(Dispatchers.Main).launch {
               WidgetData.getInstance().views = System.currentTimeMillis().toString()
               Log.d("WidgetData", "Greeting: "+WidgetData.getInstance().views)
               val manager = GlanceAppWidgetManager(context)
               val widget = MyAppWidget()
               val glanceIds = manager.getGlanceIds(widget.javaClass)
               glanceIds.forEach { glanceId ->
                   Log.d("WidgetData", "glanceIds: $glanceId")
                   widget.update(context, glanceId)
               }
           }
       }
    ){
        Text(text = "Click Me")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SocialViewTheme {
//        Greeting("Android")
    }
}