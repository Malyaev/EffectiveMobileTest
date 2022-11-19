package com.yingenus.feature_showcase.domain.dto

import android.net.Uri
import android.os.Parcel
import android.os.ParcelFormatException
import android.os.Parcelable


internal data class Category(val icon: Uri, val title: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Uri::class.java.classLoader)?: throw ParcelFormatException(
            "uri cant be null"
        ),
        parcel.readString()?: throw ParcelFormatException(
            "title cant be null"
        )
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(icon, flags)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }

}