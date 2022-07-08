package com.hikvision.mykotlintest

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MyListViewTest :AppCompatActivity() {
        //就是在这个界面来进行listview的展示,只展示简单的字符串
        private lateinit var listview:ListView

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.my_listview_test)
                //获取到listview的元素
                listview=findViewById(R.id.listView);
                //准备数据
                var names= arrayOf("小王","小张","小李","小陈","小八","小孩","小月","小涛","小器","小春","小陈","小张","小李","小陈","小八","小孩","小月")
                //新建一个ListView对象,制定展示数据的格式和数据的内容
                var adapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names)
                //将适配器加载到控件中
                listview.adapter=adapter
                listview.setOnItemClickListener { adapterView, view, i, l ->
                        var name=(view as TextView).getText().toString()
                        Toast.makeText(this,name,Toast.LENGTH_SHORT).show()
                }

        }
}