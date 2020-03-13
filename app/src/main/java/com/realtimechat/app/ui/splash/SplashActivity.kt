package com.realtimechat.app.ui.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.realtimechat.app.R
import com.realtimechat.app.data.prefs.AppPreferences
import com.realtimechat.app.ui.base.BaseActivity
import com.realtimechat.app.ui.base.MvpView
import com.realtimechat.app.ui.main.MainActivity
import com.realtimechat.app.ui.users.login.LoginActivity

class SplashActivity : BaseActivity(), SplashView {

    val mPresenter: SplashPresenter<SplashView> = SplashPresenter()

    lateinit var PERMISSIONS: Array<String>
    //lateinit var context: Context

    companion object {
        val PERMISSIONS_MULTIPLE_REQUEST = 123
        private val SPLASH_TIME_OUT = 2000

        lateinit var fa: Activity
    }

    private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** Hiding Title bar of this activity screen  */
        window.requestFeature(Window.FEATURE_NO_TITLE)

        /** Making this activity, full screen  */
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.splash_activity)
        mPresenter.onAttach(this)

        setUp()
    }

    override fun setUp() {
        fa = this
        PERMISSIONS = arrayOf(
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        CekPermission()
    }

    fun CekPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission()
        } else {
            ShowActivity()
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) +
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE) +
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) +
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, android.Manifest.permission.INTERNET) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, android.Manifest.permission.ACCESS_NETWORK_STATE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {

                SnackBarMsg("Aplikasi memerlukan beberapa izin, mohon diaktifkan.")
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSIONS,
                        PERMISSIONS_MULTIPLE_REQUEST
                    )
                }
            }
        } else {
            ShowActivity()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {
            PERMISSIONS_MULTIPLE_REQUEST -> if (grantResults.size > 0) {
                val permission0 = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val permission1 = grantResults[1] == PackageManager.PERMISSION_GRANTED
                val permission2 = grantResults[2] == PackageManager.PERMISSION_GRANTED
                val permission3 = grantResults[3] == PackageManager.PERMISSION_GRANTED

                if (permission0 && permission1 && permission2 && permission3) {
                    ShowActivity()
                } else {
                    SnackBarMsg("Aplikasi memerlukan beberapa izin, mohon diaktifkan.")
                }
            } else {
                SnackBarMsg("Aplikasi memerlukan beberapa izin, mohon diaktifkan.")
            }
        }
    }

    private fun ShowActivity() {
        Handler().postDelayed(/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            {
                LoginStatus()
            }, SPLASH_TIME_OUT.toLong()
        )
    }

    private fun SnackBarMsg(msg: String) {
        val snackBar = Snackbar.make(this.findViewById(R.id.content), msg, Snackbar.LENGTH_INDEFINITE)
        val snackbarView = snackBar.view
        val textView = snackbarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)

        snackBar.setAction("Enable") {
            snackBar.dismiss()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS,
                    PERMISSIONS_MULTIPLE_REQUEST
                )
            }
        }
        snackBar.show()
    }

    private fun LoginStatus() {
        val isLogin = AppPreferences.isLogin()
        if(isLogin){
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }else{
            mPresenter.setAccount()

            val i = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
