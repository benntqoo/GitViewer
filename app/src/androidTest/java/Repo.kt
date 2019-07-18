package <default> (D:\WorkSpace\Android\MyProject\GitViewer\app\src\androidTest\java)

import com.google.gson.annotations.SerializedName

data class Repo(@SerializedName("total_count")
                val totalCount: Int = 0,
                @SerializedName("incomplete_results")
                val incompleteResults: Boolean = false,
                @SerializedName("items")
                val items: List<ItemsItem>?)