package com.jrtou.gitviewer.api


class ApiQuery {
    data class SearchRepo(val keyword: String, val language: String) {
        override fun toString(): String {
            return "$keyword+language:$language"
        }
    }
}