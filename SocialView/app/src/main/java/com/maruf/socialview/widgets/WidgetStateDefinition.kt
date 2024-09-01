package com.maruf.socialview.widgets

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.glance.state.GlanceStateDefinition
import java.io.File

class WidgetStateDefinition : GlanceStateDefinition<WidgetData> {
    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<WidgetData> {
        return WidgetData.getInstance()
    }

    override fun getLocation(context: Context, fileKey: String): File {
        TODO("Not yet implemented")
    }
}