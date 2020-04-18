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
import com.lovisgod.safehaven.databinding.SafetyTipItemBinding
import com.lovisgod.safehaven.model.Contact
import com.lovisgod.safehaven.model.SafetyTip

class SafetyTipsListAdapter: RecyclerView.Adapter<SafetyTipsListAdapter.Viewholder>() {
    private var tipsList: ArrayList<SafetyTip> = ArrayList()
    val dialog  = Dialog()

    class Viewholder(itemView: SafetyTipItemBinding): RecyclerView.ViewHolder(itemView.root) {
        val image = itemView.tipImage
        val title = itemView.tipTitle
        val category = itemView.tipCategory
        val time_ = itemView.tipTime
        val layout = itemView.item
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SafetyTipsListAdapter.Viewholder {
        val itemView: SafetyTipItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.safety_tip_item, parent, false)
        return Viewholder(itemView)
    }

    override fun getItemCount(): Int {
        if(tipsList.isNotEmpty()){
            return tipsList.size
        }
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: SafetyTipsListAdapter.Viewholder, position: Int) {
        val tip = tipsList.get(position)
        holder.title.text = tip.title
        holder.category.text = tip.category
        holder.time_.text = tip.time

        holder.layout.setOnClickListener {
            Snackbar.make(holder.itemView, "${holder.title.text} clicked", Snackbar.LENGTH_LONG)
                .setBackgroundTint(holder.layout.context.getColor(R.color.colorAccent))
                .show()
        }
    }

    fun setAdvertList (tipsList: ArrayList<SafetyTip>){
        this.tipsList = tipsList
        println("this is ${tipsList}")
        notifyDataSetChanged()
    }
}