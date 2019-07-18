package com.jrtou.gitviewer.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @param isAutoRefresh 自否將數據觀察者綁定自動刷新 adapter 數據 預設綁定
 */
abstract class BaseBindingAdapter<DATA : BaseBindingAdapter.HolderItemType, BIND : ViewDataBinding>(isAutoRefresh: Boolean = true) :
    RecyclerView.Adapter<BaseBindingAdapter.BindingViewHolder>() {

    companion object {
        private const val TAG = "BaseBindingAdapter"
    }

    var items = ObservableArrayList<DATA>()


    /**
     * bind view holder
     */
    abstract fun onBindHolder(binding: BIND, item: DATA)

    init {
        if (isAutoRefresh) setupList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): BindingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: BIND = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return BindingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val binding: BIND? = DataBindingUtil.getBinding(holder.itemView)
        binding?.let { onBindHolder(it, items[position]) }
            ?: run { Log.i(TAG, "onBindViewHolder : binding is null.") }
    }

    @LayoutRes
    override fun getItemViewType(position: Int) = items[position].getLayoutRes()

    override fun getItemCount() = items.size

    /**
     * 設置 數據刷新後自動 呼叫 notify 相關方法
     */
    private fun setupList() {
        items.addOnListChangedCallback(OnDataChangeListener<DATA>())
    }

    /**
     * 統一使用 holder 需客製化功能請在繼承類中的 [onBindHolder][onBindHolder] 實作
     */
    class BindingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    /**
     * 使用內部類別調用 [adapter][BaseBindingAdapter] 中的
     *
     * [notifyDataSetChanged][notifyDataSetChanged] ,
     * [notifyItemRangeRemoved][notifyItemRangeRemoved] ,
     * [notifyItemRangeInserted][notifyItemRangeInserted] ,
     * [notifyItemRangeChanged][notifyItemRangeChanged] , 動態刷新 adapter 中數據
     */
    inner class OnDataChangeListener<D> : ObservableList.OnListChangedCallback<ObservableArrayList<D>>() {
        override fun onChanged(sender: ObservableArrayList<D>?) {
            notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(sender: ObservableArrayList<D>?, positionStart: Int, itemCount: Int) {
            notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(sender: ObservableArrayList<D>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            if (itemCount == 1) notifyItemMoved(fromPosition, toPosition)
            else notifyDataSetChanged()
        }

        override fun onItemRangeInserted(sender: ObservableArrayList<D>?, positionStart: Int, itemCount: Int) {
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(sender: ObservableArrayList<D>?, positionStart: Int, itemCount: Int) {
            notifyItemRangeChanged(positionStart, itemCount)
        }
    }

    /**
     * 讓 data 與 layoutRes 做綁定
     *
     * 且可自訂 header 與 footer 不須再額外判斷 [getItemViewType][getItemViewType] 與 [getItemCount][getItemCount]
     */
    interface HolderItemType {
        @LayoutRes
        fun getLayoutRes(): Int
    }
}