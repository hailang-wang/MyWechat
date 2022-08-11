package com.hikvision.mykotlintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

import android.widget.TextView
import androidx.annotation.CallSuper
import com.hikvision.mykotlintest.database.dao.UserDao
import com.hikvision.mykotlintest.database.database.AppDatabase
import com.hikvision.mykotlintest.recycle.NewsMainActivity
import com.hikvision.mykotlintest.util.KeyboardSetting
import com.hikvision.mykotlintest.util.KeyboardUtils


class MainActivity : AppCompatActivity() {
        var count = 0;
        var showPassword = false
        //private lateinit var password:EditText
        lateinit var name:EditText
        lateinit var btnGoToListView:Button
        lateinit var btnGoToFruitView:Button
        lateinit var btnGoToNewsRecyclerView:Button
        lateinit var btnGoToDatabasePractice:Button
        lateinit var btnGoToKeyboard:Button

        lateinit var btnGoToRegister:Button

        private lateinit var userDao:UserDao
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)
                name =findViewById<EditText>(R.id.textOfName)
                name.setText(intent?.getStringExtra("username"))
                //创建数据库,获取到userDao!
               // createUserDao()
                userDao=AppDatabase.getDatabaseSingleton2(this).userDao()

                btnGoToListView=findViewById(R.id.goToListView)
                btnGoToListView.setOnClickListener {
                        startActivity(Intent(this,MyListViewTest::class.java))
                }

                btnGoToFruitView=findViewById(R.id.goToFruitListView)
                btnGoToFruitView.setOnClickListener {
                        startActivity(Intent(this,FruitListMain::class.java))
                }

                btnGoToNewsRecyclerView=findViewById(R.id.goToNewsRecyclerView)
                btnGoToNewsRecyclerView.setOnClickListener {
                        startActivity(Intent(this,NewsMainActivity::class.java))
                }

                btnGoToDatabasePractice=findViewById(R.id.goToDatabasePractice)
                btnGoToDatabasePractice.setOnClickListener {
                        startActivity(Intent(this,DataBaseActivity::class.java))
                }

                //自定义键盘测试
                btnGoToKeyboard=findViewById(R.id.goToKeyBoard)
                btnGoToKeyboard.setOnClickListener {
                        //点击跳转到其他的界面去处理自定义的键盘
                        startActivity(Intent(this,KeyboardActivity::class.java))
                }
                //这里需要想恢复出用户名
                btnGoToRegister=findViewById(R.id.goToRegister)
                btnGoToRegister.setOnClickListener {
                        startActivityForResult(Intent(this,RegisterActivity::class.java),1)
                }

        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                name =findViewById<EditText>(R.id.textOfName)
                when(requestCode){
                        1->{
                                if(resultCode== RESULT_OK){
                                        var nameFromRegister=data?.getStringExtra("username")
                                        name.setText(nameFromRegister)
                                }
                        }
                }
        }

        fun sureBtn1(view: View) {
                var name =findViewById<TextView>(R.id.textOfName)
                var password = findViewById<EditText>(R.id.textOfPassWord)
                var message = findViewById<TextView>(R.id.mess)
                var forgetPW=findViewById<TextView>(R.id.forgetPW)
                /*
                * 需要变化的资源，希望从一个锁定的状态变成一个解锁的状态，然后又可以点击后面的眼睛按钮，使密码进行显示
                * */
                var lockOpen = getDrawable(R.drawable.ic_baseline_lock_open_24)

                var text = name.text.toString()
                var text1 =password.text.toString()

                if (userDao.queryUserByName(text,text1)!=null) {
                        // message.setText("密码或用户名为$text+$text1")
                        //设置显示的位置，前面两个参数是左上角的位置，后面两个参数是右上角的位置
                        lockOpen?.setBounds(0, 0, 40, 40)
                        password.setCompoundDrawables(lockOpen, null, null, null)
                        var intent = Intent(this@MainActivity, ThanksActivity::class.java)
                        intent.putExtra("name", text)
                        //传递参数到另一个界面上
                        startActivity(intent)
                } else {
                        count++
                        if (count < 10) {
                                message.text="密码或用户名有误,你还有${10 - count}次机会"
                                password.setText("")
                                forgetPW.text="忘记密码？点击找回"
                        } else {
                                message.text="已锁定，请在10分钟后重试"
                        }
                }
        }

        fun forgetPassWord(view: View) {
                var intent=Intent(this,ThankAgain::class.java)
                startActivity(intent)
        }

        fun showPassword(view: View) {
                var password = findViewById<EditText>(R.id.textOfPassWord)

                var eye = getDrawable(R.drawable.ic_baseline_remove_red_eye_24)
                var close = getDrawable(R.drawable.ic_baseline_visibility_off_24)

                var eyeSet=findViewById<ImageView>(R.id.showPassword)
                showPassword = !showPassword
                if (showPassword) {

                        password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        password.setSelection(password.text.length)
                        eyeSet.setImageDrawable(eye)
                        //message.setText("点击一下，此时被点击是要显示明文")
                } else {
                        password.transformationMethod =PasswordTransformationMethod.getInstance()
                        //message.setText("有点击一下，需要显示密文")
                        password.setSelection(password.text.length)
                        eyeSet.setImageDrawable(close)
                }
        }


        //实现了点击空白处回收键盘的缺陷
        @CallSuper
        override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
                if (ev.action == MotionEvent.ACTION_DOWN) {
                        val view = currentFocus
                        //打印出x和y的坐标位置
                        if (KeyboardUtils.isShouldHideKeyBord(view, ev)) {
                                KeyboardUtils.hintKeyBoards(view)
                        }
                }
                return super.dispatchTouchEvent(ev)
        }



}
