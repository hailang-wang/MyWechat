package com.hikvision.mykotlintest.entity

//这个实体类是当点击消息界面时，在聊天框展示的数据
class WeChatMessage(var message:String,var friendImage:Int,val type:Int){

        companion object{
                const val TYPE_SEND=0
                const val TYPE_RECEIVE=1
        }
}