package com.hikvision.mykotlintest.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hikvision.mykotlintest.R

class FragmentTest2:Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
                return inflater.inflate(R.layout.fragment_test2,container,false)
        }
}