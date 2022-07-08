package com.hikvision.mykotlintest

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hikvision.mykotlintest.util.Fruit

class FruitListMain : AppCompatActivity() {

        private lateinit var fruitListView:ListView
        //新建一个list，来存储即将展示的数据
        lateinit var list: ArrayList<Fruit>

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.fruit_main)

                //准备好数据源
                initValue()

                //先获取到控件的id,然后将此界面给他赋值adapter
                fruitListView=findViewById(R.id.fruitListView)
                //将listview进行适配！需要一个fruit适配的Adapter!需要新建一个
                var fruitAdapter=FruitAdapter(this,R.layout.fruit_layout,list)
               // fruitListView.adapter  //适配完了之后，就完成展示了
                fruitListView.adapter=fruitAdapter
              //希望对其进行一个点击事件的添加
                fruitListView.setOnItemClickListener { adapterView, view, i, l ->
                        var fruit = list[i]
                        Toast.makeText(this,fruit.name,Toast.LENGTH_SHORT).show()
                }

        }
        fun initValue(){
                list= ArrayList<Fruit>()
                for(i in 1..100){
                        var image=0
                        if(i%3==0){
                                image=R.drawable.ic_baseline_engineering_24
                        }else if(i%3==1){
                                image=R.drawable.ic_baseline_person_24
                        }else{
                                image=R.drawable.ic_baseline_lock_24
                        }
                        var name="姓名+${i}"
                        var introduce="我是第${i}名"
                        var fruit=Fruit(image,name,introduce)

                        list.add(fruit)
                }
                //需要准备参数

        }
}