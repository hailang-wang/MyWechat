package com.hikvision.mykotlintest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.util.Address

class AddressAdapter(var addresses:List<Address>):RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
        //在此处获得自定义的组件
        inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
                var image:ImageView=view.findViewById(R.id.addressItemImage)
                var name:TextView=view.findViewById(R.id.addressItemName)
        }
        //将需要加载的自定义的list引入
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                var view=LayoutInflater.from(parent.context).inflate(R.layout.addredd_list_item,parent,false)
               return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                //将数据进行重新的设置
                var address=addresses[position]
                holder.image.setImageResource(address.image)
                holder.name.text=address.name
        }

        override fun getItemCount(): Int {
                return addresses.size
        }
}