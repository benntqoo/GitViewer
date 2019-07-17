package com.jrtou.gitviewer.api

import android.content.Context
import androidx.annotation.StringRes
import com.jrtou.gitviewer.R

/**
 * 客製化 api 請求錯誤回調
 */
enum class ApiResult(val code: String, @param: StringRes private val messageId: Int) {
    SUCCESS("000", R.string.api_code_200),
    BAD_GATEWAY("502", R.string.api_code_52),
    NOT_FOUND("404", R.string.api_code_404),
    CONNECTION_TIMEOUT("408", R.string.api_code_408),
    NETWORK_NOT_CONNECT("499", R.string.api_code_499),
    UNEXPECTED_RESULT("700", R.string.api_code_700);


    fun getModel(context: Context): Model {
        return Model(code, context.getString(messageId))
    }

    data class Model(var status: String, var message: String)
}
