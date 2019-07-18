package com.jrtou.gitviewer.api

import com.google.gson.annotations.SerializedName
import com.jrtou.gitviewer.R
import com.jrtou.gitviewer.views.adapters.BaseBindingAdapter

class GitHubData {

    /**
     * [query user 使用][com.jrtou.gitviewer.api.ApiInterface.getUserInfo]
     */
    data class User(
        @SerializedName("login") var login: String = "",
        @SerializedName("id") var id: Int = 0,
        @SerializedName("node_id") var nodeId: String = "",
        @SerializedName("avatar_url") var avatarUrl: String = "",
        @SerializedName("gravatar_id") var gravatarId: String = "",
        @SerializedName("url") var url: String = "",
        @SerializedName("html_url") var htmlUrl: String = "",
        @SerializedName("followers_url") var followersUrl: String = "",
        @SerializedName("following_url") var followingUrl: String = "",
        @SerializedName("gists_url") var gistsUrl: String = "",
        @SerializedName("starred_url") var starredUrl: String = "",
        @SerializedName("subscriptions_url") var subscriptionsUrl: String = "",
        @SerializedName("organizations_url") var organizationsUrl: String = "",
        @SerializedName("repos_url") var reposUrl: String = "",
        @SerializedName("events_url") var eventsUrl: String = "",
        @SerializedName("received_events_url") var receivedEventsUrl: String = "",
        @SerializedName("type") var type: String = "",
        @SerializedName("site_admin") var siteAdmin: Boolean = false,
        @SerializedName("name") var name: String = "",
        @SerializedName("company") var company: String = "",
        @SerializedName("blog") var blog: String = "",
        @SerializedName("location") var location: String = "",
        @SerializedName("email") var email: String = "",
        @SerializedName("hireable") var hireable: Boolean = false,
        @SerializedName("bio") var bio: String = "",
        @SerializedName("public_repos") var publicRepos: Int = 0,
        @SerializedName("public_gists") var publicGists: Int = 0,
        @SerializedName("followers") var followers: Int = 0,
        @SerializedName("following") var following: Int = 0,
        @SerializedName("created_at") var createdAt: String = "",
        @SerializedName("updated_at") var updatedAt: String = ""
    )

    //***** start Repo 使用 data *****//
    /**
     * [Search repo][com.jrtou.gitviewer.api.ApiInterface.searchRepoByKeyWord] 使用
     */
    data class Repo(
        @SerializedName("total_count") val totalCount: Int = 0,
        @SerializedName("incomplete_results") val incompleteResults: Boolean = false,
        @SerializedName("items") val items: MutableList<RepoItem> = mutableListOf()
    )

