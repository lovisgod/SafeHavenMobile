package com.lovisgod.safehaven.Util

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.model.ClosedMDialog
import kotlinx.android.synthetic.main.contact_dialog.*
import org.greenrobot.eventbus.EventBus
import org.koin.core.qualifier.named

class Dialog {
    @RequiresApi(Build.VERSION_CODES.M)
    fun displayContactDialog(context: Context, name:String, address: String, phone_number: String): AlertDialog? {
        val builder = AlertDialog.Builder(context)
        var inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mView: View = inflater.inflate( R.layout.contact_dialog, null )
        mView.findViewById<TextView>(R.id.contact_dialog_title).text = name
        mView.findViewById<TextView>(R.id.contact_dialog_address).text = address
        mView.findViewById<TextView>(R.id.contact_dialog_number).text = phone_number
        val open_button = mView.findViewById<ImageView>(R.id.contact_dialog_open_button)
        val call_now = mView.findViewById<TextView>(R.id.call_now)
        val website = mView.findViewById<TextView>(R.id.website)

        call_now.setOnClickListener {
            Snackbar.make(mView, "call now clicked", Snackbar.LENGTH_LONG)
                .setBackgroundTint(context.getColor(R.color.colorAccent))
                .show()
        }

        website.setOnClickListener {
            Snackbar.make(mView, "Website clicked", Snackbar.LENGTH_LONG)
                .setBackgroundTint(context.getColor(R.color.colorAccent))
                .show()
        }
        builder.setView(mView)
        val alertDialog = builder.create()
        return alertDialog
    }

    fun cancel_dailog() {
        // trigger close events
        EventBus.getDefault().post(ClosedMDialog(event = "close"))
    }
}