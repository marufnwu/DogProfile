package com.maruf.socialview.widgets

import androidx.lifecycle.MutableLiveData

class WidgetData {
    var views = ""
    companion object {
        private var widgetData : WidgetData? = null
        fun getInstance() : WidgetData{
            if(widgetData==null){
                widgetData = WidgetData()
            }

            return widgetData!!
        }
    }


}