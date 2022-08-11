package com.hikvision.mykotlintest.database.dao

import androidx.room.*
import com.hikvision.mykotlintest.database.entity.Friend

@Dao
interface FriendDao {
        //按照姓名查询friend
        @Query("select * from Friend where name=:name")
        fun queryByName(name:String):Friend

        //增加friend
        @Insert
        fun insertFriend(friend:Friend):Long

        //删除friend
        @Query("delete from Friend where name=:name")
        fun deleteByName(name:String):Int



        //查询所有的用户
        @Query("select * from Friend")
        fun queryAll():List<Friend>

       //修改friend的备注信息等
        @Update
        fun updateFriend(friend: Friend):Int

}