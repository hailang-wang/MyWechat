package com.hikvision.mykotlintest

class Student {
    var name : String=""
    var age: Int=0
    var address=""
    //空参构造器
    fun constructor(){}
    fun constructor(name:String,age:Int){
        this.name=name
        this.age=age
    }
    fun printMessage(address:String){
        this.address=address
        println("${name}的年龄是${age} ,家庭住址为${address}")
    }

    fun week(a:String){
        when(a){
            "星期一" ->{
                println("今天星期几？")
                println("今天是星期一")}
            "星期二" ->{
                println("今天星期几？")
                println("今天是星期二")}
            else->{
                println("输入的日期有误，请重新输入")
            }
        }
    }
}