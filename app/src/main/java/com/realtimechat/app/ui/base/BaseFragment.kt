package com.realtimechat.app.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import rebus.permissionutils.AskAgainCallback

abstract class BaseFragment : Fragment(), MvpView {
    private var baseActivity: BaseActivity? = null

    private val mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.baseActivity = activity
            activity!!.onFragmentAttached()
        }
    }

    protected abstract fun setUp(view: View)

    override fun onError(message: String) {
        if (baseActivity != null) {
            baseActivity!!.onError(message)
        }
    }

    override fun onError(@StringRes resId: Int) {
        if (baseActivity != null) {
            baseActivity!!.onError(resId)
        }
    }

    override fun showMessage(message: String) {
        if (baseActivity != null) {
            baseActivity!!.showMessage(message)
        }
    }

    override fun showMessage(@StringRes resId: Int) {
        if (baseActivity != null) {
            baseActivity!!.showMessage(resId)
        }
    }

    override fun isNetworkConnected(): Boolean {
        return if (baseActivity != null) {
            baseActivity!!.isNetworkConnected()
        } else false
    }

    override fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    override fun getContext(): Context {
        if(baseActivity != null) {
            return baseActivity!!.getContext()
        }else{
            return requireContext()
        }
    }

    override fun showDialogMessage(message: String) {
        if (baseActivity != null) {
            baseActivity!!.showDialogMessage(message)
        }
    }

    override fun showPermissionDialog(response: AskAgainCallback.UserResponse) {
        if (baseActivity != null) {
            baseActivity!!.showPermissionDialog(response)
        }
    }

    override fun showLoading() {
        if (baseActivity != null) {
            baseActivity!!.showLoading()
        }
    }

    override fun hideLoading() {
        if (baseActivity != null) {
            baseActivity!!.hideLoading()
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}
