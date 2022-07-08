package com.hikvision.mykotlintest.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.FruitAdapter
import com.hikvision.mykotlintest.R

//相比于ArrayAdapter这里应该也是有几个参数的,只不过把参数给写到了方法里面，只留下一个数据传输即可
class NewsAdapter(var newsList:List<News> ) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
        //在内部类里面获取到item里面的组件
        inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
                var newPicture:ImageView=view.findViewById(R.id.newPicture)
                var newName:TextView=view.findViewById(R.id.newsName)
                var newDetail:TextView=view.findViewById(R.id.newDetail)
        }

        //重写的第一个方法，用来给制定加载那个类型的Recycler布局
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
               val view=LayoutInflater.from(parent.context).inflate(R.layout.news_item_layout,parent,false)
                var viewHolder=ViewHolder(view)
                //单机事件
                viewHolder.itemView.setOnClickListener {
                       var position= viewHolder.adapterPosition
                        var news=newsList[position]
                        Toast.makeText(parent.context,"你点击的新闻是${news.name}",Toast.LENGTH_SHORT).show()
                }
                viewHolder.newPicture.setOnClickListener {
                        var position=viewHolder.adapterPosition
                        var news=newsList[position]
                        Toast.makeText(parent.context,"你点击的图片是${news.name}",Toast.LENGTH_SHORT).show()
                }

                return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val news=newsList[position]
                holder.newPicture.setImageResource(news.picture)
                holder.newName.text=news.name
                holder.newDetail.text=news.detail
        }

        override fun getItemCount(): Int {
                return newsList.size
        }
}