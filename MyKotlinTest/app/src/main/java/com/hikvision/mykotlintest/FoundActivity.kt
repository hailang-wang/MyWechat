package com.hikvision.mykotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.util.Found

class FoundActivity: Fragment() {
        private lateinit var founds:ArrayList<Found>
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
                var view=inflater.inflate(R.layout.found_layout,container,false)
                var foundRecyclerView=view.findViewById<RecyclerView>(R.id.foundRecyclerView)
                foundRecyclerView.layoutManager=LinearLayoutManager(context)
                intiValue()
                foundRecyclerView.adapter=FoundAdapter(founds)
                return view
        }

        fun intiValue(){
                founds=ArrayList()
                for(i in 1..100){
                                var image=0
                                //仅仅是为了好区分
                        image = when {
                                i%3==0 -> {
                                        R.drawable.ic_baseline_lock_24
                                }
                                i%3==1 -> {
                                        R.drawable.ic_baseline_person_24
                                }
                                else -> {
                                        R.drawable.ic_baseline_engineering_24
                                }
                        }
                                var name="朋友圈${i}号"
                                founds.add(Found(image,name))
                }
        }
}