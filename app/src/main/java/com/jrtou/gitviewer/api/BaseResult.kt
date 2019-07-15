package com.jrtou.gitviewer.api

open class BaseResult {


    var processStatus: ProcessStatusBean? = null

    class ProcessStatusBean {
        /**
         * code : 000
         * message : success
         * stackTrace : null
         */

        var code: String? = null
        var message: String? = null
        var token: String? = null
    }
}