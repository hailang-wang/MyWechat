package com.hikvision.mykotlintest.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//这张表用来存储和所有朋友的聊天信息，根据朋友的姓名查询即可。然后根据type来区分是发送的消息还是接收的消息

@Entity
class Messages(var name:String,var message:String,var type:Int) {
        @PrimaryKey(autoGenerate = true)
        var id:Long=0
        companion object{
                const val TYPE_SEND=0
                const val TYPE_RECEIVE=1
        }
}