package com.hikvision.mykotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikvision.mykotlintest.database.dao.FriendDao
import com.hikvision.mykotlintest.database.database.AppDatabase
import com.hikvision.mykotlintest.database.entity.Friend
import com.hikvision.mykotlintest.util.Address
import kotlinx.coroutines.launch

class AddressActivity : Fragment() {
        lateinit var addresss: ArrayList<Address>
        private lateinit var friendDao: FriendDao
        private lateinit var addressView:View


        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
               addressView = inflater.inflate(R.layout.address_fragment, container, false)

                var addressRecyclerView = addressView.findViewById<RecyclerView>(R.id.addressList)
                initValues()
                addressRecyclerView.layoutManager = LinearLayoutManager(context)
                addressRecyclerView.adapter = AddressAdapter(addresss)
                return addressView
        }

        //当再次被展示的时候，从新加载数据
        override fun onHiddenChanged(hidden: Boolean) {

                if(!hidden){
                        var addressRecyclerView = addressView.findViewById<RecyclerView>(R.id.addressList)
                        initValues()
                        addressRecyclerView.layoutManager = LinearLayoutManager(context)
                        addressRecyclerView.adapter = AddressAdapter(addresss)
                }
                super.onHiddenChanged(hidden)
        }

        fun initValues() {
                addresss = ArrayList()
                //怎么在fragment中引入数据库
                friendDao = AppDatabase.getDatabaseSingleton2(requireContext()).friendDao()
                //当前查出所有的朋友
                var friends = friendDao.queryAll()
                //应该在这里查询所有的friend，将结果查出来，遍历所有的集合，然后将里面的元素对于friendDao来说只要有用的2个
                // friendName 和friendAvatar
                //下面开始赋值
                if (friends != null) {
                        for (friend in friends) {
                                var image = when (friend.avatar) {
                                        1->{R.drawable.view1}
                                        2->{R.drawable.dou2}
                                        3->{R.drawable.dou4}
                                        else->{R.drawable.flow1}
                                }

                                var name = friend.name
                                addresss.add(Address(image, name))
                        }
                }
        }
}