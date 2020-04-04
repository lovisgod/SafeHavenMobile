package com.lovisgod.safehaven.Util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.lovisgod.safehaven.model.ClosedMDialog
import org.greenrobot.eventbus.EventBus

class Dialog {
    fun getMessage(context: Context, layout: Int = 0, image:Int= 0, message: String = ""): AlertDialog? {
        val builder = AlertDialog.Builder(context)
        var inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mView: View = inflater.inflate( layout, null )
        builder.setView(mView)
        val alertDialog = builder.create()
        return alertDialog
    }

    fun cancel_dailog() {
        // trigger close events
        EventBus.getDefault().post(ClosedMDialog(event = "close"))
    }
}