    data class RepoItem(
        @SerializedName("id") var id: Int = 0,
        @SerializedName("node_id") var nodeId: String = "",
        @SerializedName("name") var name: String = "",
        @SerializedName("full_name") var fullName: String = "",
        @SerializedName("private") var private: Boolean = false,
        @SerializedName("owner") var owner: GitHubData.Owner,
        @SerializedName("html_url") var htmlUrl: String = "",
        @SerializedName("description") var description: String = "",
        @SerializedName("fork") var fork: Boolean = false,
        @SerializedName("url") var url: String = "",
        @SerializedName("forks_url") var forksUrl: String = "",
        @SerializedName("keys_url") var keysUrl: String = "",
        @SerializedName("collaborators_url") var collaboratorsUrl: String = "",
        @SerializedName("teams_url") var teamsUrl: String = "",
        @SerializedName("hooks_url") var hooksUrl: String = "",
        @SerializedName("issue_events_url") var issueEventsUrl: String = "",
        @SerializedName("events_url") var eventsUrl: String = "",
        @SerializedName("assignees_url") var assigneesUrl: String = "",
        @SerializedName("branches_url") var branchesUrl: String = "",
        @SerializedName("tags_url") var tagsUrl: String = "",
        @SerializedName("blobs_url") var blobsUrl: String = "",
        @SerializedName("git_tags_url") var gitTagsUrl: String = "",
        @SerializedName("git_refs_url") var gitRefsUrl: String = "",
        @SerializedName("trees_url") var treesUrl: String = "",
        @SerializedName("statuses_url") var statusesUrl: String = "",
        @SerializedName("languages_url") var languagesUrl: String = "",
        @SerializedName("stargazers_url") var stargazersUrl: String = "",
        @SerializedName("contributors_url") var contributorsUrl: String = "",
        @SerializedName("subscribers_url") var subscribersUrl: String = "",
        @SerializedName("subscription_url") var subscriptionUrl: String = "",
        @SerializedName("commits_url") var commitsUrl: String = "",
        @SerializedName("git_commits_url") var gitCommitsUrl: String = "",
        @SerializedName("comments_url") var commentsUrl: String = "",
        @SerializedName("issue_comment_url") var issueCommentUrl: String = "",
        @SerializedName("contents_url") var contentsUrl: String = "",
        @SerializedName("compare_url") var compareUrl: String = "",
        @SerializedName("merges_url") var mergesUrl: String = "",
        @SerializedName("archive_url") var archiveUrl: String = "",
        @SerializedName("downloads_url") var downloadsUrl: String = "",
        @SerializedName("issues_url") var issuesUrl: String = "",
        @SerializedName("pulls_url") var pullsUrl: String = "",
        @SerializedName("milestones_url") var milestonesUrl: String = "",
        @SerializedName("notifications_url") var notificationsUrl: String = "",
        @SerializedName("labels_url") var labelsUrl: String = "",
        @SerializedName("releases_url") var releasesUrl: String = "",
        @SerializedName("deployments_url") var deploymentsUrl: String = "",
        @SerializedName("created_at") var createdAt: String = "",
        @SerializedName("updated_at") var updatedAt: String = "",
        @SerializedName("pushed_at") var pushedAt: String = "",
        @SerializedName("git_url") var gitUrl: String = "",
        @SerializedName("ssh_url") var sshUrl: String = "",
        @SerializedName("clone_url") var cloneUrl: String = "",
        @SerializedName("svn_url") var svnUrl: String = "",
        @SerializedName("homepage") var homepage: String = "",
        @SerializedName("size") var size: Int = 0,
        @SerializedName("stargazers_count") var stargazersCount: Int = 0,
        @SerializedName("watchers_count") var watchersCount: Int = 0,
        @SerializedName("language") var language: String = "",
        @SerializedName("has_issues") var hasIssues: Boolean = false,
        @SerializedName("has_projects") var hasProjects: Boolean = false,
        @SerializedName("has_downloads") var hasDownloads: Boolean = false,
        @SerializedName("has_wiki") var hasWiki: Boolean = false,
        @SerializedName("has_pages") var hasPages: Boolean = false,
        @SerializedName("forks_count") var forksCount: Int = 0,
        @SerializedName("mirror_url") var mirrorUrl: String = "",
        @SerializedName("archived") var archived: Boolean = false,
        @SerializedName("disabled") var disabled: Boolean = false,
        @SerializedName("open_issues_count") var openIssuesCount: Int = 0,
        @SerializedName("license") var license: GitHubData.License,
        @SerializedName("forks") var forks: Int = 0,
        @SerializedName("open_issues") var openIssues: Int = 0,
        @SerializedName("watchers") var watchers: Int = 0,
        @SerializedName("default_branch") var defaultBranch: String = "",
        @SerializedName("score") var score: Double = 0.0
    ) : BaseBindingAdapter.HolderItemType {
        override fun getLayoutRes(): Int {
            return R.layout.item_rv_trend_repo
        }
    }


    data class Owner(
        @SerializedName("login") val login: String = "",
        @SerializedName("id") val id: Int = 0,
        @SerializedName("node_id") val nodeId: String = "",
        @SerializedName("avatar_url") val avatarUrl: String = "",
        @SerializedName("gravatar_id") val gravatarId: String = "",
        @SerializedName("url") val url: String = "",
        @SerializedName("html_url") val htmlUrl: String = "",
        @SerializedName("followers_url") val followersUrl: String = "",
        @SerializedName("following_url") val followingUrl: String = "",
        @SerializedName("gists_url") val gistsUrl: String = "",
        @SerializedName("starred_url") val starredUrl: String = "",
        @SerializedName("subscriptions_url") val subscriptionsUrl: String = "",
        @SerializedName("organizations_url") val organizationsUrl: String = "",
        @SerializedName("repos_url") val reposUrl: String = "",
        @SerializedName("events_url") val eventsUrl: String = "",
        @SerializedName("received_events_url") val receivedEventsUrl: String = "",
        @SerializedName("type") val type: String = "",
        @SerializedName("site_admin") val siteAdmin: Boolean = false
    )

    data class License(
        @SerializedName("key") val key: String = "",
        @SerializedName("name") val name: String = "",
        @SerializedName("spdx_id") val spdxId: String = "",
        @SerializedName("url") val url: String = "",
        @SerializedName("node_id") val nodeId: String = ""
    )
    //***** end Repo 使用 data *****//

    //***** start Trend 使用 data *****//
    /**
     * Trend APi 回傳
     */
    data class TrendItem(
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
        @SerializedName("builtBy") var builtBy: MutableList<BuiltBy> = mutableListOf()
    ): BaseBindingAdapter.HolderItemType {
        override fun getLayoutRes(): Int {
            return R.layout.item_rv_trend_repo
        }
    }

    data class BuiltBy(
        @SerializedName("username") var username: String = "",
        @SerializedName("href") var href: String = "",
        @SerializedName("avata") var avata: String = ""
    )

    //***** end Trend 使用 data *****//
}