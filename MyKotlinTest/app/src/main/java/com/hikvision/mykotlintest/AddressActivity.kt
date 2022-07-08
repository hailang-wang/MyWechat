package com.hikvision.mykotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.util.Address
import com.hikvision.mykotlintest.util.UserMessage

class AddressActivity : Fragment() {
        lateinit var addresss:ArrayList<Address>
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
                var view=inflater.inflate(R.layout.address_fragment,container,false)
                var addressRecyclerView=view.findViewById<RecyclerView>(R.id.addressList)
                initValues()
                addressRecyclerView.layoutManager= LinearLayoutManager(context)
                addressRecyclerView.adapter=AddressAdapter(addresss)
                return view
        }
        fun initValues() {
                addresss = ArrayList()
                for (i in 1..200) {
                        var image = 0
                        //仅仅是为了好区分
                        if (i % 3 == 0) {
                                image = R.drawable.ic_baseline_lock_24
                        } else if (i % 3 == 1) {
                                image = R.drawable.ic_baseline_person_24
                        } else {
                                image = R.drawable.ic_baseline_engineering_24
                        }
                        var name = "好友${i}号"
                        addresss.add(Address(image, name))
                }
        }
}