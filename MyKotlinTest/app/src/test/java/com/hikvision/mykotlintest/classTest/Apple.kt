package com.hikvision.mykotlintest.classTest

class Apple:Fruit() {
    var name:String=""

    override fun eat(){
        println("苹果很好吃！")
    }
    fun add(c:Int): Int{
        var d=add(1,2)+c;
        println(d)
        return d

    }
    override fun sub(a:Int,b:Int){
        println(a*b)
    }

}