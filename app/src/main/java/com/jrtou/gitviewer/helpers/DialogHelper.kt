package com.jrtou.gitviewer.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.jrtou.gitviewer.R
import com.jrtou.gitviewer.views.dialog.CustomDialog
import com.jrtou.gitviewer.views.dialog.IDialog
import kotlinx.android.synthetic.main.view_api_progress.view.*


object DialogHelper {
    @SuppressLint("InflateParams")
    fun getApiWaitingDialog(context: Context): AlertDialog? {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_api_progress, null)

        view.loading_msg.text = "Waiting"

        val dialog = AlertDialog.Builder(context)
            .setTitle("提醒")
            .setView(view)
            .setCancelable(false)
            .show()

        dialog?.window?.let { window ->
            // 取得螢幕解析度
            val dm = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(dm)

            val width = dm.widthPixels
            val lp = WindowManager.LayoutParams()

            lp.copyFrom(window.attributes)
            lp.width = (width * 0.8).toInt()
            window.attributes = lp
        }

        return dialog
    }

    fun getAlertMessageDialog(activity: AppCompatActivity, @StringRes message: Int) {
        getAlertMessageDialog(activity, activity.getString(R.string.dialog_title), message)
    }

    fun getAlertMessageDialog(activity: AppCompatActivity, message: String) {
        getAlertMessageDialog(activity, activity.getString(R.string.dialog_title), message)
    }

    fun getAlertMessageDialog(activity: AppCompatActivity, @StringRes title: Int, @StringRes message: Int) {
        getAlertMessageDialog(activity, activity.getString(title), activity.getString(message))
    }

    fun getAlertMessageDialog(activity: AppCompatActivity, title: String, @StringRes message: Int) {
        getAlertMessageDialog(activity, title, activity.getString(message))
    }

    fun getAlertMessageDialog(activity: AppCompatActivity, @StringRes title: Int, message: String) {
        getAlertMessageDialog(activity, activity.getString(title), message)
    }

    fun getAlertMessageDialog(activity: AppCompatActivity, title: String, message: String): IDialog? {
        return CustomDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setWidth(0.8f)
            .setPositiveButton(R.string.dialog_btn_submit)
            .show()
    }

    fun getEditDialog(
        activity: AppCompatActivity, @LayoutRes layoutId: Int,
        builderListener: IDialog.OnBuildListener,
        submitClickListener: IDialog.OnClickListener
    ) {
        CustomDialog.Builder(activity)
            .setTitle("編輯")
            .setDialogView(layoutId)
            .setWidth(0.8f)
            .setBuildListener(builderListener)
            .setPositiveButton(R.string.dialog_btn_submit, submitClickListener)
            .setNegativeButton(R.string.dialog_btn_cancel)
            .setDismissListener(object : IDialog.OnDismissListener {
                override fun onDismiss(dialog: IDialog) {
                    val imm: InputMethodManager =
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    dialog.getDialogView()?.findFocus()?.let { focusView ->
                        if (focusView is EditText || focusView is AppCompatEditText) imm.toggleSoftInput(
                            InputMethodManager.HIDE_IMPLICIT_ONLY,
                            0
                        )
                    }
                }
            }).show()
    }
}