package com.dzzchao.fwanandroid

import android.app.Person
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

        val (name,age) = test()
        print(name)
        print(age)
    }

    private fun test(): com.dzzchao.fwanandroid.Person {
        return Person("zhangchao",12)
    }


}

data class Person(val name:String,val age:Int)
