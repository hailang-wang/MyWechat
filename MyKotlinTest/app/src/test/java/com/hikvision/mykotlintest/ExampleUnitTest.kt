package com.hikvision.mykotlintest

import com.hikvision.mykotlintest.classTest.Apple
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        println("你是我的")


    }

    @Test
    fun functionTest(){
        var arr=arrayOf(1,2,3,4)
        for(i in arr.indices){
            println(arr[i])
        }

        /* val b = Array(3, { i -> (i * 2) })*/
        val b = Array(3,{ i-> (i * 3)})
        print(b[0])
        print(b[1])
        print(b[2])


    }

    @Test
    fun stringTest(){
        val a=2
        val name="王海浪"
        println("[$name]的年龄是:$a")
    }
    @Test
    fun stringClass(){
        var student =Student()

        student.constructor("张三",13)
        student.printMessage("安徽省")
        student.week("星期4")

    }
    @Test
    fun arrayTest(){
        var arr:  Array<Int> = arrayOf(2,4,1)
        var strArr :Array<String> = arrayOf("我","爱","你","中","国")
        for(i in 0 until strArr.size){
            println(strArr[i])
        }
        //先找出arr中的最大值
        var max=0
        for (i in 0 until 3 step 1 ){
            if(arr[i]>max){
                max=arr[i]
            }

        }
        println("最大的数是"+max)
        //顺序输出数组中的值
        for(str in strArr){
            print(str)
        }
        var sA: Array<String>  =arrayOf("a","b")
        for(i in sA){
            println(i+"特殊符号的打印${'$'}")
        }
    }
    @Test
    fun testSet(){
       var setVal:Set<Int>?
        setVal= setOf(1,2,3,4,5,6)
        println(setVal.size)

        setVal=setVal.plus(7)
        setVal.plus(8)
        for(value in setVal){
            println(value)
        }

        var aa:Int=0
        aa=setVal?.size?:3
        var dd:Array<Int> = arrayOf(1,2,3,4)



    }

    @Test
    fun testOverride(){
        var fruit=Apple()
        fruit.eat()
        fruit.add(4)
        fruit.sub(6,3)
        var apple=com.hikvision.mykotlintest.interfaceTest.Apple()
        apple.name()
    }





}