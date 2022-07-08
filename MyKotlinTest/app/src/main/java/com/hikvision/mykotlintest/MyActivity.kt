package com.hikvision.mykotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.util.My

//我的
class MyActivity:Fragment() {
        private lateinit var mys:ArrayList<My>
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
               var view=inflater.inflate(R.layout.my_fragment,container,false)
                var myRecyclerView=view.findViewById<RecyclerView>(R.id.myRecyclerView)
                initValue()
                myRecyclerView.layoutManager=LinearLayoutManager(context)
                myRecyclerView.adapter=MyAdapter(mys)
                return view
        }
        fun initValue(){
                mys=ArrayList()
                for(i in 1..50){
                        var image=0
                        //仅仅是为了好区分
                        image = when {
                                i % 3 == 0 -> {
                                        R.drawable.ic_baseline_lock_24
                                }
                                i % 3 == 1 -> {
                                        R.drawable.ic_baseline_person_24
                                }
                                else -> {
                                        R.drawable.ic_baseline_engineering_24
                                }
                        }
                        var  name="服务${i}"
                        mys.add(My(image,name))
                }
        }
}