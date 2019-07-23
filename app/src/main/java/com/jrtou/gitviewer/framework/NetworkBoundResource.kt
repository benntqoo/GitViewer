package com.jrtou.gitviewer.framework

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData


/**
 * https://developer.android.com/jetpack/docs/guide?source=post_page---------------------------#addendum
 *
 * @param ResultType Type for the Resource data.
 * @param RequestType  Type for the API response.
 *
 * 資料基礎控制項目
 */
abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
//                    setValue(Resource.success(newData))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
//        val apiResponse = createCall()
//        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
//        result.addSource(dbSource) { newData ->
//            setValue(Resource.loading(newData))
//        }
//        result.addSource(apiResponse) { response ->
//            result.removeSource(apiResponse)
//            result.removeSource(dbSource)
//            when (response) {
//                is ApiSuccessResponse -> {
//                    appExecutors.diskIO().execute {
//                        saveCallResult(processResponse(response))
//                        appExecutors.mainThread().execute {
//                            // we specially request a new live data,
//                            // otherwise we will get immediately last cached value,
//                            // which may not be updated with latest results received from network.
//                            result.addSource(loadFromDb()) { newData ->
//                                setValue(Resource.success(newData))
//                            }
//                        }
//                    }
//                }
//                is ApiEmptyResponse -> {
//                    appExecutors.mainThread().execute {
//                        // reload from disk whatever we had
//                        result.addSource(loadFromDb()) { newData ->
//                            setValue(Resource.success(newData))
//                        }
//                    }
//                }
//                is ApiErrorResponse -> {
//                    onFetchFailed()
//                    result.addSource(dbSource) { newData ->
//                        setValue(Resource.error(response.errorMessage, newData))
//                    }
//                }
//            }
//        }
    }

    /**
     * Called to save the result of the API response into the database
     *
     * 將API取得的資料存進資料庫
     */
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    /**
     * Called with the data in the database to decide whether to fetch
     *
     * potentially updated data from the network.
     *
     * 依照撈出的資料決定是否要發出API連線。
     */
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    /**
     * Called to get the cached data from the database.
     *
     * 從資料庫撈出資料
     */
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    /**
     * Called to create the API call.
     *
     * 發出API連線
     */
    @MainThread
    protected abstract fun createCall(): LiveData<RequestType>

    /**
     *   Called when the fetch fails. The child class may want to reset components like rate limiter.
     */
    protected open fun onFetchFailed() {}

    /**
     *  Returns a LiveData object that represents the resource that's implemented in the base class.
     */
    fun asLiveData(): LiveData<ResultType> = TODO()
}