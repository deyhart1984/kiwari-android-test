package com.realtimechat.app.data.network.model.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UsersAccount: Serializable{

    class Data: Serializable {

        @SerializedName("name")
        @Expose
        lateinit var name: String

        @SerializedName("email")
        @Expose
        lateinit var email: String

        @SerializedName("password")
        @Expose
        lateinit var password: String

        @SerializedName("avatar")
        @Expose
        lateinit var avatar: String

    }
}