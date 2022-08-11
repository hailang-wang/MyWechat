package com.hikvision.mykotlintest


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.entity.WeChatMessage

class WeChatAdapter(var friends: List<WeChatMessage>) :
        RecyclerView.Adapter<WeChatAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                var image: ImageView = view.findViewById(R.id.chat_item_userImage)
                var mess: TextView = view.findViewById(R.id.chat_item_text)
        }

        //返回数据的类型，是发送还是接收，然后根据发送接收标识，来创建不同的view
        override fun getItemViewType(position: Int): Int {
                var friend = friends[position]
                return friend.type
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                //根据返回的不同的type,来绑定不同的自定义的列表样式
                var view: View = when (viewType) {
                        WeChatMessage.TYPE_SEND -> {
                                LayoutInflater.from(parent.context).inflate(
                                        R.layout.chat_recycleview_item_right_send,
                                        parent,
                                        false
                                )
                        }
                        WeChatMessage.TYPE_RECEIVE -> {
                                LayoutInflater.from(parent.context).inflate(
                                        R.layout.chat_recyclerview_item_left_receive,
                                        parent,
                                        false
                                )
                        }
                        else -> {
                                LayoutInflater.from(parent.context).inflate(
                                        R.layout.chat_recyclerview_item_left_receive,
                                        parent,
                                        false
                                )
                        }
                }
                return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                //这里需要绑定那个那个自己定义的item
                var friend = friends[position]
                holder.image.setImageResource(friend.friendImage)
                holder.mess.text = friend.message
        }

        override fun getItemCount(): Int {
                return friends.size
        }




}