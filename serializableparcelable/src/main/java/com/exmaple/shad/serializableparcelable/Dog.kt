package com.exmaple.shad.serializableparcelable

import java.io.Serializable

class Dog(private val name: String
          , private val age: Int
          , private val color: String
          , private val friend: Cat
) : Serializable {

    override fun toString(): String {
//        return "Name: $name, Age: $age, Color: $color"
        return "Name: $name, Age: $age, Color: $color, Friend: $friend"
    }
}