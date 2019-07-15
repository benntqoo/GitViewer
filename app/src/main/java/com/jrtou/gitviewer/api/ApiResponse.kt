package com.jrtou.gitviewer.api

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import com.jrtou.gitviewer.helpers.DialogHelper

abstract class ApiResponse<T : BaseResult>
    (private val context: Context, private val isShowDialog: Boolean = true) : Observer<T> {

    companion object {
        private val TAG: String = ApiResponse::class.java.simpleName
    }


    abstract fun success(data: T, result: ApiResult.Model)
    abstract fun failure(statusCode: String, model: ApiResult.Model)

    var isSowDialog: Boolean = false
    var dialog: AlertDialog? = null

    override fun onSubscribe(d: Disposable) {
        Log.i(TAG, "onSubscribe")
        if (isShowDialog) dialog = DialogHelper.getApiWaitingDialog(context)
    }

    override fun onNext(t: T) {
        Log.i(TAG, "onNext code = " + t.processStatus?.code)
        val result = when (t.processStatus?.code) {
            ApiResult.SUCCESS.code -> ApiResult.SUCCESS
            ApiResult.NOT_FOUND.code -> ApiResult.NOT_FOUND
            else -> ApiResult.NOT_FOUND
        }

        success(t, result.getModel(context))
    }

    override fun onComplete() {
        Log.i(TAG, "onComplete")
        dialog?.let { if (isShowDialog && it.isShowing) it.dismiss() }
    }

    override fun onError(e: Throwable) {
        Log.e(TAG, "onError ", e)
        if (isShowDialog) dialog?.dismiss()
        //連接 server 成功 但是 server error
        if (e is HttpException) {
            val model: ApiResult.Model = when (e.code().toString()) {
                ApiResult.BAD_GATEWAY.code -> ApiResult.BAD_GATEWAY.getModel(context)
                ApiResult.NOT_FOUND.code -> ApiResult.NOT_FOUND.getModel(context)
                else -> otherError(e)
            }
            failure(e.code().toString(), model)
            return
        }

        // 網路或是其他問題
        val apiResult: ApiResult = when (e) {
            is UnknownHostException -> ApiResult.NETWORK_NOT_CONNECT
            is ConnectException -> ApiResult.NETWORK_NOT_CONNECT
            is SocketTimeoutException -> ApiResult.CONNECTION_TIMEOUT
            else -> ApiResult.UNEXPECTED_RESULT
        }

        failure(apiResult.code, apiResult.getModel(context))
    }

    private fun otherError(e: HttpException) =
        Gson().fromJson(e.response().errorBody()?.charStream(), ApiResult.Model::class.java)
}
