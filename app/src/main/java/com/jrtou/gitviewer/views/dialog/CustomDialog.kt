package com.jrtou.gitviewer.views.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.AnimRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.jrtou.gitviewer.R
import com.jrtou.gitviewer.helpers.ScreenHelper

class CustomDialog : DialogFragment(), IDialog, View.OnClickListener {
    companion object {
        val TAG: String = CustomDialog::class.java.simpleName

        fun newInstance(manager: FragmentManager, layoutParam: LayoutParam): CustomDialog {
            val dialog = CustomDialog()
            dialog.manager = manager
            dialog.layoutParam = layoutParam
            return dialog
        }
    }

    lateinit var layoutParam: LayoutParam
    var manager: FragmentManager? = null
    private var dialogView: View? = null
    var customView: View? = null


    var title: String = ""
    var message: String = ""

    private var buildListener: IDialog.OnBuildListener? = null
    private var dismissListener: IDialog.OnDismissListener? = null

    private var isShowPositive: Boolean = false
    private var positiveButtonListener: IDialog.OnClickListener? = null
    private var positiveText: String = ""

    private var isShowNegative: Boolean = false
    private var negativeButtonListener: IDialog.OnClickListener? = null
    private var negativeText: String = ""

    private var checkDataListener: IDialog.onCheckDataListener? = null

    @AnimRes
    var animRes: Int = 0

    override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialogView = inflater.inflate(R.layout.custom_dialog, container, false)

        dialogView?.let { view ->
            if (layoutParam.customViewId != 0) {
                val customContent: RelativeLayout = view.findViewById(R.id.customDialogView)
                customView = LayoutInflater.from(context).inflate(layoutParam.customViewId, customContent, true)
                customView?.let { buildListener?.onCustomViewCreate(this, it, layoutParam.customViewId) }
            }
        }

