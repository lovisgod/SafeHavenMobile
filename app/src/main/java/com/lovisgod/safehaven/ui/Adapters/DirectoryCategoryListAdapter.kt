package com.lovisgod.safehaven.ui.Adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.lovisgod.safehaven.R
import kotlinx.android.synthetic.main.directory_category_item.view.*

class DirectoryCategoryListAdapter(context: Context): BaseAdapter() {
    var categories = arrayOf("Report Child Abuse", "Report Sexual Abuse/Rape", "Contact Pro bono lawyer",
        "Police Rapid Response Teams")
    private val mInflator: LayoutInflater = LayoutInflater.from(context)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = this.mInflator.inflate(R.layout.directory_category_item, parent, false)
        val holder = ViewHolder(view)
        view.tag = holder
        holder.label.text = categories[position]
        view.setOnClickListener {
            Snackbar.make(holder.label, "${holder.label.text} clicked", Snackbar.LENGTH_LONG)
                .setBackgroundTint(view.context.getColor(R.color.colorAccent))
                .show()
            Navigation.findNavController(view).navigate(R.id.action_directoryFragment_to_directoryListFragment)
        }
        return  view
    }

    override fun getItem(position: Int): Any {
        return categories[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return categories.size
    }

    private class ViewHolder(row: View?) {
        val label: TextView = row?.findViewById<TextView>(R.id.category_title)!!
    }
}