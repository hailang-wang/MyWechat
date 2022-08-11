package com.hikvision.mykotlintest.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
//为什么要设为地址为字符串类型呢，是因为我想将图片存放在一个文件夹下时，获取的应该是文件的路径！然后根据路径去找资源

class Address(var name:String,var image:String) {

        override fun toString(): String {
                return "当前朋友的姓名=${name}:图片地址=${image}"
        }
}