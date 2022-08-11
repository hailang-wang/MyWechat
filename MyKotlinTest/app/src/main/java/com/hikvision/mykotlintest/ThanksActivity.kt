package com.hikvision.mykotlintest


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.hikvision.mykotlintest.database.database.AppDatabase
import kotlinx.coroutines.launch

class ThanksActivity : AppCompatActivity() {
        private lateinit var username: String
        //对所有的fragment的activity声明变量
        private lateinit var mainTitle:TextView
        private lateinit var weixintianjia:ImageView

        private  var messageActivity: MessageActivity? = null
        private  var addressActivity: AddressActivity?=null
        private  var foundActivity: FoundActivity?=null
        private  var myActivity: MyActivity?=null

        //对微信的首页的所有元素进行获取
        private lateinit var messagePart:RelativeLayout
        private lateinit var messagePicture:ImageView
        private lateinit var messageText:TextView

        private lateinit var addressPart:RelativeLayout
        private lateinit var addressPicture:ImageView
        private lateinit var addressText:TextView

        private lateinit var foundPart:RelativeLayout
        private lateinit var foundPicture:ImageView
        private lateinit var foundText:TextView

        private lateinit var myPart:RelativeLayout
        private lateinit var myPicture:ImageView
        private lateinit var myText:TextView

        //点击前和点击之后恢复的颜色
        private  var touchColor=0
        private  var backColor=0
        //管理对象，用于
        private lateinit var support:FragmentManager

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.thanks_activity)
                 mainTitle=findViewById(R.id.mainTitle)
                weixintianjia=findViewById(R.id.weixintianjia)
                //为控件进行赋初始值
                initValues()
                support=supportFragmentManager
                changeFragmentAndColor(0)

                var display = findViewById<TextView>(R.id.display)
                var intent = intent
                username = intent.getStringExtra("name").toString()
                display.text="""欢迎您用户名为：【${intent.getStringExtra("name")}】的用户"""
                //初始默认选中第一个message

                //已经初始化好了当前的所有的数，现在需要对案件加上一个监听事件，如果点击了对应的地方，就可以改变fragment
                messagePart.setOnClickListener {
                        changeFragmentAndColor(0)
                }
                addressPart.setOnClickListener {

                        changeFragmentAndColor(1)
                }
                foundPart.setOnClickListener {
                        changeFragmentAndColor(2)
                }
               myPart.setOnClickListener {
                        changeFragmentAndColor(3)
                }

        }
        fun initValues(){
                //信息部分进行初始化
                messagePart=findViewById(R.id.messagePart)
                messagePicture=findViewById(R.id.messagePicture)
                messageText=findViewById(R.id.messageText)
                //通讯录初始化
                addressPart=findViewById(R.id.addressPart)
                addressPicture=findViewById(R.id.addressPicture)
                addressText=findViewById(R.id.addressText)
                //发现初始化
                foundPart=findViewById(R.id.foundPart)
                foundPicture=findViewById(R.id.foundPicture)
                foundText=findViewById<TextView>(R.id.foundText)
                //我的进行初始化
                myPart=findViewById(R.id.mePart)
                myPicture=findViewById(R.id.mePicture)
                myText=findViewById<TextView>(R.id.meText)

                touchColor=getColor(R.color.green)
                backColor=getColor(R.color.white)

        }

        @SuppressLint("ResourceAsColor")
        fun changeFragmentAndColor(num:Int){
                //在每次开始变化fragment之前，先将所有的fragment停止掉,颜色全都改回去
                clearColor()
                //然后开启fragment的转换事务,这里注意使用不过时的manage
                var transaction =support.beginTransaction()
                //将现存的fragment进行隐藏，避免出现多个fragment出现在一个界面上
                hideAllFragments(transaction)
                //开始处理点击事件
                when(num){
                        0->{
                                messagePicture.setBackgroundColor(touchColor)
                                messageText.setTextColor(touchColor)
                                mainTitle.text=messageText.text

                                //控制微信添加按钮的显示
                                weixintianjia.visibility=View.VISIBLE
                                weixintianjia.setImageResource(R.drawable.ic_baseline_add_circle_outline_24)

                                weixintianjia.setOnClickListener {
                                       // startActivity(Intent(this,SendMessageActivity::class.java))
                                        Toast.makeText(this,"此界面将来添加扫一扫群聊等功能",Toast.LENGTH_SHORT).show()
                                }

                                //转换activity
                                if(messageActivity==null){
                                        messageActivity= MessageActivity()
                                        transaction.add(R.id.content, messageActivity!!)

                                }else{
                                        transaction.show(messageActivity!!)
                                }


                        }
                        1->{
                                addressPicture.setBackgroundColor(touchColor)
                                addressText.setTextColor(touchColor)
                                //把图片设为可见
                                weixintianjia.visibility=View.VISIBLE
                                weixintianjia.setImageResource(R.drawable.ic_baseline_person_add_alt_1_24)
                                //给当前的添加按钮添加一个监听事件，用来添加朋友
                                weixintianjia.setOnClickListener {
                                        startActivity(Intent(this,FriendAddActivity::class.java))
                                        //我想销毁当前的fragment，怎么办
                                }

                                mainTitle.text=addressText.text
                                //改变好颜色之后，就进行页面的加载

                                //此时应该是要展示数据，此数据应该在哪里

                                if(addressActivity==null){
                                        addressActivity=AddressActivity()
                                        transaction.add(R.id.content, addressActivity!!)
                                }else{
                                        transaction.show(addressActivity!!)
                                }
                        }
                        2->{
                                foundPicture.setBackgroundColor(touchColor)
                                foundText.setTextColor(touchColor)
                                mainTitle.text=foundText.text
                                if(foundActivity==null){
                                        foundActivity= FoundActivity()
                                        transaction.add(R.id.content,foundActivity!!)
                                }else{
                                        transaction.show(foundActivity!!)
                                }

                        }
                        3->{
                                myPicture.setBackgroundColor(touchColor)
                                myText.setTextColor(touchColor)
                                mainTitle.text=myText.text
                                if(myActivity==null){
                                        myActivity=MyActivity()
                                        transaction.add(R.id.content,myActivity!!)
                                }else{
                                        transaction.show(myActivity!!)
                                }
                        }
                }
                transaction.commit()
        }
        //把颜色改回白色
        @SuppressLint("ResourceAsColor")
        fun clearColor(){

                //隐藏微信添加按钮
                weixintianjia.visibility= View.INVISIBLE
                weixintianjia.setBackgroundColor(R.color.black)

                messagePicture.setBackgroundColor(backColor)
                messageText.setTextColor(backColor)

                addressPicture.setBackgroundColor(backColor)
                addressText.setTextColor(backColor)

                foundPicture.setBackgroundColor(backColor)
                foundText.setTextColor(backColor)

                myPicture.setBackgroundColor(backColor)
                myText.setTextColor(backColor)
        }
        //将所有的fragment都隐藏掉

        fun hideAllFragments(transaction:FragmentTransaction ){
                if(messageActivity!=null){
                        transaction.hide(messageActivity!!)
                }
                if(addressActivity!=null){
                        transaction.hide(addressActivity!!)
                }
               if(foundActivity!=null){
                       transaction.hide(foundActivity!!)
               }
                if(myActivity!=null){
                        transaction.hide(myActivity!!)
                }
        }


}