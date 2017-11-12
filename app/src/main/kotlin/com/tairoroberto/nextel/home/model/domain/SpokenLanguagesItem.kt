package com.tairoroberto.nextel.home.model.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SpokenLanguagesItem(
        @SerializedName("name")
        val name: String = "",

        @SerializedName("iso_639_1")
        val iso: String = "") : Parcelable {

        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(name)
                parcel.writeString(iso)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<SpokenLanguagesItem> {
                override fun createFromParcel(parcel: Parcel): SpokenLanguagesItem {
                        return SpokenLanguagesItem(parcel)
                }

                override fun newArray(size: Int): Array<SpokenLanguagesItem?> {
                        return arrayOfNulls(size)
                }
        }
}