package com.jrtou.gitviewer.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class RxSupportSHAbstractFragment<A : AppCompatActivity> : RxFragment() {
    companion object {
        private const val TAG = "SHAbstractFragment"
        private const val KEY_STATUS = "status"
    }

    var mActivity: A? = null

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(TAG, "onHiddenChanged (line 22): $hidden")
        mActivity?.let { if (hidden) onHidden(it) else if (isResumed) onShow(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate (line 28): $savedInstanceState")
        onDataRestoreState(savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.i(TAG, "onAttach (line 35): ")
        activity?.let { mActivity = it as A }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView (line 37): $savedInstanceState")
        return onInflaterView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated (line 41): $savedInstanceState")
        mActivity?.let { onViewSetting(it) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "onActivityCreated (line 49): $savedInstanceState")
        mActivity?.let { onRenderView(it, savedInstanceState) }
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach (line 58): ")
        mActivity = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState (line 60): $outState")
        outState.putBoolean(KEY_STATUS, isHidden)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i(TAG, "onViewStateRestored (line 70): $savedInstanceState")
        savedInstanceState?.let {
            it.putBoolean(KEY_STATUS, isHidden)
            onSaveState(it)
        }
    }

    /**
     * 恢復 show hide 狀態並且調用 [onRestoreState]
     */
    private fun onDataRestoreState(savedInstanceState: Bundle?) {
        Log.i(TAG, "onDataRestoreState (line 81): ")
        savedInstanceState?.let { bundle ->
            fragmentManager?.beginTransaction()?.let { transaction ->
                val isHidden = bundle.getBoolean(KEY_STATUS, false)
                if (isHidden) transaction.show(this)
                else transaction.hide(this)
                transaction.commit()
                onRestoreState(bundle)
            }
        }
    }

    /**
     * 插入 view
     */
    abstract fun onInflaterView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    /**
     * 初始化 ui 基本設定 ex setListener or setAdapter
     */
    abstract fun onViewSetting(activity: A)

    /**
     *請求數據 渲染至 ui 上
     */
    abstract fun onRenderView(activity: A, savedInstanceState: Bundle?)

    /**
     * activity 存在時 fragment 顯示
     */
    abstract fun onShow(activity: A)

    /**
     * activity 存在時 fragment 隱藏
     */
    abstract fun onHidden(activity: A)

    /**
     * fragment 重新建立恢復 bundle 處存資料
     */
    abstract fun onRestoreState(savedInstanceState: Bundle)

    /**
     * fragment 保存狀態時觸發 以保存 show hide 判斷在建立時候 判斷 show/hide
     */
    abstract fun onSaveState(outState: Bundle)
}