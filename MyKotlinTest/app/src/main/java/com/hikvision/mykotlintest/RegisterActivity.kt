package com.hikvision.mykotlintest

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.hikvision.mykotlintest.database.dao.UserDao
import com.hikvision.mykotlintest.database.database.AppDatabase
import com.hikvision.mykotlintest.database.entity.User
import com.hikvision.mykotlintest.util.KeyboardUtils

class RegisterActivity : AppCompatActivity() {
        /*获取到所有的界面按钮
        * */
        private lateinit var name: EditText
        private lateinit var password1: EditText
        private lateinit var password2: EditText
        private lateinit var eye1: ImageView
        private lateinit var eye2: ImageView
        private lateinit var errorMess: TextView
        private lateinit var register: Button
        private lateinit var login: Button

        private var flag1=false
        private var flag2=false
        private var success=false

        private lateinit var userDao: UserDao

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.register_activity)



                name = findViewById<EditText>(R.id.username)
                password1 = findViewById<EditText>(R.id.register1PW)
                password2 = findViewById<EditText>(R.id.register2PW)
                eye1 = findViewById<ImageView>(R.id.eyeOpen1)
                eye2 = findViewById<ImageView>(R.id.eyeOpen2)
                errorMess = findViewById<TextView>(R.id.errorMess)
                register = findViewById<Button>(R.id.register)
                login = findViewById<Button>(R.id.login)

                var eye1Open=getDrawable(R.drawable.ic_baseline_visibility_off_24)
                var eye1Close=getDrawable(R.drawable.ic_baseline_remove_red_eye_24)

                userDao=AppDatabase.getDatabaseSingleton2(this).userDao()
               //准备好数据库和借口到dao实例
                //createUserDao()

                eye1.setOnClickListener {
                        flag1=!flag1
                        //此时显示明文
                        if(flag1){
                                eye1.setImageDrawable(eye1Open)
                                password1.transformationMethod=HideReturnsTransformationMethod.getInstance()
                                password1.setSelection(password1.text.length)
                        }else{
                                eye1.setImageDrawable(eye1Close)
                                password1.transformationMethod=PasswordTransformationMethod.getInstance()
                                password1.setSelection(password1.text.length)
                        }
                }

                eye2.setOnClickListener {
                        flag2=!flag2
                        //此时显示明文
                        if(flag2){
                                eye2.setImageDrawable(eye1Open)
                                password2.transformationMethod=HideReturnsTransformationMethod.getInstance()
                                password2.setSelection(password2.text.length)
                        }else{
                                eye2.setImageDrawable(eye1Close)
                                password2.transformationMethod=PasswordTransformationMethod.getInstance()
                                password2.setSelection(password2.text.length)
                        }
                }

                register.setOnClickListener {
                       if(name.text.length===0){
                               errorMess.text="用户名不能为空!"
                       }else if(password1.text.toString()!=password2.text.toString()){
                               errorMess.text="两次密码输入不一致！"
                       }else{
                               var username=name.text.toString()
                               if(userDao.queryUserByName(username)!=null){
                                       errorMess.text="用户名已存在！"
                               }else{
                                       success=true
                                       var password=password1.text.toString()
                                       var user=User(username,password)
                                       userDao.insertUser(user)
                                       errorMess.text="密码设置成功，请点击登录按钮去登录吧！"
                               }
                       }
                }
                /*点击之后，将错误提示信息关闭掉*/
                name.setOnClickListener{
                        errorMess.text=""
                }
                password1.setOnClickListener{
                        errorMess.text=""
                }
                password2.setOnClickListener{
                        errorMess.text=""
                }

                login.setOnClickListener {

                        var intent=Intent()
                        intent.putExtra("username",name.text.toString())
                        setResult(RESULT_OK,intent)
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                }
        }

        @CallSuper
        override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
                if (ev.action == MotionEvent.ACTION_DOWN) {
                        val view = currentFocus
                        if (KeyboardUtils.isShouldHideKeyBord(view, ev)) {
                                KeyboardUtils.hintKeyBoards(view)
                        }
                }
                return super.dispatchTouchEvent(ev)
        }


}