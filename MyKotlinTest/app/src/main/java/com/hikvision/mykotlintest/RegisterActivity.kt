package com.hikvision.mykotlintest

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
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

                var eye1Open=getDrawable(R.drawable.ic_baseline_remove_red_eye_24)
                var eye1Close=getDrawable(R.drawable.ic_baseline_visibility_off_24)

                var eye2Open=getDrawable(R.drawable.ic_baseline_remove_red_eye_24)
                var eye2Close=getDrawable(R.drawable.ic_baseline_visibility_off_24)

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
                                eye2.setImageDrawable(eye2Open)
                                password2.transformationMethod=HideReturnsTransformationMethod.getInstance()
                                password2.setSelection(password2.text.length)
                        }else{
                                eye2.setImageDrawable(eye2Close)
                                password2.transformationMethod=PasswordTransformationMethod.getInstance()
                                password2.setSelection(password2.text.length)
                        }
                }

                register.setOnClickListener {
                       if(name.text.length===0){
                               errorMess.setText("用户名不能为空!")
                       }else if(password1.text.toString()!=password2.text.toString()){
                               errorMess.setText("两次密码输入不一致！")
                       }else{
                               success=true
                               errorMess.setText("密码设置成功，请点击登录按钮去登录吧！")

                       }
                }
                /*点击之后，将错误提示信息关闭掉*/
                name.setOnClickListener{
                        errorMess.setText("")
                }
                password1.setOnClickListener{
                        errorMess.setText("")
                }
                password2.setOnClickListener{
                        errorMess.setText("")
                }

                login.setOnClickListener {
                        startActivity(Intent(this,MainActivity::class.java))

                }
        }

        @CallSuper
        override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
                if (ev.action == MotionEvent.ACTION_DOWN) {
                        val view = currentFocus
                        //打印出x和y的坐标位置

                        // errorMess.setText("${arr[0]}和${arr[1]}.右边界：${right},下边界为${buttom},触摸的坐标为${ev.getX()}和${ev.getY()}")
                        if (KeyboardUtils.isShouldHideKeyBord(view, ev)) {
                               /* var arr= intArrayOf(0,0)
                                view?.getLocationInWindow(arr)
                                var right=arr[0]+view!!.width
                                var top=arr[1]+view!!.height
                                errorMess.setText("触摸的坐标为${view!!.width}和${view!!.height} 有边界和下边界为为${right}和${top}")*/
                                KeyboardUtils.hintKeyBoards(view)
                        }
                }
                return super.dispatchTouchEvent(ev)
        }


}