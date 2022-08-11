package com.hikvision.mykotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.database.dao.FriendDao
import com.hikvision.mykotlintest.database.dao.MessageDao
import com.hikvision.mykotlintest.database.database.AppDatabase
import com.hikvision.mykotlintest.entity.Messages
import com.hikvision.mykotlintest.entity.UserMessage

class MessageActivity : Fragment() {
        private lateinit var users: ArrayList<UserMessage>
        //根据消息列表查找出当前最新的消息
        private lateinit var messageDao: MessageDao
        //根据消息的姓名，来获取到发送人的图片信息
        private lateinit var friendDao: FriendDao

        lateinit var mview: View


        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View {

                mview= inflater.inflate(R.layout.message_fragment, container, false)

                var recyclerView = mview.findViewById<RecyclerView>(R.id.messageList)
                messageDao= AppDatabase.getDatabaseSingleton2(requireContext()).messageDao()
                friendDao=AppDatabase.getDatabaseSingleton2(requireContext()).friendDao()
                initValues()
                //应该添加用户，在哪添加朋友啊
                var adapter = MessageAdapter(users)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = adapter

                return mview
        }

        override fun onHiddenChanged(hidden: Boolean) {
                //如果没有隐藏，就直接处理和展示数据库中的数据
                if(!hidden){

                        var recyclerView = mview.findViewById<RecyclerView>(R.id.messageList)
                        messageDao= AppDatabase.getDatabaseSingleton2(requireContext()).messageDao()
                        friendDao=AppDatabase.getDatabaseSingleton2(requireContext()).friendDao()
                        initValues()
                        //应该添加用户，在哪添加朋友啊
                        var adapter = MessageAdapter(users)
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = adapter
                }

                super.onHiddenChanged(hidden)
        }

        fun initValues() {
                users = ArrayList()
                var messages=messageDao.queryLatestMessage()
                if(messages!=null){
                        for(message in messages){
                                var friend= friendDao.queryByName(message.name)
                                var avatar=friend.avatar
                                var image=when(avatar){
                                        1->{R.drawable.view1}
                                        2->{R.drawable.dou2}
                                        3->{R.drawable.dou4}
                                        else->{R.drawable.flow1}
                                }
                                //根据朋友的图片数字标识，找到不同的图片
                                users.add(UserMessage(image,message.name,message.message))
                        }
                }

        }
}