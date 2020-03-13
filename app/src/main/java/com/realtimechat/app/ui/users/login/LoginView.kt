package com.realtimechat.app.ui.users.login

import com.realtimechat.app.data.network.model.users.Login
import com.realtimechat.app.ui.base.MvpView

interface LoginView: MvpView {
    fun loginSuccess()
    fun failed(msg: String)
}