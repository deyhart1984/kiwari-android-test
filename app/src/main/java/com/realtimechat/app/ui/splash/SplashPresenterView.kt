package com.realtimechat.app.ui.splash

import com.realtimechat.app.ui.base.MvpPresenter

interface SplashPresenterView<V : SplashView> : MvpPresenter<V> {
    fun setAccount()
}