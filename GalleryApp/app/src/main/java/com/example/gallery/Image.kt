package com.example.gallery

import android.os.Parcel
import android.os.Parcelable

class Image(var desc : String, var imagePath : String, var rating : Float) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flag: Int) {
        dest?.writeString(this.desc)
        dest?.writeString(this.imagePath)
        dest?.writeFloat(this.rating)
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}