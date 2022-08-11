package com.hikvision.mykotlintest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hikvision.mykotlintest.database.dao.FriendDao
import com.hikvision.mykotlintest.database.database.AppDatabase
import com.hikvision.mykotlintest.database.entity.Friend
import kotlinx.coroutines.launch

class FriendAddActivity:AppCompatActivity() {


        private lateinit var friendName:EditText
        private lateinit var friendGender:EditText
        private lateinit var friendNickName:EditText
        private lateinit var friendwechatNum:EditText
        private lateinit var friendAddress:EditText
        private lateinit var friendAvatar:EditText

        private lateinit var addFriend:Button
        private lateinit var returnAddress:Button
        private lateinit var friendDao: FriendDao

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.address_add_friend)
                initValue()
                addFriend.setOnClickListener {
                        var name=friendName.text.toString()
                        var nickname=friendNickName.text.toString()
                        var sex=Integer.valueOf(friendGender.text.toString())
                        var num=friendwechatNum.text.toString()
                        var address=friendAddress.text.toString()

                        //这里只填123吧
                        var image=Integer.valueOf(friendAvatar.text.toString())
                        var friend= Friend(name,sex,nickname,num,address,image)

                        var result:Long=0
                        lifecycleScope.launch {
                                result=friendDao.insertFriend(friend)
                        }

                        if(result>0){
                                Toast.makeText(this,"朋友添加成功",Toast.LENGTH_SHORT).show()
                        }else{
                                Toast.makeText(this,"朋友添加失败",Toast.LENGTH_SHORT).show()
                        }

                }

                //返回按钮
                returnAddress.setOnClickListener {

                        //此时应该回到通讯路的界面，并且将此界面再次刷新


                        finish()
                }
        }
        fun initValue(){
                friendName=findViewById(R.id.textOfFriendName)
                friendNickName=findViewById(R.id.textOfFriendNickName)
                friendGender=findViewById(R.id.textOfFriendGender)
                friendwechatNum=findViewById(R.id.textOfFriendWechatNum)
                friendAddress=findViewById(R.id.textOfFriendAddress)
                friendAvatar=findViewById(R.id.textOfFriendAvatar)
                addFriend=findViewById(R.id.addFriend)

                returnAddress=findViewById(R.id.returnAddress)
                friendDao=AppDatabase.getDatabaseSingleton2(this).friendDao()
        }
}