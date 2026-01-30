package com.freetime.bluebrowser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(
    viewModel: BrowserViewModel,
    onDismiss: () -> Unit
) {
    var showAboutDialog by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }
    var javascriptEnabled by remember { mutableStateOf(true) }
    var cookiesEnabled by remember { mutableStateOf(true) }

    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Settings") },
            text = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Card {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Appearance",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Dark Mode")
                                    Switch(
                                        checked = darkMode,
                                        onCheckedChange = { darkMode = it }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Card {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Privacy & Security",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Enable JavaScript")
                                        Text(
                                            text = "Required for most websites",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Switch(
                                        checked = javascriptEnabled,
                                        onCheckedChange = { javascriptEnabled = it }
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Enable Cookies")
                                        Text(
                                            text = "Allow websites to store data",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Switch(
                                        checked = cookiesEnabled,
                                        onCheckedChange = { cookiesEnabled = it }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Card {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Data Management",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Column {
                                    Button(
                                        onClick = { 
                                            viewModel.clearHistory()
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = null)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Clear History")
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = { 
                                            viewModel.bookmarks.value = emptyList()
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Outlined.Bookmark, contentDescription = null)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Clear Bookmarks")
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Card {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "About",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Button(
                                    onClick = { showAboutDialog = true },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.Info, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("About BlueBrowser")
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
}

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("About BlueBrowser") },
        text = {
            Column {
                Text(
                    text = "BlueBrowser v1.0",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("A modern, privacy-focused browser")
                Text("with no registration required.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Features:")
                Text("• Multiple search engines")
                Text("• Bookmarks")
                Text("• Browsing history")
                Text("• Privacy controls")
                Text("• No tracking")
                Text("• Open source")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Made with ❤️ for privacy",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}
