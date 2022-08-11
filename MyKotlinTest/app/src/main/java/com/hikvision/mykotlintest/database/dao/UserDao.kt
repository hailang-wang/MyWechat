package com.hikvision.mykotlintest.database.dao

import androidx.room.*
import com.hikvision.mykotlintest.database.entity.User

@Dao
interface UserDao {
        @Insert
        fun insertUser(user: User):Long

        @Update
        fun updateUser(user:User):Int

        @Query("select * from User")
        fun queryAllUser():List<User>

        @Delete()
        fun deleteUser(user: User):Int

        @Query("delete from User where username=:name")
        fun deleteByName(name:String):Int

        @Query("select id,username,password from User where username=:name")
        fun queryUserByName(name:String):User

        //验证密码对不对所以需要进行一个按照密码和用户名的查询
        @Query("select id,username,password from User where username=:name and password=:password")
        fun queryUserByName(name:String,password:String):User


}