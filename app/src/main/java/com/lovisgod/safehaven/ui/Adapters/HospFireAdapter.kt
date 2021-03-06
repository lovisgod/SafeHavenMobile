package com.lovisgod.safehaven.ui.Adapters

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.github.loadingview.LoadingDialog
import com.google.android.material.snackbar.Snackbar
import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.Util.Dialog
import com.lovisgod.safehaven.databinding.ContactItemBinding
import com.lovisgod.safehaven.model.Business
import com.lovisgod.safehaven.model.Contact
import com.lovisgod.safehaven.ui.Fragments.LandingFragment
import com.lovisgod.safehaven.viewmodel.AppViewModel

class HospFireAdapter(val viewmodel: AppViewModel, val lifecycleOwner: LifecycleOwner, val activity: Activity): RecyclerView.Adapter<HospFireAdapter.Viewholder>() {
    private var contactList: ArrayList<Business> = ArrayList()
    val dialog  = Dialog()

    class Viewholder(itemView: ContactItemBinding): RecyclerView.ViewHolder(itemView.root) {
        val name = itemView.contactTitle
        val phone_number = itemView.number
        val address = itemView.address
        val open_button = itemView.openButton
        val more_info = itemView.moreInfo
        val call_button = itemView.callButton
        val layout = itemView.contact
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospFireAdapter.Viewholder {
        val itemView: ContactItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.contact_item, parent, false)
        return Viewholder(itemView)
    }

    override fun getItemCount(): Int {
        if(contactList.isNotEmpty()){
            return contactList.size
        }
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: HospFireAdapter.Viewholder, position: Int) {
      val contact = contactList.get(position)
        holder.name.text = contact.name
        holder.address.text = contact.vicinity
        holder.phone_number.text = contact.business_status
        if (contact.opening_hours != null) {
            if (contact.opening_hours.open_now) {
                holder.open_button.setImageDrawable(holder.name.context.getDrawable(R.drawable.ic_open))
            }
        }

        holder.more_info.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    holder.name.context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    2
                )
                return@setOnClickListener
            }
            val dialogg = LoadingDialog.get(activity).show()
            viewmodel.getPlaceDetails(contact.place_id).observe(lifecycleOwner, Observer {
                dialogg.hide()
                if (it.name != null) {
                    dialog.displayContactDialog(
                        holder.more_info.context,
                        contact.name,
                        contact.vicinity,
                        it.international_phone_number,
                        it.website
                    )?.show()
                }
            })

        }

        holder.call_button.setOnClickListener {
            val dialogg = LoadingDialog.get(activity).show()
            viewmodel.getPlaceDetails(contact.place_id).observe(lifecycleOwner, Observer {
                dialogg.hide()
                if (it.name != null) {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "${it.international_phone_number}"))
                    if (ActivityCompat.checkSelfPermission(
                            holder.name.context,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(android.Manifest.permission.CALL_PHONE),
                            2
                        )
                        return@Observer
                    }
                    activity.startActivity(intent)
                }
            })

        }
    }

    fun setAdvertList (contactList: ArrayList<Business>){
        this.contactList = contactList
        notifyDataSetChanged()
    }
}