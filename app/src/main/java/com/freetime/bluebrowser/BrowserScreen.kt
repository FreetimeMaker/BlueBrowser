package com.freetime.bluebrowser

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserScreen(
    viewModel: BrowserViewModel = viewModel()
) {
    var urlText by remember { mutableStateOf("") }
    var showUrlBar by remember { mutableStateOf(true) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.currentUrl.value) {
        urlText = viewModel.currentUrl.value
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BlueBrowser") },
                actions = {
                    IconButton(onClick = { viewModel.showBookmarksDialog.value = true }) {
                        Icon(Icons.Default.Bookmark, contentDescription = "Bookmarks")
                    }
                    IconButton(onClick = { viewModel.showHistoryDialog.value = true }) {
                        Icon(Icons.Default.History, contentDescription = "History")
                    }
                    IconButton(onClick = { viewModel.showSearchEngineDialog.value = true }) {
                        Icon(Icons.Default.Search, contentDescription = "Search Engine")
                    }
                    IconButton(onClick = { showSettingsDialog = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            BrowserBottomBar(
                viewModel = viewModel,
                onUrlSubmit = { url ->
                    if (url.isNotBlank()) {
                        if (!url.startsWith("http://") && !url.startsWith("https://")) {
                            if (url.contains(".") && !url.contains(" ")) {
                                viewModel.currentUrl.value = "https://$url"
                            } else {
                                viewModel.search(url)
                            }
                        } else {
                            viewModel.currentUrl.value = url
                        }
                    }
                },
                urlText = urlText,
                onUrlChange = { urlText = it },
                showUrlBar = showUrlBar,
                onToggleUrlBar = { showUrlBar = !showUrlBar }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            WebViewContent(
                viewModel = viewModel,
                onPageTitleChanged = { title ->
                    viewModel.pageTitle.value = title
                    viewModel.addToHistory(title, viewModel.currentUrl.value)
                }
            )

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // Search Engine Selection Dialog
        if (viewModel.showSearchEngineDialog.value) {
            SearchEngineDialog(
                searchEngines = viewModel.getSearchEngines(),
                selectedEngine = viewModel.selectedSearchEngine.value,
                onEngineSelected = { engine -> viewModel.selectSearchEngine(engine) },
                onDismiss = { viewModel.showSearchEngineDialog.value = false }
            )
        }

        // Bookmarks Dialog
        if (viewModel.showBookmarksDialog.value) {
            BookmarksDialog(
                bookmarks = viewModel.bookmarks.value,
                onBookmarkClick = { url -> viewModel.currentUrl.value = url },
                onDismiss = { viewModel.showBookmarksDialog.value = false },
                onAddBookmark = { title, url -> viewModel.addBookmark(title, url) },
                onRemoveBookmark = { bookmark -> viewModel.removeBookmark(bookmark) }
            )
        }

        // History Dialog
        if (viewModel.showHistoryDialog.value) {
            HistoryDialog(
                history = viewModel.browsingHistory.value,
                onHistoryClick = { url -> viewModel.currentUrl.value = url },
                onDismiss = { viewModel.showHistoryDialog.value = false },
                onClearHistory = { viewModel.clearHistory() }
            )
        }

        // Settings Dialog
        if (showSettingsDialog) {
            SettingsDialog(
                viewModel = viewModel,
                onDismiss = { showSettingsDialog = false }
            )
        }
    }
}

@Composable
fun BrowserBottomBar(
    viewModel: BrowserViewModel,
    onUrlSubmit: (String) -> Unit,
    urlText: String,
    onUrlChange: (String) -> Unit,
    showUrlBar: Boolean,
    onToggleUrlBar: () -> Unit
) {
    Column {
        if (showUrlBar) {
            Surface(
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.canGoBack.value.takeIf { it }?.let { /* WebView go back */ } },
                        enabled = viewModel.canGoBack.value
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }

                    IconButton(
                        onClick = { viewModel.canGoForward.value.takeIf { it }?.let { /* WebView go forward */ } },
                        enabled = viewModel.canGoForward.value
                    ) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Forward")
                    }

                    OutlinedTextField(
                        value = urlText,
                        onValueChange = onUrlChange,
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Search or enter URL") },
                        trailingIcon = {
                            IconButton(onClick = { onUrlSubmit(urlText) }) {
                                Icon(Icons.Default.Search, contentDescription = "Go")
                            }
                        }
                    )

                    IconButton(onClick = onToggleUrlBar) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Hide URL bar")
                    }
                }
            }
        }

        Surface(
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!showUrlBar) {
                    IconButton(onClick = onToggleUrlBar) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Show URL bar")
                    }
                }
                
                IconButton(onClick = { /* Refresh */ }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
                
                IconButton(onClick = { /* Home */ }) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
                
                IconButton(onClick = { /* Tabs */ }) {
                    Icon(Icons.Default.Tab, contentDescription = "Tabs")
                }
                
                IconButton(onClick = { /* Menu */ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContent(
    viewModel: BrowserViewModel,
    onPageTitleChanged: (String) -> Unit
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.databaseEnabled = true
                settings.allowFileAccess = true
                settings.allowContentAccess = true
                
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        viewModel.isLoading.value = false
                        viewModel.canGoBack.value = canGoBack()
                        viewModel.canGoForward.value = canGoForward()
                        onPageTitleChanged(view?.title ?: "")
                    }
                    
                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        viewModel.isLoading.value = true
                        viewModel.currentUrl.value = url ?: ""
                    }
                }
                
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        // Update progress if needed
                    }
                    
                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        super.onReceivedTitle(view, title)
                        onPageTitleChanged(title ?: "")
                    }
                }
                
                loadUrl(viewModel.currentUrl.value)
            }
        },
        update = { webView ->
            if (webView.url != viewModel.currentUrl.value) {
                webView.loadUrl(viewModel.currentUrl.value)
            }
        }
    )
}

