package com.hikvision.mykotlintest

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hikvision.mykotlintest.database.dao.MessageDao
import com.hikvision.mykotlintest.database.database.AppDatabase
import com.hikvision.mykotlintest.entity.Messages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SendMessageActivity : AppCompatActivity() {

        private lateinit var friendTitle: TextView


        private lateinit var sendMessageContent: EditText
        private lateinit var sendMessageType: EditText

        private lateinit var addMessage: Button
        private lateinit var returnMessage: Button

        private lateinit var messageDao: MessageDao
        private lateinit var curFriendName:String
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.message_add)
                initValue()
                messageDao = AppDatabase.getDatabaseSingleton2(this).messageDao()
                addMessage.setOnClickListener {

                        var message = Messages(
                                curFriendName,
                                sendMessageContent.text.toString(),
                                Integer.valueOf(sendMessageType.text.toString())
                        )

                        var result: Long = 0
                        lifecycleScope.launch(Dispatchers.IO) {
                                result = messageDao.insertMessage(message)
                                if (result > 0) {
                                        withContext(Dispatchers.Main){
                                                Toast.makeText(
                                                        this@SendMessageActivity,
                                                        "消息${sendMessageContent.text}插入成功",
                                                        Toast.LENGTH_SHORT
                                                ).show()
                                        }
                                } else {
                                        withContext(Dispatchers.Main){
                                                Toast.makeText(
                                                        this@SendMessageActivity,
                                                        "消息${sendMessageContent.text}插入失败",
                                                        Toast.LENGTH_SHORT
                                                ).show()
                                        }
                                }
                        }
                }
                returnMessage.setOnClickListener {

                        //这里需要更新前一个界面的显示
                        finish()

                }
        }

        fun initValue() {
                 curFriendName=intent.getStringExtra("curFriendName").toString()
                friendTitle = findViewById(R.id.friendTitle)
                friendTitle.text =curFriendName


                sendMessageContent = findViewById(R.id.sendMessageContent)
                sendMessageType = findViewById(R.id.sendMessageType)

                addMessage = findViewById(R.id.addMessage)
                returnMessage = findViewById(R.id.returnMessage)


        }
}