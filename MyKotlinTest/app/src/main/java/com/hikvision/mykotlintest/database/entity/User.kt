package com.hikvision.mykotlintest.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(var username: String, var password: String) {
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0

        override fun toString(): String {
                return "id为：${id}用户名为：$username:密码为$password"
        }
}