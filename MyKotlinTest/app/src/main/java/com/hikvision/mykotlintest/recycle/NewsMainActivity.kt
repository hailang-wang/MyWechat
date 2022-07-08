package com.hikvision.mykotlintest.recycle

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.R

class NewsMainActivity : AppCompatActivity() {

        private lateinit var newsList:ArrayList<News>
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.news_main_activity)
                //初始化参数
                initNews()
                //找到要显示的数据的 位置
                var recyclerView=findViewById<RecyclerView>(R.id.recycleViewItem)
                var layoutManager = LinearLayoutManager(this)

                recyclerView.layoutManager=layoutManager
                var adapter=NewsAdapter(newsList)
                recyclerView.adapter=adapter

        }
        fun initNews(){
                newsList= ArrayList()
                for(i in 1..100){
                        var image=0
                        //仅仅是为了好区分
                        if(i%3==0){
                                image=R.drawable.ic_baseline_lock_24
                        }else if(i%3==1){
                                image=R.drawable.ic_baseline_person_24
                        }else{
                                image=R.drawable.ic_baseline_engineering_24
                        }

                        var name="新闻+${i}"
                        var details="内容是我是第${i}条新闻"
                        var news=News(image,name,details)
                        newsList.add(news)
                }
        }
}