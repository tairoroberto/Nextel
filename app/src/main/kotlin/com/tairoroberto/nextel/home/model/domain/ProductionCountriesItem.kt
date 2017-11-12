package com.tairoroberto.nextel.home.model.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ProductionCountriesItem(@SerializedName("iso_3166_1")
                                   val iso: String = "",

                                   @SerializedName("name")
                                   val name: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(iso)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductionCountriesItem> {
        override fun createFromParcel(parcel: Parcel): ProductionCountriesItem {
            return ProductionCountriesItem(parcel)
        }

        override fun newArray(size: Int): Array<ProductionCountriesItem?> {
            return arrayOfNulls(size)
        }
    }
}