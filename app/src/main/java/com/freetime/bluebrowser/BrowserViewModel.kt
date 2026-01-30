package com.freetime.bluebrowser

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class SearchEngine(
    val name: String,
    val baseUrl: String,
    val queryParam: String = "q"
)

class BrowserViewModel : ViewModel() {
    private val searchEngines = listOf(
        SearchEngine("Google", "https://www.google.com/search"),
        SearchEngine("DuckDuckGo", "https://duckduckgo.com/", "q"),
        SearchEngine("Bing", "https://www.bing.com/search"),
        SearchEngine("Yahoo", "https://search.yahoo.com/search"),
        SearchEngine("Brave", "https://search.brave.com/search"),
        SearchEngine("Qwant", "https://www.qwant.com/", "q"),
        SearchEngine("Ecosia", "https://www.ecosia.org/search"),
        SearchEngine("Startpage", "https://www.startpage.com/do/search"),
        SearchEngine("Swisscows", "https://swisscows.com/web", "query"),
        SearchEngine("Yandex", "https://yandex.com/search/")
    )

    val currentUrl = mutableStateOf("https://www.bing.com")
    val canGoBack = mutableStateOf(false)
    val canGoForward = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val pageTitle = mutableStateOf("")
    val selectedSearchEngine = mutableStateOf(searchEngines[0])
    val showSearchEngineDialog = mutableStateOf(false)
    val bookmarks = mutableStateOf<List<Bookmark>>(emptyList())
    val showBookmarksDialog = mutableStateOf(false)
    val showHistoryDialog = mutableStateOf(false)
    val browsingHistory = mutableStateOf<List<HistoryItem>>(emptyList())

    fun getSearchEngines() = searchEngines

    fun selectSearchEngine(engine: SearchEngine) {
        selectedSearchEngine.value = engine
        showSearchEngineDialog.value = false
    }

    fun search(query: String) {
        val engine = selectedSearchEngine.value
        val searchUrl = "${engine.baseUrl}?${engine.queryParam}=${query.replace(" ", "+")}"
        currentUrl.value = searchUrl
    }

    fun addBookmark(title: String, url: String) {
        val newBookmark = Bookmark(title, url, System.currentTimeMillis())
        bookmarks.value = bookmarks.value + newBookmark
    }

    fun removeBookmark(bookmark: Bookmark) {
        bookmarks.value = bookmarks.value - bookmark
    }

    fun addToHistory(title: String, url: String) {
        val historyItem = HistoryItem(title, url, System.currentTimeMillis())
        browsingHistory.value = listOf(historyItem) + browsingHistory.value.take(99)
    }

    fun clearHistory() {
        browsingHistory.value = emptyList()
    }
}

data class Bookmark(
    val title: String,
    val url: String,
    val timestamp: Long
)

data class HistoryItem(
    val title: String,
    val url: String,
    val timestamp: Long
)
