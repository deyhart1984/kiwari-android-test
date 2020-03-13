package com.realtimechat.app.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.realtimechat.app.utils.ConstantsUtils
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.realtimechat.app.BaseApplication.Companion.mContext
import com.realtimechat.app.data.network.model.users.UsersAccount

class AppPreferences {
    companion object {
        var mPrefs: SharedPreferences = mContext.getSharedPreferences(ConstantsUtils.PREF_NAME, Context.MODE_PRIVATE)

        fun isLogin(): Boolean {
            return mPrefs.getBoolean(ConstantsUtils.PREF_LOGIN_STATUS, false)
        }

        fun setLogin(login: Boolean) {
            mPrefs.edit().putBoolean(ConstantsUtils.PREF_LOGIN_STATUS, login).apply()
        }

        fun setLoginAt(account: Int) {
            mPrefs.edit().putInt(ConstantsUtils.PREF_LOGIN_AT, account).apply()
        }

        fun loginAt(): Int {
            return mPrefs.getInt(ConstantsUtils.PREF_LOGIN_AT, 0)
        }

        fun setRegister1(user: UsersAccount.Data) {
            val json = Gson().toJson(user)
            mPrefs.edit().putString(ConstantsUtils.PREF_USER_ACCOUNT_1, json).apply()
        }

        fun setRegister2(user: UsersAccount.Data) {
            val json = Gson().toJson(user)
            mPrefs.edit().putString(ConstantsUtils.PREF_USER_ACCOUNT_2, json).apply()
        }

        fun getAccount1(): UsersAccount.Data {
            val profileNotExist = UsersAccount.Data()
            profileNotExist.email = ""
            val json = mPrefs.getString(ConstantsUtils.PREF_USER_ACCOUNT_1, null) ?: return profileNotExist

            return Gson().fromJson<UsersAccount.Data>(json, object : TypeToken<UsersAccount.Data>() {

            }.type)
        }

        fun getAccount2(): UsersAccount.Data {
            val profileNotExist = UsersAccount.Data()
            profileNotExist.email = ""
            val json = mPrefs.getString(ConstantsUtils.PREF_USER_ACCOUNT_2, null) ?: return profileNotExist

            return Gson().fromJson<UsersAccount.Data>(json, object : TypeToken<UsersAccount.Data>() {

            }.type)
        }
    }
}
