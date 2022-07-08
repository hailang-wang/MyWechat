package com.hikvision.mykotlintest.interfaceTest
interface  Fruit{
    fun name()
}
class Apple:Fruit {
    override fun name() {

        print("我是一个苹果")

    }
}