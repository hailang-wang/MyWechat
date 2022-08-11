package com.hikvision.mykotlintest.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hikvision.mykotlintest.database.dao.FriendDao
import com.hikvision.mykotlintest.database.dao.MessageDao

import com.hikvision.mykotlintest.database.dao.UserDao
import com.hikvision.mykotlintest.database.entity.Address
import com.hikvision.mykotlintest.database.entity.Friend
import com.hikvision.mykotlintest.database.entity.User
import com.hikvision.mykotlintest.entity.Messages

@Database(version = 4, entities = [User::class,Friend::class,Messages::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao

        //abstract fun addressDao(): AddressDao

        abstract fun friendDao():FriendDao

        abstract fun messageDao():MessageDao

        companion object {
                private var instance: AppDatabase? = null

                //新增表时只需要将Migrate重写，然后重写方法，migrate,
              /* private var Migrate_1_2=object : Migration(1,2){
                        override fun migrate(database: SupportSQLiteDatabase) {
                                database.execSQL("create table Address (id integer primary key autoincrement not null, name text not null, image text not null)" )
                        }

                }*/
                //新增一个friend表，用来在address界面点击friendList时，展现出friend的具体信息是什么
               private var Migrate_2_3=object : Migration(2,3){
                        override fun migrate(database: SupportSQLiteDatabase) {
                                database.execSQL("create table Friend(id integer primary key autoincrement not null, name text not null,gender integer not null,nickname text not null," +
                                                "wechatNum text not null,address text not null,avatar integer not null)" )
                        }

                }

                //新增了一个消息表。里面存放了和所有人的消息，根据用户的姓名和类别区分，如果是0.说明是发送的消息，如果是1，则是接收的消息
               private var Migrate_3_4=object : Migration(3,4){
                        override fun migrate(database: SupportSQLiteDatabase) {
                                database.execSQL("create table Messages(id integer primary key autoincrement not null, name text not null,message text not null,type integer not null)" )
                        }

                }

                //kotlin的正规双重校验锁写法
                fun getDatabaseSingleton2(context: Context): AppDatabase =
                        instance ?: synchronized(AppDatabase::class.java) {
                                instance ?: Room.databaseBuilder(
                                        context.applicationContext,
                                        AppDatabase::class.java,
                                        "app__lang_database"
                                ).addMigrations( Migrate_2_3, Migrate_3_4).allowMainThreadQueries().build().also {
                                        instance = it
                                }
                        }


                //返回一个数据库实例，单利模式，如果没创建过，就新创建一个名字为app_database的数据库
                @Synchronized
                fun getDatabase(context: Context): AppDatabase {
                        instance?.let {
                                return it
                        }
                        return Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "app__lang_database"
                        ).allowMainThreadQueries()
                                .build().apply {
                                        instance = this
                                }
                }

                //写一个双重校验的单利模式
                fun getDatabaseSingleton(context: Context): AppDatabase {
                        if (instance == null) {
                                synchronized(AppDatabase::class.java) {
                                        if (instance == null) {
                                                instance = Room.databaseBuilder(
                                                        context.applicationContext,
                                                        AppDatabase::class.java,
                                                        "app__lang_database"
                                                ).allowMainThreadQueries().build()
                                                        .apply { instance = this }
                                        }
                                }
                        }
                        return instance!!
                }
        }
}
