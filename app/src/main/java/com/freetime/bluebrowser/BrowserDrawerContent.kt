package com.freetime.bluebrowser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserDrawerContent(
    onCloseDrawer: () -> Unit
) {
    var showNewTabDialog by remember { mutableStateOf(false) }
    var showDownloadsDialog by remember { mutableStateOf(false) }
    var showFindInPageDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "BlueBrowser",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                ListItem(
                    headlineContent = { Text("New Tab") },
                    leadingContent = { Icon(Icons.Default.Add, contentDescription = null) },
                    trailingContent = { Text("Ctrl+T") },
                    onClick = {
                        showNewTabDialog = true
                        onCloseDrawer()
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = { Text("New Incognito Tab") },
                    leadingContent = { Icon(Icons.Default.PersonOff, contentDescription = null) },
                    trailingContent = { Text("Ctrl+Shift+N") },
                    onClick = {
                        // Incognito functionality
                        onCloseDrawer()
                    }
                )
            }

            item {
                HorizontalDivider()
            }

            item {
                ListItem(
                    headlineContent = { Text("Downloads") },
                    leadingContent = { Icon(Icons.Default.Download, contentDescription = null) },
                    trailingContent = { Text("Ctrl+J") },
                    onClick = {
                        showDownloadsDialog = true
                        onCloseDrawer()
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = { Text("History") },
                    leadingContent = { Icon(Icons.Default.History, contentDescription = null) },
                    trailingContent = { Text("Ctrl+H") },
                    onClick = {
                        // History functionality
                        onCloseDrawer()
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = { Text("Bookmarks") },
                    leadingContent = { Icon(Icons.Default.Bookmark, contentDescription = null) },
                    trailingContent = { Text("Ctrl+Shift+B") },
                    onClick = {
                        // Bookmarks functionality
                        onCloseDrawer()
                    }
                )
            }

            item {
                HorizontalDivider()
            }

            item {
                ListItem(
                    headlineContent = { Text("Find in Page") },
                    leadingContent = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingContent = { Text("Ctrl+F") },
                    onClick = {
                        showFindInPageDialog = true
                        onCloseDrawer()
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = { Text("Desktop Site") },
                    leadingContent = { Icon(Icons.Default.Computer, contentDescription = null) },
                    onClick = {
                        // Desktop site functionality
                        onCloseDrawer()
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = { Text("Zoom") },
                    leadingContent = { Icon(Icons.Default.ZoomIn, contentDescription = null) },
                    onClick = {
                        // Zoom functionality
                        onCloseDrawer()
                    }
                )
            }

            item {
                HorizontalDivider()
            }

            item {
                ListItem(
                    headlineContent = { Text("Settings") },
                    leadingContent = { Icon(Icons.Default.Settings, contentDescription = null) },
                    onClick = {
                        // Settings functionality
                        onCloseDrawer()
                    }
                )
            }

            item {
                ListItem(
                    headlineContent = { Text("Help & Feedback") },
                    leadingContent = { Icon(Icons.Default.Help, contentDescription = null) },
                    onClick = {
                        // Help functionality
                        onCloseDrawer()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Version 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    // Dialogs
    if (showNewTabDialog) {
        AlertDialog(
            onDismissRequest = { showNewTabDialog = false },
            title = { Text("New Tab") },
            text = { Text("Open a new tab?") },
            confirmButton = {
                TextButton(onClick = { showNewTabDialog = false }) {
                    Text("Open")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewTabDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showDownloadsDialog) {
        AlertDialog(
            onDismissRequest = { showDownloadsDialog = false },
            title = { Text("Downloads") },
            text = { Text("No downloads yet") },
            confirmButton = {
                TextButton(onClick = { showDownloadsDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    if (showFindInPageDialog) {
        var searchText by remember { mutableStateOf("") }
        
        AlertDialog(
            onDismissRequest = { showFindInPageDialog = false },
            title = { Text("Find in Page") },
            text = {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Search text") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = { showFindInPageDialog = false }) {
                    Text("Find")
                }
            },
            dismissButton = {
                TextButton(onClick = { showFindInPageDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
