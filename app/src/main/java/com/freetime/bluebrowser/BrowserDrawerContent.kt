package com.freetime.bluebrowser

import androidx.compose.foundation.clickable
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showNewTabDialog = true
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("New Tab")
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Ctrl+T")
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Incognito functionality
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.PersonOff, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("New Incognito Tab")
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Ctrl+Shift+N")
                }
            }

            item {
                HorizontalDivider()
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showDownloadsDialog = true
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Downloads")
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Ctrl+J")
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // History functionality
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.History, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("History")
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Ctrl+H")
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Bookmarks functionality
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Bookmark, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Bookmarks")
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Ctrl+Shift+B")
                }
            }

            item {
                HorizontalDivider()
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showFindInPageDialog = true
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Search, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Find in Page")
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Ctrl+F")
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Desktop site functionality
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Computer, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Desktop Site")
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Zoom functionality
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ZoomIn, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Zoom")
                }
            }

            item {
                HorizontalDivider()
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Settings functionality
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Settings")
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Help functionality
                            onCloseDrawer()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Help, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Help & Feedback")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Version 1.0.1",
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
