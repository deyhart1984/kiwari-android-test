package com.realtimechat.app.ui.base

import android.content.Context
import com.androidnetworking.error.ANError


open class BasePresenter<V : MvpView> : MvpPresenter<V> {

    private val TAG = "BasePresenter"
    private var mMvpView: V? = null

    constructor (){}

    override fun onAttach(mvpView: V) {
        mMvpView = mvpView
    }

    override fun onDetach() {
        mMvpView = null
    }

    fun isViewAttached(): Boolean {
        return mMvpView != null
    }

    fun getMvpView(): V {
        return mMvpView!!
    }

    fun getContext(): Context {
        return mMvpView!!.getContext()
    }

    fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }

    override fun handleApiError(error: ANError) {

    }

    /*override fun handleApiError(error: ANError) {
        if (error.cause is SocketTimeoutException) {
            getMvpView().onError("SocketTimeoutException")
            return;
        }

        if(error.cause is ConnectException || error.cause is UnknownHostException){
            if(mMvpView is MainActivity){
                Log.e("Activity", "MainActivity")
            }else{
                Log.e("Activity ","Anouther");
                getMvpView().onError("ConnectException or UnknownHostException");
            }
            return;
        }

        if (error.errorBody == null) {
            getMvpView().onError("API Error !");
            return;
        }


        if (error.errorCode == API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
            getMvpView().onError(R.string.connection_error);
            return;
        }

        if (error.getErrorCode() == API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
            getMvpView().onError(R.string.api_retry_error);
            return;
        }

        final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        try {
            ApiError apiError = gson.fromJson(error.getErrorBody(), ApiError.class);

            if (apiError == null || apiError.getMessage() == null) {
                getMvpView().onError(R.string.api_default_error);
                return;
            }

            switch (error.getErrorCode()) {
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                case HttpsURLConnection.HTTP_FORBIDDEN:
                    setUserAsLoggedOut();
                    getMvpView().openActivityOnTokenExpire();
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                case HttpsURLConnection.HTTP_NOT_FOUND:
                default:
                    getMvpView().onError(apiError.getMessage());
            }
        } catch (JsonSyntaxException | NullPointerException e) {
            Log.e(TAG, "handleApiError", e);
            getMvpView().onError(R.string.api_default_error);
        }
    }*/

    override fun setUserAsLoggedOut() {

    }

    class MvpViewNotAttachedException :
        RuntimeException("Please call Presenter.onAttach(MvpView) before" + " requesting data to the Presenter")
}