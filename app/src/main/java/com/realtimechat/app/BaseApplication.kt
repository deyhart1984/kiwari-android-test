package com.realtimechat.app

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.realtimechat.app.data.prefs.AppPreferences

class BaseApplication : MultiDexApplication() {

    companion object {
        lateinit var mContext: Context
        lateinit var mInstance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        mContext = this

        if (!BuildConfig.DEBUG) {
            //Fabric.with(this, new Crashlytics());
        }

        Log.i("BaseApp", "START")
        AppPreferences

    }

    fun getInstance(): BaseApplication {
        return mInstance
    }

    fun getContext(): Context {
        return mContext
    }
}