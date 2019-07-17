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

/**
 * 客製化 api 請求回調判斷
 *
 * @param context [Waiting Dialog][DialogHelper.getApiWaitingDialog] 與 [自訂義錯誤訊息][ApiResult.Model] 所需 [context][Context]
 * @param isShowDialog 網路請求是否顯示 [Waiting Dialog][DialogHelper.getApiWaitingDialog]
 */
abstract class ApiResponse<T>(private val context: Context, private val isShowDialog: Boolean = true) : Observer<T> {
    companion object {
        private val TAG: String = ApiResponse::class.java.simpleName
    }

    private var dialog: AlertDialog? = null

    /**
     * 自定義成功回調
     */
    abstract fun success(data: T, result: ApiResult.Model)

    /**
     * 自訂義失敗回掉
     */
    abstract fun failure(statusCode: String, model: ApiResult.Model)


    override fun onSubscribe(d: Disposable) {
        Log.i(TAG, "onSubscribe")
        if (isShowDialog) dialog = DialogHelper.getApiWaitingDialog(context)
    }

    override fun onNext(t: T) {
        val result: ApiResult = run { t?.let { ApiResult.SUCCESS } ?: run { ApiResult.NOT_FOUND } }
        success(t, result.getModel(context))
    }

    override fun onComplete() {
        dialog?.let { if (isShowDialog && it.isShowing) it.dismiss() }
    }

    override fun onError(e: Throwable) {
        if (isShowDialog) dialog?.dismiss()
        //連接 server 成功 但是 server error
        if (e is HttpException) {
            Log.e(TAG, "onError (line 59): ${e.code()}")
            val model: ApiResult.Model = when (e.code().toString()) {
                ApiResult.BAD_GATEWAY.code -> ApiResult.BAD_GATEWAY.getModel(context)
                ApiResult.NOT_FOUND.code -> ApiResult.NOT_FOUND.getModel(context)
                else -> otherError(e)
            }
            failure(e.code().toString(), model)
            return
        }

        Log.e(TAG, "onError ", e)
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
        Gson().fromJson(e.response()?.errorBody()?.charStream(), ApiResult.Model::class.java)
}
