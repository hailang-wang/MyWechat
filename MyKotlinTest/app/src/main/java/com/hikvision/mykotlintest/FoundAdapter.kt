package com.hikvision.mykotlintest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.util.Found

class FoundAdapter(var founds:List<Found>):RecyclerView.Adapter<FoundAdapter.ViewHolder>() {
        //这个静态内部类要继承主的静态内部类
        inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
                var image:ImageView=view.findViewById(R.id.foundItemImage)
                var name:TextView=view.findViewById(R.id.foundItemName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.found_list_item,parent,false)
                return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                var found=founds[position]
                holder.image.setImageResource(found.image)
                holder.name.text=found.name
        }

        override fun getItemCount(): Int {
                return founds.size
        }
}