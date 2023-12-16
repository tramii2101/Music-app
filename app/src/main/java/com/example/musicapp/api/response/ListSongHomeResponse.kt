package com.example.musicapp.api.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ListSongHomeResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : DataListSongAtHome?
)

data class ResultSongAtHome(
    @SerializedName("singer"      ) var singerId      : String?,
    @SerializedName("title"       ) var title       : String?,
    @SerializedName("description" ) var description : String?,
    @SerializedName("country"     ) var country     : String?,
    @SerializedName("image"       ) var image       : String?,
    @SerializedName("audio"       ) var audio       : String?,
    @SerializedName("year"        ) var year        : Int?   ,
    @SerializedName("length"      ) var length      : Int?   ,
    @SerializedName("playCount"   ) var playCount   : Int?   ,
    @SerializedName("category"    ) var category    : String?,
    @SerializedName("id"          ) var id          : String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(singerId)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(country)
        parcel.writeString(image)
        parcel.writeString(audio)
        parcel.writeValue(year)
        parcel.writeValue(length)
        parcel.writeValue(playCount)
        parcel.writeString(category)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultSongAtHome> {
        override fun createFromParcel(parcel: Parcel): ResultSongAtHome {
            return ResultSongAtHome(parcel)
        }

        override fun newArray(size: Int): Array<ResultSongAtHome?> {
            return arrayOfNulls(size)
        }
    }
}

data class DataListSongAtHome(
    @SerializedName("results"      ) var results      : ArrayList<ResultSongAtHome> = arrayListOf(),
)