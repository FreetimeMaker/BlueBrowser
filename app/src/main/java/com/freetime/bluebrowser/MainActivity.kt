package com.freetime.bluebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.freetime.bluebrowser.ui.theme.BlueBrowserTheme
import kotlinx.coroutines.launch
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlueBrowserTheme {
                BrowserScreenWithDrawer()
            }
        }
    }
}

@Composable
fun BrowserScreenWithDrawer() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    val colorScheme = MaterialTheme.colorScheme
    val useDarkIcons = colorScheme.primary == colorScheme.surface

    // Hide system bars
    LaunchedEffect(Unit) {
        systemUiController.isStatusBarVisible = false
        systemUiController.isNavigationBarVisible = false
        systemUiController.setSystemBarsColor(
            color = colorScheme.surface,
            darkIcons = useDarkIcons
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                BrowserDrawerContent(
                    onCloseDrawer = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            BrowserScreen(
                onMenuClick = { scope.launch { drawerState.open() } }
            )
        }
    }
}