package com.jrtou.gitviewer.helpers

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.lang.ref.WeakReference

/**
 * @param fragmentManager support fragment manager
 * @param layoutId 需要顯示的 view group id
 */
open class SHFragmentHelper(fragmentManager: FragmentManager, @IdRes private var layoutId: Int) {
    companion object {
        private const val TAG = "SHFragmentHelper"

        private const val GlideFragmentTag: String = "SupportRequestManagerFragment"
        const val HOME = "HOME"
    }

    //activity 弱引用
    private var reference: WeakReference<FragmentManager>

    private val onBackListener = FragmentManager.OnBackStackChangedListener {
        Log.i(TAG, "onBackStackChanged")
        getFragmentManager()?.let { fragmentManager ->
            val fragmentList = fragmentManager.fragments

            var glideFragment: Fragment? = null
            for (f: Fragment in fragmentList) {
                if (f::class.java.simpleName == GlideFragmentTag) {
                    glideFragment = f
                    break
                }
            }

            if (glideFragment != null) fragmentList.remove(glideFragment)//移除 glide 加載 fragment
        } ?: run { Log.i(TAG, "onBackStackChanged fragment manager is null.") }
    }

    init {
        fragmentManager.addOnBackStackChangedListener(onBackListener)
        reference = WeakReference(fragmentManager)
    }

    /**
     * 添加 fragment<br>
     */
    fun addFragment(fragment: Fragment, backStack: String?) {
        val addTag = fragment::class.java.simpleName
        Log.i(TAG, "addFragment: fragment add tag = $addTag")

        reference.get()?.let { fragmentManager ->
            val currentFragment = getCurrentFragment()
            val addFragment = findFragmentByTag(addTag)
            Log.e(TAG, "addFragment current = $currentFragment")

            currentFragment?.let { current ->
                if (current.javaClass.simpleName != addTag) {
                    val trans = fragmentManager.beginTransaction().addToBackStack(backStack)

                    addFragment?.let { add ->
                        Log.i(TAG, "addFragment 已存在 是否已添加 ${add.isAdded}")
                        if (add.isAdded) trans.show(add).hide(currentFragment)
                        else trans.hide(currentFragment).add(layoutId, add, addTag)

                        trans.commitAllowingStateLoss()
                    } ?: run {
                        Log.i(TAG, "addFragment 不存在")
                        trans.hide(current).add(layoutId, fragment, addTag).commitAllowingStateLoss()
                    }
                } else Log.i(TAG, "addFragment 當前畫面 與 切換畫面為同一個")
            } ?: run {
                Log.w(TAG, "addFragment: 第一次加入 fragment(通常為首頁添加)")
                fragmentManager.beginTransaction()
                    .addToBackStack(backStack)
                    .add(layoutId, fragment, addTag)
                    .commitAllowingStateLoss()
            }
        } ?: run { Log.w(TAG, "addFragment fragment manager is null.") }
    }

    /**
     * 添加首頁
     * @see addFragment(fragment: Fragment, backStack: String?)
     */
    fun addHomeFragment(fragment: Fragment, backStack: String = HOME) {
        addFragment(fragment, backStack)
    }

    /**
     * 添加 fragment帶入 backStack
     * @see addFragment
     */
    fun addFragment(fragment: Fragment) {
        addFragment(fragment, null)
    }

    /**
     * 獲取當前顯示 fragment
     * @return [Fragment?]
     */
    fun getCurrentFragment(): Fragment? {
        return reference.get()?.findFragmentById(layoutId)
    }

    /**
     * 透過 fragment tag 尋找 fragment
     * @param fragmentTag 呼叫 add 時傳入的　tag
     */
    fun findFragmentByTag(fragmentTag: String): Fragment? {
        reference.get()?.let { return it.findFragmentByTag(fragmentTag) } ?: run { return null }
    }

    fun backToStack(stack: String) {
        Log.i(TAG, "backToStack $stack")
        val current = getCurrentFragment()
        current?.let {
            if (current.javaClass.simpleName == stack) Log.i(TAG, "backToHome 已經退回畫面")
            else reference.get()?.popBackStackImmediate(stack, 0) ?: Log.i(TAG, "backToHome fragment manager is null.")
        } ?: run { reference.get()?.popBackStackImmediate(stack, 0) ?: Log.i(TAG, "backToHome fragment manager is null.") }
    }

    /**
     * 使用內建　home stack
     */
    fun backToHome() {
        backToStack(HOME)
    }

    fun getFragmentManager(): FragmentManager? {
        return reference.get()
    }

    fun release() {
        reference.clear()
    }
}