package com.realtimechat.app.ui.main

import com.realtimechat.app.data.prefs.AppPreferences
import com.realtimechat.app.ui.base.BasePresenter

class MainPresenter<V: MainView>: BasePresenter<V>(), MainPresenterView<V> {
    override fun doLogout() {
        AppPreferences.setLogin(false)
        getMvpView().logoutSuccess()
    }

}