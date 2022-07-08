package com.hikvision.mykotlintest.classTest

open class Fruit {
    open fun eat(){
        println("水果很好吃")
    }
    open fun add(a:Int,b:Int): Int{
        return a+b;
    }
   open fun sub(a:Int,b:Int){
        print(a-b)
    }
}