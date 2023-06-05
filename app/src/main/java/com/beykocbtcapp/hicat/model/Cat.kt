package com.beykocbtcapp.hicat.model

import com.google.gson.annotations.SerializedName

data class Cat(

    @SerializedName("id") var id: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null

)