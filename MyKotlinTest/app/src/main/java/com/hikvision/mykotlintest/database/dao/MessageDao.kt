package com.hikvision.mykotlintest.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hikvision.mykotlintest.entity.Messages

@Dao
interface MessageDao {
        //根据用户的姓名查询所有的聊天记录
        @Query("select * from Messages where name=:friendName")
        fun queryAllByFriendName(friendName:String):List<Messages>

        //插入一条聊天记录当成功时会返回一条整数
        @Insert
        fun insertMessage(message:Messages):Long

        //根据用户一直点击聊天记录之后，然后弹出删除选项，将点击的选项重新赋值成一个对象，传入到下面就可以删除当前用户啦
        @Delete
        fun deleteMessage(message:Messages):Int

        //根据姓名分组，找到id最大的那个，然后倒叙排序输出，即可找到最新的朋友发的消息数据
        @Query("select * from Messages where id in (select t.maxId from (select max(id) as maxId from Messages group by name) t ) order by id desc")
        fun queryLatestMessage():List<Messages>
}