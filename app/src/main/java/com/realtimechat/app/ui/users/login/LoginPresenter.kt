package com.realtimechat.app.ui.users.login

import com.realtimechat.app.data.prefs.AppPreferences
import com.realtimechat.app.ui.base.BasePresenter

class LoginPresenter<V : LoginView> : BasePresenter<V>(), LoginPresenterView<V> {

    override fun doLogin(email: String, password: String) {
        val emailRepo1 = AppPreferences.getAccount1().email
        val passRepo1 = AppPreferences.getAccount1().password

        val emailRepo2 = AppPreferences.getAccount2().email
        val passRepo2 = AppPreferences.getAccount2().password

        if(email == emailRepo1 && password == passRepo1){
            AppPreferences.setLogin(true)

            if(AppPreferences.isLogin()) {
                AppPreferences.setLoginAt(1)
                getMvpView().loginSuccess()
            }
            else{
                getMvpView().failed("Login failed")
            }

            return
        }

        if(email == emailRepo2 && password == passRepo2){
            AppPreferences.setLogin(true)

            if(AppPreferences.isLogin()) {
                AppPreferences.setLoginAt(2)
                getMvpView().loginSuccess()
            }
            else{
                getMvpView().failed("Login failed")
            }

            return
        }

        getMvpView().failed("Login failed")
    }
}