package com.hikvision.mykotlintest.util

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService

open class KeyboardSetting : AppCompatActivity() {

        override fun dispatchTouchEvent(motionEvent: MotionEvent):Boolean{
                if(motionEvent.action==MotionEvent.ACTION_DOWN){
                        val v=currentFocus
                        //如果当前触摸到了空白地方，就需要回收
                        if(isShouldHideInput(v,motionEvent)){
                                //隐藏
                                hideSoftInput(v!!.windowToken)
                        }
                }
                return super.dispatchTouchEvent(motionEvent)
        }

        //判断当前触摸事件是不是空白处
        private fun isShouldHideInput(v:View?,event: MotionEvent):Boolean{
                if (v != null && v is EditText) {
                        val l = intArrayOf(0, 0)
                        v.getLocationInWindow(l)
                        val left = l[0]
                        val top = l[1]
                        val bottom = top + v.getHeight()
                        val right = (left + v.getWidth())
                        //如果触摸的点不是
                        return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
                }
                return false
        }
        //隐藏输入的实际操作,是真正关闭键盘的方法
        private fun hideSoftInput(token:IBinder){
                if(token!=null){
                        val im:InputMethodManager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        im.hideSoftInputFromWindow(
                                token,
                                InputMethodManager.HIDE_NOT_ALWAYS
                        )
                }
        }
}