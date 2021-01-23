package com.wajahatkarim3.imagine.utils

import android.app.Application
import com.wajahatkarim3.imagine.R

class StringUtils(val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = context.getString(R.string.message_something_went_wrong_str)
}