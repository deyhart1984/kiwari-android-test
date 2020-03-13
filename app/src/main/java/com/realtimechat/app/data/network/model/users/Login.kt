package com.realtimechat.app.data.network.model.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Login {
    class Request {
        @SerializedName("email")
        @Expose
        var email : String = ""

        @SerializedName("password")
        @Expose
        var password : String = ""
    }

    class Response {
        @SerializedName("rc")
        @Expose
        var rc: String? = ""

        @SerializedName("message")
        @Expose
        var message: String? = ""

        @SerializedName("data")
        @Expose
        var data = UsersAccount.Data()
    }
}
