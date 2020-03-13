package com.realtimechat.app.ui.base

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.realtimechat.app.R
import com.realtimechat.app.utils.NetworkUtils
import rebus.permissionutils.AskAgainCallback
import com.realtimechat.app.utils.CommonUtils


abstract class BaseActivity : AppCompatActivity(), MvpView, BaseFragment.Callback {
    var mProgressDialog : ProgressDialog? = null
    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        /*final MenuItem menuItem = menu.findItem(R.id.notification);

        View actionView = MenuItemCompat.getActionView(menuItem);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });*/

        return true
    }*/

    fun setToolbar(mToolbar: Toolbar) {
        setSupportActionBar(mToolbar)
    }

    fun hideOption(item: MenuItem) {
        item.isVisible = false
    }

    fun showOption(item: MenuItem) {
        item.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                //finish()
                onBackPressed()
                return true
            }
        }/*case R.id.setting: {
                Intent intent = SettingActivity.getStartIntent(this);
                startActivity(intent);
                return true;
            }*/
        return super.onOptionsItemSelected(item)
    }

    // @Override
    //  protected void attachBaseContext(Context newBase) {
    //newBase = LocaleChanger.configureBaseContext(newBase);
    //super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    //  }

    override fun onResume() {
        super.onResume()
    }

    override fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.cancel()
        }
    }

    override fun onError(message: String) {
        if (message != null) {
            //showSnackBar(message);
        } else {
            //showSnackBar(getString(R.string.some_error));
        }
    }

    override fun onError(@StringRes resId: Int) {
        onError(getString(resId))
    }

    override fun showMessage(message: String) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Several things happen. Please be patient :", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

    override fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(getContext())
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun getContext(): Context {
        return this
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        //ketika disentuh tombol back
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(Activity.RESULT_OK)
            finish()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    /**
     * Overrides the pending NearActivity transition by performing the "Enter" animation.
     */
    protected fun overridePendingTransitionEnter() {
        //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending NearActivity transition by performing the "Exit" animation.
     */
    protected fun overridePendingTransitionExit() {
        //overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    protected abstract fun setUp()

    override fun showPermissionDialog(response: AskAgainCallback.UserResponse) {
        AlertDialog.Builder(this)
            .setTitle("Kebutuhan hak akses")
            .setMessage("Aplikasi membutuhkan hak akses, Ijinkan hak akses?")
            .setPositiveButton("OK") { dialogInterface, i -> response.result(true) }
            .setNegativeButton( "Cancel") { dialogInterface, i -> response.result(false) }
            .setCancelable(false)
            .show()
    }

    fun showBackButton(bool: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(bool)
        supportActionBar!!.setDisplayShowHomeEnabled(bool)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit()
        return
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun showDialogMessage(message: String){
        val dialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_message, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        //dialog.setIcon(R.mipmap.ic_launcher)
        //dialog.setTitle("Device Info")

        val mTvDeviceId = dialogView.findViewById(R.id.tv_message) as TextView
        val btnOk = dialogView.findViewById(R.id.btn_ok) as Button

        mTvDeviceId.text = message

        val show = dialog.show()

        btnOk.setOnClickListener {
            show.dismiss()
        }
    }

}
