package com.hikvision.mykotlintest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.entity.UserMessage

class MessageAdapter(var users:List<UserMessage>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
        //在InnerClass中获取到item中的资源
        private var image=0;
        inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
                var userImage:ImageView=view.findViewById(R.id.userImage)
                var userName:TextView=view.findViewById(R.id.username)
                var userMessage:TextView=view.findViewById(R.id.userMessage)
        }
        //在第一个方法中开始获取到item自定义的布局视图,将布局传入到内部类中，获取到自定义布局的元素
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                var view=LayoutInflater.from(parent.context).inflate(R.layout.message_list_item,parent,false)
                var viewHolder=ViewHolder(view)
                view.setOnClickListener {
                        //此时应该跳转到聊天的界面,顺便把当前聊天用户的姓名和图片也传到聊天界面
                        //Toast.makeText(parent.context,"点击的聊天对象为${viewHolder.userName.text}",Toast.LENGTH_SHORT).show()
                        var intent=Intent(parent.context,ChatActivity::class.java)
                        intent.putExtra("friendName",viewHolder.userName.text)
                        parent.context.startActivity(intent)
                }
                return viewHolder
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