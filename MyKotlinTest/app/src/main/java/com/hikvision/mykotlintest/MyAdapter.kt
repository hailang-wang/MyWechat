package com.hikvision.mykotlintest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.util.My

class MyAdapter(var mys:List<My>):RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
                var image=view.findViewById<ImageView>(R.id.myItemImage)
                var name=view.findViewById<TextView>(R.id.myItemName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.my_item_list,parent,false)
                return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                var my=mys[position]
                holder.image.setImageResource(my.image)
                holder.name.text=my.name
        }

        override fun getItemCount(): Int {
                return mys.size
        }
}