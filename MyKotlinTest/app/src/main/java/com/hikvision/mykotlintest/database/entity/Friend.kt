package com.hikvision.mykotlintest.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Friend(var name:String,var gender:Int,var nickname:String,var wechatNum:String,var address:String,var avatar:Int) {
        @PrimaryKey(autoGenerate = true)
        var id:Long=0

        override fun toString(): String {
                return "当前朋友的姓名为：${name},年龄为${gender},昵称为:${nickname},微信号为：${wechatNum},地址为：${address}"
        }
}