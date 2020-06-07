package com.lovisgod.safehaven.ui.Adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.Util.Dialog
import com.lovisgod.safehaven.databinding.ContactItemBinding
import com.lovisgod.safehaven.databinding.FriendItemBinding
import com.lovisgod.safehaven.model.Contact
import com.lovisgod.safehaven.model.Friend

class SafeFriendsListAdapter: RecyclerView.Adapter<SafeFriendsListAdapter.Viewholder>() {
    private var friendList: ArrayList<Friend> = ArrayList()
    val dialog  = Dialog()

    class Viewholder(itemView: FriendItemBinding): RecyclerView.ViewHolder(itemView.root) {
        val name = itemView.friendTitle
        val phone_number = itemView.friendNumber
        val arrow = itemView.seeDetails
        val layout = itemView.friendItem
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SafeFriendsListAdapter.Viewholder {
        val itemView: FriendItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.friend_item, parent, false)
        return Viewholder(itemView)
    }

    override fun getItemCount(): Int {
        if(friendList.isNotEmpty()){
            return friendList.size
        }
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: SafeFriendsListAdapter.Viewholder, position: Int) {
        val contact = friendList.get(position)
        holder.name.text = contact.name
        holder.phone_number.text = contact.phone_number
        holder.arrow.setOnClickListener {
            Snackbar.make(holder.itemView, "${holder.name.text} clicked", Snackbar.LENGTH_LONG)
                .setBackgroundTint(holder.name.context.getColor(R.color.colorAccent))
                .show()
            dialog.displayEditContactDialog(holder.name.context, contact.phone_number, contact.name)?.show()
        }
    }

    fun setFriendList (friendList: ArrayList<Friend>){
        this.friendList = friendList
        notifyDataSetChanged()
    }
}