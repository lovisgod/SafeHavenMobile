package com.lovisgod.safehaven.Util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.model.ClosedMDialog
import kotlinx.android.synthetic.main.contact_dialog.*
import org.greenrobot.eventbus.EventBus
import org.koin.core.qualifier.named

class Dialog {
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    fun displayContactDialog(context: Context, name:String, address: String, phone_number: String, websiteurl: String): AlertDialog? {
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
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "${phone_number}"))
            context.startActivity(intent)
        }

        website.setOnClickListener {
            Snackbar.make(mView, "Website clicked", Snackbar.LENGTH_LONG)
                .setBackgroundTint(context.getColor(R.color.colorAccent))
                .show()
            val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("${websiteurl}"))
            context.startActivity(intentBrowser)
        }
        builder.setView(mView)
        val alertDialog = builder.create()
        return alertDialog
    }


    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    fun displayAddContactDialog(context: Context): AlertDialog? {
        val builder = AlertDialog.Builder(context)
        var inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mView: View = inflater.inflate( R.layout.safefriend_layout, null )
        val layoutTitle = mView.findViewById<TextView>(R.id.contact_dialog_title)
        val friendName = mView.findViewById<TextInputEditText>(R.id.name_input)
        val friendNumber = mView.findViewById<TextInputEditText>(R.id.phone_input)
        val saveBtn = mView.findViewById<MaterialTextView>(R.id.save_number)
        val cancelBtn = mView.findViewById<MaterialTextView>(R.id.cancel_number)

        cancelBtn.text = "Cancel"

        saveBtn.setOnClickListener {
            if ( friendName.text.toString().isNotBlank() || friendNumber.text.toString().isNotBlank() ) {
                println(friendName.text.toString())
                println(friendNumber.text.toString())
                Snackbar.make(mView, "Save clicked", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(context.getColor(R.color.colorAccent))
                    .show()
            }

        }

        cancelBtn.setOnClickListener {
            Snackbar.make(mView, "Cancel clicked", Snackbar.LENGTH_LONG)
                .setBackgroundTint(context.getColor(R.color.colorAccent))
                .show()
            cancel_dailog()
        }
        builder.setView(mView)
        val alertDialog = builder.create()
        return alertDialog
    }


    // edit friend details
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    fun displayEditContactDialog(context: Context, phone: String, friendname: String): AlertDialog? {
        val builder = AlertDialog.Builder(context)
        var inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mView: View = inflater.inflate( R.layout.safefriend_layout, null )
        val layoutTitle = mView.findViewById<TextView>(R.id.contact_dialog_title)
        val friendName = mView.findViewById<TextInputEditText>(R.id.name_input)
        val friendNumber = mView.findViewById<TextInputEditText>(R.id.phone_input)
        val saveBtn = mView.findViewById<MaterialTextView>(R.id.save_number)
        val cancelBtn = mView.findViewById<MaterialTextView>(R.id.cancel_number)

        layoutTitle.text = "Edit Details"
        friendName.setText(friendname)
        friendNumber.setText(phone)
        cancelBtn.text = "Delete"

        saveBtn.setOnClickListener {
            if ( friendName.text.toString().isNotBlank() || friendNumber.text.toString().isNotBlank() ) {
                println(friendName.text.toString())
                println(friendNumber.text.toString())
                Snackbar.make(mView, "Save clicked", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(context.getColor(R.color.colorAccent))
                    .show()
            }
        }

        cancelBtn.setOnClickListener {
            Snackbar.make(mView, "Cancel clicked", Snackbar.LENGTH_LONG)
                .setBackgroundTint(context.getColor(R.color.colorAccent))
                .show()
            cancel_dailog()
        }
        builder.setView(mView)
        val alertDialog = builder.create()
        return alertDialog
    }



    fun cancel_dailog() {
        // trigger close events
        EventBus.getDefault().post(ClosedMDialog(event = "close"))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun makeSnack(view:View, message:String, context: Context) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(context.getColor(R.color.colorAccent))
            .show()
    }
}