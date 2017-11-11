package com.tairoroberto.nextel.home.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class BelongsToCollection(@SerializedName("backdrop_path")
                               val backdropPath: String? = "",

                               @SerializedName("name")
                               val name: String = "",

                               @SerializedName("id")
                               val id: Int = 0,

                               @SerializedName("poster_path")
                               val posterPath: String? = "") : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdropPath)
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeString(posterPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BelongsToCollection> {
        override fun createFromParcel(parcel: Parcel): BelongsToCollection {
            return BelongsToCollection(parcel)
        }

        override fun newArray(size: Int): Array<BelongsToCollection?> {
            return arrayOfNulls(size)
        }
    }
}