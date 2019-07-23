package com.jrtou.gitviewer.databases.schemas

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jrtou.gitviewer.R
import com.jrtou.gitviewer.api.GitHubData
import com.jrtou.gitviewer.databases.schemas.TrendSchema.Companion.tableName
import com.jrtou.gitviewer.views.adapters.BaseBindingAdapter

/**
 * Trend APi 回傳
 */
@Entity(tableName = tableName)
data class TrendSchema(
    @SerializedName("author") var author: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("avatar") var avatar: String = "",
    @SerializedName("url") var url: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("language") var language: String = "",
    @SerializedName("languageColor") var languageColor: String = "",
    @SerializedName("stars") var stars: String = "",
    @SerializedName("forks") var forks: String = "",
    @SerializedName("currentPeriodStars") var currentPeriodStars: String = "",
    @SerializedName("builtBy") var builtBy: MutableList<GitHubData.BuiltBy> = mutableListOf()
) : BaseBindingAdapter.HolderItemType {
    companion object {
        @Ignore
        const val tableName = "trend"
    }

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun getLayoutRes(): Int {
        return R.layout.item_rv_trend_repo
    }
}