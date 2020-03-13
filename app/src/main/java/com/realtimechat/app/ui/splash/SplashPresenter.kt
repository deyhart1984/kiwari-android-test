package com.realtimechat.app.ui.splash

import com.realtimechat.app.data.network.model.users.UsersAccount
import com.realtimechat.app.data.prefs.AppPreferences
import com.realtimechat.app.ui.base.BasePresenter

class SplashPresenter<V: SplashView>: BasePresenter<V>(), SplashPresenterView<V> {
    override fun setAccount() {
        val user1 = UsersAccount.Data()
        user1.email = "jarjit@mail.com"
        user1.password = "123456"
        user1.name = "Jarjit Singh"
        user1.avatar = "https://api.adorable.io/avatars/160/jarjit@mail.com.png"

        val user2 = UsersAccount.Data()
        user2.email = "ismail@mail.com"
        user2.password = "123456"
        user2.name = "Ismail bin Mail"
        user2.avatar = "https://api.adorable.io/avatars/160/ismail@mail.com.png"

        AppPreferences.setRegister1(user1)
        AppPreferences.setRegister2(user2)

    }
}