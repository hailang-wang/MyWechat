package com.hikvision.mykotlintest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hikvision.mykotlintest.database.dao.FriendDao
import com.hikvision.mykotlintest.database.database.AppDatabase
import com.hikvision.mykotlintest.database.entity.Friend
import kotlinx.coroutines.launch

class FriendInfoActivity:AppCompatActivity() {
        private lateinit var friendDao: FriendDao

        private lateinit var friendAvatar:ImageView
        private lateinit var friendGender:ImageView
        private lateinit var friendName:TextView
        private lateinit var friendNickname:TextView
        private lateinit var friendAddress:TextView
        private lateinit var friendWechatNum:TextView

        private lateinit var  currentFriendName:String

        private lateinit var toSend:Button
        private lateinit var friend:Friend
        private lateinit var backToAddress:ImageView
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.address_friend_info_display)
                //根据用户名查出来的数据，将对应的数据查到对应的位置上
                initValue()
               currentFriendName=intent.getStringExtra("friendName").toString()

                lifecycleScope.launch {
                       friend=   friendDao.queryByName(currentFriendName)
                }

                //获取到当前界面的所有控件，然后将数据
                if(friend!=null){
                        var avatar=friend.avatar
                        //根据朋友的图片数字标识，找到不同的图片
                        if(avatar==1){
                                friendAvatar.setImageResource(R.drawable.view1)
                        }else if(avatar==2) {
                                friendAvatar.setImageResource(R.drawable.dou2)
                        }else if(avatar==3){
                                friendAvatar.setImageResource(R.drawable.dou4)
                        }else {
                                friendAvatar.setImageResource(R.drawable.flow1)
                        }
                        //设置朋友的姓名
                        friendName.text=friend.name
                        //设置性别标识
                        if(friend.gender==0){
                                friendGender.setImageResource(R.drawable.ic_baseline_pregnant_woman_24)
                        }else{
                                friendGender.setImageResource(R.drawable.ic_baseline_person_24)

                        }
                        friendNickname.text=friend.nickname
                        friendWechatNum.text=friend.wechatNum
                        friendAddress.text=friend.address
                }

                backToAddress.setOnClickListener {
                        finish()
                }

                //给好友发消息界面
                toSend.setOnClickListener {
                        var intent=Intent(this,ChatActivity::class.java)
                        intent.putExtra("friendName",currentFriendName)
                        startActivity(intent)
                }
        }

        //因为要添加一个menu按钮，所以这里要重写一个方法来启动menu
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
                //先在当前布局中引入menu
                menuInflater.inflate(R.menu.friend_activity_menu,menu)
                return true
        }

        //然后给当前menu里面的元素添加点击事件
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                        R.id.infoSet->{Toast.makeText(this,"信息设置被点击了,然后进行修改好友信息和设置权限的操作",Toast.LENGTH_SHORT).show()}

                        R.id.delete ->{Toast.makeText(this,"删除好友被点击了，直接调用朋友的接口根据姓名删除即可！",Toast.LENGTH_SHORT).show()}
                }
                return true
        }

        fun initValue(){

                friendDao=AppDatabase.getDatabaseSingleton2(this).friendDao()
                friendAddress=findViewById(R.id.friendAddress)
                friendAvatar=findViewById(R.id.friendAvatar)
                friendGender=findViewById(R.id.friendGender)
                friendName=findViewById(R.id.friendName)
                friendNickname=findViewById(R.id.friendNickName)
                friendWechatNum=findViewById(R.id.friendWechatNum)
                toSend=findViewById(R.id.toSend)
                backToAddress=findViewById(R.id.backToAddress)
        }
}