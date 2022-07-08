package com.hikvision.mykotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.util.UserMessage

class MessageActivity : Fragment() {
        private lateinit var users:ArrayList<UserMessage>
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

                var view= inflater.inflate(R.layout.message_fragment,container,false)
                var recyclerView=view.findViewById<RecyclerView>(R.id.messageList)
                initValues()
                var adapter=MessageAdapter(users)
                recyclerView.layoutManager=LinearLayoutManager(context)
                recyclerView.adapter=adapter
                return view

        }
        fun initValues(){
                users= ArrayList()
                for(i in 1..200){
                        var image=0
                        //仅仅是为了好区分
                        if(i%3==0){
                                image=R.drawable.ic_baseline_lock_24
                        }else if(i%3==1){
                                image=R.drawable.ic_baseline_person_24
                        }else{
                                image=R.drawable.ic_baseline_engineering_24
                        }
                        var name="好友第${i}号"
                        var message="发送消息的内容是：我是第${i}个好友"
                        var user=UserMessage(image,name,message)
                        users.add(user)
                }
        }
}