        return dialogView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated ")
        dialog?.let {
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)//不顯示 title
            it.setCancelable(isCancelable)//false 屏蔽物理返回按鈕
            it.setCanceledOnTouchOutside(layoutParam.isCancelableOutside)//點擊 dialog 外不消失
            it.setOnKeyListener { _, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && !isCancelable }
        }

        if (!layoutParam.isCustomView) {
            Log.i(TAG, "onViewCreated default view build")
            val titleView: TextView = view.findViewById(R.id.customDialogTvTitle)
            titleView.text = title

            val messageView: TextView = view.findViewById(R.id.customDialogTvMessage)
            messageView.text = message

            if (isShowPositive) {
                val positiveButton: Button = view.findViewById(R.id.customDialogBtSubmit)
                positiveButton.text = positiveText
                positiveButton.setOnClickListener(this)
                positiveButton.visibility = View.VISIBLE
            }

            if (isShowNegative) {
                val negativeButton: Button = view.findViewById(R.id.customDialogBtCancel)
                negativeButton.text = negativeText
                negativeButton.setOnClickListener(this)
                negativeButton.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { viewWindow ->
            Log.i(TAG, "onStart param setting")
            viewWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))//設置背景透明
            if (animRes > 0) viewWindow.setWindowAnimations(animRes)//設置動畫

            val params = viewWindow.attributes
            params.width = layoutParam.width//設置寬度
            params.height = layoutParam.height//設置高度

            params.dimAmount = layoutParam.dimAmount//透明度設置 0.0f - 1.0 f
            params.gravity = layoutParam.gravity
            viewWindow.attributes = params
        }
    }

    override fun onDestroy() {
        dismissListener?.onDismiss(this)
        super.onDestroy()

    }

    override fun showDialog(tag: String) {
        show(manager, tag)
    }

    override fun dismissDialog() {
        dismiss()
    }

    override fun isShow(): Boolean {
        return isShow()
    }


    override fun onClick(v: View?) {
        checkDataListener?.let {
            when (v?.id) {
                R.id.customDialogBtSubmit -> {
                    if (it.onCheckData(this)) {
                        dismissDialog()
                        positiveButtonListener?.onClick(this)
                    } else {
                    }
                }
                R.id.customDialogBtCancel -> {
                    dismissDialog()
                    negativeButtonListener?.onClick(this)
                }
                else -> {
                }
            }
        } ?: run {
            dismissDialog()
            when (v?.id) {
                R.id.customDialogBtSubmit -> positiveButtonListener?.onClick(this)
                R.id.customDialogBtCancel -> negativeButtonListener?.onClick(this)
                else -> {
                }
            }
        }
    }

    override fun getDialogView(): View? {
        return dialogView
    }

    data class Builder(var activity: AppCompatActivity) {
        private val layoutParam: LayoutParam = LayoutParam()

        private var title: String = ""
        private var message: String = ""

        private var positiveText: String = ""
        private var positiveListener: IDialog.OnClickListener? = null
        private var isShowPositive: Boolean = false

        private var isShowNegative: Boolean = false
        private var negativeText: String = ""
        private var negativeListener: IDialog.OnClickListener? = null

        private var buildListener: IDialog.OnBuildListener? = null
        private var dismissListener: IDialog.OnDismissListener? = null
        private var checkDataListener: IDialog.onCheckDataListener? = null

        fun setTitle(@StringRes title: Int) = apply { this.title = activity.resources.getString(title) }
        fun setTitle(title: String) = apply { this.title = title }

        fun setMessage(@StringRes message: Int) = apply { this.message = activity.resources.getString(message) }
        fun setMessage(message: String) = apply { this.message = message }

        fun setGravity(gravity: Int) = apply { this.layoutParam.gravity = gravity }
        fun setDialogView(@LayoutRes layoutRes: Int) = apply { this.layoutParam.customViewId = layoutRes }
        fun setCancelable(isCancelable: Boolean) = apply { this.layoutParam.isCancelableOutside = isCancelable }
        fun setDismissListener(dismissListener: IDialog.OnDismissListener) = apply { this.dismissListener = dismissListener }
        fun setCheckDataListener(checkDataListener: IDialog.onCheckDataListener?) = apply { this.checkDataListener = checkDataListener }
        /**
         * 設置 寬 為螢幕的多少比例
         * @param width range 0-1
         */
        fun setWidth(width: Float) = apply {
            this.layoutParam.width = (ScreenHelper.getScreenWidth(this.activity) * width).toInt()
        }

        /**
         * 設置 高 為螢幕的多少比例
         * @param height range 0-1
         */
        fun setHeight(height: Float) = apply {
            this.layoutParam.height = (ScreenHelper.getScreenWidth(this.activity) * height).toInt()
        }

        fun setSize(width: Int = WindowManager.LayoutParams.WRAP_CONTENT, height: Int = WindowManager.LayoutParams.WRAP_CONTENT) = apply {
            this.layoutParam.width = width
            this.layoutParam.height = height
        }

        /**
         * 客製化 ui listener
         */
        fun setBuildListener(buildListener: IDialog.OnBuildListener) = apply { this.buildListener = buildListener }

        fun setPositiveButton(@StringRes text: Int, listener: IDialog.OnClickListener? = null) = apply {
            this.positiveText = this.activity.resources.getString(text)
            this.positiveListener = listener
            this.isShowPositive = true
        }

        fun setPositiveButton(text: String, listener: IDialog.OnClickListener? = null) = apply {
            this.positiveText = text
            this.positiveListener = listener
            this.isShowPositive = true
        }

        fun setNegativeButton(@StringRes text: Int, listener: IDialog.OnClickListener? = null) = apply {
            this.negativeText = this.activity.resources.getString(text)
            this.negativeListener = listener
            this.isShowNegative = true
        }

        fun setNegativeButton(text: String, listener: IDialog.OnClickListener? = null) = apply {
            this.negativeText = text
            this.negativeListener = listener
            this.isShowNegative = true
        }

        fun create(): IDialog {
            val dialog = newInstance(activity.supportFragmentManager, layoutParam)
            dialog.title = title
            dialog.message = message

            dialog.isShowPositive = isShowPositive
            dialog.positiveText = positiveText
            dialog.positiveButtonListener = positiveListener

            dialog.isShowNegative = isShowNegative
            dialog.negativeText = negativeText
            dialog.negativeButtonListener = negativeListener

            dialog.buildListener = buildListener
            dialog.dismissListener = dismissListener
            dialog.checkDataListener = checkDataListener

            return dialog
        }

        fun show(): IDialog {
            val dialog = create()
            dialog.showDialog()
            return dialog
        }
    }

    data class LayoutParam(
        var width: Int = WindowManager.LayoutParams.WRAP_CONTENT,
        var height: Int = WindowManager.LayoutParams.WRAP_CONTENT,
        @LayoutRes var customViewId: Int = 0,
        var isCancelableOutside: Boolean = false,//是否點擊外部取消 dialog
        var dimAmount: Float = 0.2f,
        var gravity: Int = Gravity.CENTER,
        var isCustomView: Boolean = false//是否放置 客製化畫面
    )
}