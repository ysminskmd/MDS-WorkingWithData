package com.exmaple.shad.serializableparcelable

import android.os.Parcel
import android.os.Parcelable

class Cat(private val name: String,
          private val age: Int,
          private val color: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readInt(),
            parcel.readString() ?: "")

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeInt(age)
        dest?.writeString(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cat> {
        override fun createFromParcel(parcel: Parcel): Cat {
            return Cat(parcel)
        }

        override fun newArray(size: Int): Array<Cat?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Name: $name, Age: $age, Color: $color"
    }
}