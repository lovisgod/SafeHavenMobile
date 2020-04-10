package com.lovisgod.safehaven.ui.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.databinding.ContactItemBinding
import com.lovisgod.safehaven.model.Contact

class HospFireAdapter: RecyclerView.Adapter<HospFireAdapter.Viewholder>() {
    private var contactList: ArrayList<Contact> = ArrayList()

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
        val itemView: ContactItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.contact_item, parent, false)
        return Viewholder(itemView)
    }

    override fun getItemCount(): Int {
        if(contactList.isNotEmpty()){
            return contactList.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: HospFireAdapter.Viewholder, position: Int) {
      val contact = contactList.get(position)
        holder.name.text = contact.name
        holder.address.text = contact.address
        holder.phone_number.text = contact.phone_number
        holder.more_info.setOnClickListener {
            Snackbar.make(holder.layout, "more info clicked", Snackbar.LENGTH_LONG).show()
        }
    }

    fun setAdvertList (contactList: ArrayList<Contact>){
        this.contactList = contactList
        println("this is ${contactList}")
        notifyDataSetChanged()
    }
}