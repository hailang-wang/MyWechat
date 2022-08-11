package com.hikvision.mykotlintest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.*
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.database.dao.FriendDao
import com.hikvision.mykotlintest.database.dao.MessageDao
import com.hikvision.mykotlintest.database.database.AppDatabase
import com.hikvision.mykotlintest.entity.Messages
import com.hikvision.mykotlintest.entity.WeChatMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatActivity : AppCompatActivity() {
        private lateinit var messages: ArrayList<WeChatMessage>
        private lateinit var messagesFromDatabase: ArrayList<Messages>

        private lateinit var backToBeforeActivity: ImageView

        private lateinit var friendNameTitle: TextView
        private lateinit var weChatMessageSendBtn: Button
        private lateinit var input: EditText
       private lateinit var layout_edit: LinearLayout
        private lateinit var virtualFriend: Button


        private lateinit var weChatAdapter: WeChatAdapter

        //从消息中传过来的数据
        private var friendName: String = ""

        private lateinit var friendDao: FriendDao

        private lateinit var messageList: RecyclerView

        //根据messageDao查出来的数据，进行再度解析，message就直接给message赋值，然后如果Type=0则是发送的消息，然后再根据当前登录用户名的姓名，查出当前的头像，进行展示
        // ，如果是1，则说明是接收的消息，需要根据friend的姓名查出头像进行展示，重新赋值给WeChatMessage类中，然后放到集合里再给adapter进行展示
        private lateinit var messageDao: MessageDao


        private var handler: Handler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        when (msg.what) {
                                0 -> {
                                        messageList.scrollToPosition(messages.size - 1)
                                }
                        }
                }
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.chat_layout)
                initValus()
                //然后从用户的信息表中获取到头像，然后从
                messageList = findViewById(R.id.weChatMessage)
                //准备好数据
                //为其榜上Adapter布局
                messageList.layoutManager = LinearLayoutManager(this)
                weChatAdapter = WeChatAdapter(messages)
                messageList.adapter = weChatAdapter
                //点击这个按钮时，需要去做插入信息的操作，也就是将message给实例化一个对象，将对象WeChatMessage来写到list中

                messageList.scrollToPosition(messages.size - 1)

                weChatMessageSendBtn.setOnClickListener {
                        sendMess()
                }

                layout_edit.setOnClickListener(View.OnClickListener {
                        input.requestFocus()
                        showSoftInput(this, input)
                        handler.sendEmptyMessageDelayed(0, 250)
                })

                messageList.setOnTouchListener { _, _ ->
                        hideSoftInput(this@ChatActivity, input)
                        false
                }

                //点击这个按钮去插入一条消息，所以要调到一个插入界面
                virtualFriend.setOnClickListener {
                        //调转界面到添加信息上
                        var intent = Intent(this, SendMessageActivity::class.java)
                        intent.putExtra("curFriendName", friendName)
                        startActivity(intent)
                }

                backToBeforeActivity.setOnClickListener {
                        hideSoftInput(this@ChatActivity, input)
                        finish()
                }
        }

        fun showSoftInput(context: Context, view: View) {
                var imm =
                        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }

        fun hideSoftInput(context: Context, view: View) {
                var imm =
                        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun sendMess() {
                //这里需要对当前数据进行封装，然后将数据插入到数据库中，然后将数据展示出来
                //此时type肯定是0，因为是发送按钮
                var type = 0;
                var currMessageDetail = input.text.toString()
                var curName = friendName
                var currMessage = Messages(curName, currMessageDetail, type)

                var result: Long = 0

                lifecycleScope.launch(Dispatchers.IO){
                        result = messageDao.insertMessage(currMessage)
                        if (result > 0) {
                                //跳转到主界面去显示
                                withContext(Dispatchers.Main){
                                        Toast.makeText(this@ChatActivity, "用户信息插入成功", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                                withContext(Dispatchers.Main){
                                        Toast.makeText(this@ChatActivity, "用户信息插入失败", Toast.LENGTH_SHORT).show()
                                }
                        }
                }

                input.text = null

                //插入到数据库之后，就可以让他去展示更新到list中
                messages.add(WeChatMessage(currMessageDetail, R.drawable.dou2, 0))
                //指定插入的位置，应该是要放在最后一个位置
                weChatAdapter.notifyItemInserted(messages.size-1)
                messageList.scrollToPosition(messages.size - 1)
        }

        fun initValus() {

                //需要在主界面中引入RecyclerView，也就是设置初始化参数和对Adapter进行适配，所以需要去继承RecyclerView，写适配类
                friendNameTitle = findViewById(R.id.friendName)
                weChatMessageSendBtn = findViewById(R.id.weChatMessageSendBtn)
                input = findViewById(R.id.input)
                layout_edit = findViewById(R.id.layout_edit)

                virtualFriend = findViewById(R.id.virtualFriend)

                backToBeforeActivity = findViewById(R.id.backToBeforeActivity)

                friendName = intent.getStringExtra("friendName").toString()

                friendNameTitle.text = friendName
                //初始化数据库！
                friendDao = AppDatabase.getDatabaseSingleton2(this).friendDao()
                messageDao = AppDatabase.getDatabaseSingleton2(this).messageDao()
                //这些是从数据库里查出来的额，然后将这些值重新赋值给WeChatMessage
                lifecycleScope.launch {
                        messagesFromDatabase =
                                messageDao.queryAllByFriendName(friendName) as ArrayList<Messages>
                }

                //新建一个数据来保存所要展示的数据
                messages = ArrayList();

                if (messagesFromDatabase != null) {
                        for (mess in messagesFromDatabase) {
                                //如果消息的类型是1，则是接收的消息，需要查询朋友的信息，获取到朋友的头像
                                if (mess.type == 1) {
                                        //此时是接收消息，根据姓名查到图片，然后根据类型赋值到当前类型
                                        var avatarType = friendDao.queryByName(friendName).avatar

                                        var avatar = when (avatarType) {
                                                1 -> {
                                                        R.drawable.view1
                                                }
                                                2 -> {
                                                        R.drawable.dou2
                                                }
                                                3 -> {
                                                        R.drawable.dou4
                                                }
                                                else -> {
                                                        R.drawable.flow1
                                                }
                                        }

                                        messages.add(WeChatMessage(mess.message, avatar, mess.type))
                                        //否则就是自己发送的消息，只需要穿自己的头像即可
                                } else {
                                        messages.add(
                                                WeChatMessage(
                                                        mess.message,
                                                        R.drawable.dou2,
                                                        mess.type
                                                )
                                        )
                                }
                        }
                }
        }

}