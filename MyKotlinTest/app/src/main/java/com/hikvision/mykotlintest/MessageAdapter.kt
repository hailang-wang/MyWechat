package com.hikvision.mykotlintest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.util.UserMessage

class MessageAdapter(var users:List<UserMessage>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
        //在InnerClass中获取到item中的资源
        inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
                var userImage:ImageView=view.findViewById(R.id.userImage)
                var userName:TextView=view.findViewById(R.id.username)
                var userMessage:TextView=view.findViewById(R.id.userMessage)
        }
        //在第一个方法中开始获取到item自定义的布局视图,将布局传入到内部类中，获取到自定义布局的元素
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                var view=LayoutInflater.from(parent.context).inflate(R.layout.message_list_item,parent,false)
                return ViewHolder(view)
        }
        //开始将自定义布局中的元素赋值
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                var user=users[position]
                holder.userImage.setImageResource(user.image)
                holder.userName.text=user.name
                holder.userMessage.text=user.message
        }
        //返回中的条目数
        override fun getItemCount(): Int {
                return users.size
        }


}