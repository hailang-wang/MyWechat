package com.hikvision.mykotlintest


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.hikvision.mykotlintest.util.Fruit

//自定义一个adapter,构造方法中传入父类方法中需要的参数？
class FruitAdapter( activity:Activity, var resource:Int,  data:List<Fruit>) : ArrayAdapter<Fruit>(activity,resource,data){
        //内部类，用于记录每一个view的一个属性设置，为的是在获取时，不需要重新findViewById
        inner class ViewStore(var fruitImage:ImageView,var fruitName:TextView,var fruitIntroduce:TextView)

        //此方法在单个子项滚动到屏幕的时候，会被调用
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                //就希望对每一个数据进行赋值,所以获取到当前位置的item布局
                var view:View
                var viewStore:ViewStore

                if(convertView==null){
                        view=LayoutInflater.from(context).inflate(resource,parent,false)
                        var fruitImage=view.findViewById<ImageView>(R.id.fruitImage)
                        var fruitName=view.findViewById<TextView>(R.id.fruitName)
                        var fruitIntroduce=view.findViewById<TextView>(R.id.fruitIntroduce)
                        viewStore=ViewStore(fruitImage,fruitName,fruitIntroduce)
                        //将内部类存储的信息与当前view进行绑定。
                        view.tag=viewStore
                }else{
                        view=convertView
                        viewStore=view.tag as ViewStore
                }


                //获取到组内元素

                //获取到每一个item的实例对象
                var fruit=getItem(position)
                //为每一个里面的列表项元素赋值
                if(fruit!=null){
                        viewStore.fruitImage.setImageResource(fruit.picture)
                        viewStore.fruitName.text=fruit.name
                        viewStore.fruitIntroduce.text=fruit.introduce
                }
                return view
        }
}