@Composable
fun SearchEngineDialog(
    searchEngines: List<SearchEngine>,
    selectedEngine: SearchEngine,
    onEngineSelected: (SearchEngine) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Search Engine") },
        text = {
            LazyColumn {
                items(searchEngines) { engine ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onEngineSelected(engine) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = engine.name == selectedEngine.name,
                            onClick = { onEngineSelected(engine) }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = engine.name,
                            fontWeight = if (engine.name == selectedEngine.name) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun BookmarksDialog(
    bookmarks: List<Bookmark>,
    onBookmarkClick: (String) -> Unit,
    onDismiss: () -> Unit,
    onAddBookmark: (String, String) -> Unit,
    onRemoveBookmark: (Bookmark) -> Unit
) {
    var showAddBookmark by remember { mutableStateOf(false) }
    
    if (showAddBookmark) {
        AddBookmarkDialog(
            onAdd = { title, url ->
                onAddBookmark(title, url)
                showAddBookmark = false
            },
            onDismiss = { showAddBookmark = false }
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Bookmarks") },
            text = {
                if (bookmarks.isEmpty()) {
                    Text("No bookmarks yet")
                } else {
                    LazyColumn {
                        items(bookmarks) { bookmark ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onBookmarkClick(bookmark.url) }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(bookmark.title, fontWeight = FontWeight.Bold)
                                    Text(bookmark.url, style = MaterialTheme.typography.bodySmall)
                                }
                                IconButton(onClick = { onRemoveBookmark(bookmark) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Row {
                    TextButton(onClick = { showAddBookmark = true }) {
                        Text("Add")
                    }
                    TextButton(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        )
    }
}

@Composable
fun AddBookmarkDialog(
    onAdd: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Bookmark") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("URL") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank() && url.isNotBlank()) {
                        onAdd(title, url)
                    }
                },
                enabled = title.isNotBlank() && url.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun HistoryDialog(
    history: List<HistoryItem>,
    onHistoryClick: (String) -> Unit,
    onDismiss: () -> Unit,
    onClearHistory: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("History")
                if (history.isNotEmpty()) {
                    TextButton(onClick = onClearHistory) {
                        Text("Clear All")
                    }
                }
            }
        },
        text = {
            if (history.isEmpty()) {
                Text("No history yet")
            } else {
                LazyColumn {
                    items(history) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onHistoryClick(item.url) }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                                Text(
                                    text = item.url,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 1
                                )
                                Text(
                                    text = dateFormat.format(Date(item.timestamp)),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
