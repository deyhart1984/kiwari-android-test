package com.realtimechat.app.ui.main

import com.realtimechat.app.ui.base.MvpPresenter

interface MainPresenterView<V : MainView> : MvpPresenter<V> {
    fun doLogout()
}