package com.realtimechat.app.ui.users.login

import com.realtimechat.app.ui.base.MvpPresenter

interface LoginPresenterView<V: LoginView> : MvpPresenter<V> {
    fun doLogin(email: String, password: String)
}