package com.example.shimmer.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Data {

    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("avatar")
    var avatar: String? = null
    @SerializedName("email")
    var email: String? = null
}