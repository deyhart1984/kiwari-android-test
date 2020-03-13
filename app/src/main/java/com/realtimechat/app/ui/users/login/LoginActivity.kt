package com.realtimechat.app.ui.users.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.realtimechat.app.R
import com.realtimechat.app.data.prefs.AppPreferences
import com.realtimechat.app.ui.base.BaseActivity
import com.realtimechat.app.ui.main.MainActivity
import kotlinx.android.synthetic.main.login_activity.*
import java.util.regex.Pattern

class LoginActivity : BaseActivity(), LoginView {

    val mPresenter: LoginPresenter<LoginView> = LoginPresenter()

    private val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE
    )

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        //setToolbar(toolbar)
        //showBackButton(true)
        mPresenter.onAttach(this)

        setUp()
    }

    override fun setUp() {

        btn_login.setOnClickListener{ doLogin() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isValidate(): Boolean {
        val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(ed_email.text.toString())

        if (!matcher.find()) {
            showDialogMessage("Failed Email!")
            return false
        }

        if (ed_pwd.text.toString().length < 6) {
            showDialogMessage("Password minimal 6 digit!")
            return false
        }

        return true
    }

    private fun doLogin(){
        if(isValidate()) {
            mPresenter.doLogin(ed_email.text.toString(), ed_pwd.text.toString())
        }
    }

    override fun loginSuccess() {
        val intent = MainActivity.getStartIntent(this)
        startActivity(intent)
        finish()
    }

    override fun failed(msg: String) {
        showDialogMessage(msg)
    }

}