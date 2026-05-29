package com.ritikthakur.rtcalc.ui.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ritikthakur.rtcalc.data.repository.ThemeMode
import com.ritikthakur.rtcalc.ui.theme.DarkBackground
import com.ritikthakur.rtcalc.ui.theme.LightBackground
import com.ritikthakur.rtcalc.ui.theme.Orange
import com.ritikthakur.rtcalc.ui.viewmodel.CalculatorViewModel
import com.ritikthakur.rtcalc.ui.viewmodel.CurrencyViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(calculatorViewModel: CalculatorViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "calculator"

    val currencyViewModel: CurrencyViewModel = hiltViewModel()
    
    val themeMode by calculatorViewModel.themeMode.collectAsState()
    val systemDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> systemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val containerColor = if (isDark) DarkBackground else LightBackground
    val drawerContainerColor = if (isDark) Color(0xFF1C1C1E) else Color.White
    val contentColor = if (isDark) Color.White else Color.Black

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = drawerContainerColor,
                modifier = Modifier.width(280.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "RTCalc",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Orange,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = contentColor.copy(alpha = 0.1f))

                DrawerItem(
                    icon = Icons.Default.Menu,
                    label = "Calculator",
                    isSelected = currentRoute == "calculator"
                ) {
                    navController.navigate("calculator") {
                        popUpTo("calculator") { inclusive = true }
                    }
                    scope.launch { drawerState.close() }
                }

                DrawerItem(
                    icon = Icons.Default.List,
                    label = "History",
                    isSelected = currentRoute == "history"
                ) {
                    navController.navigate("history") { popUpTo("calculator") }
                    scope.launch { drawerState.close() }
                }

                DrawerItem(
                    icon = Icons.Default.List, // Change icon for currency
                    label = "Currency Converter",
                    isSelected = currentRoute == "currency"
                ) {
                    navController.navigate("currency") { popUpTo("calculator") }
                    scope.launch { drawerState.close() }
                }

                DrawerItem(
                    icon = Icons.Default.Settings,
                    label = "Settings",
                    isSelected = currentRoute == "settings"
                ) {
                    navController.navigate("settings") { popUpTo("calculator") }
                    scope.launch { drawerState.close() }
                }

                DrawerItem(
                    icon = Icons.Default.Info,
                    label = "About",
                    isSelected = currentRoute == "about"
                ) {
                    navController.navigate("about") { popUpTo("calculator") }
                    scope.launch { drawerState.close() }
                }

                DrawerItem(
                    icon = Icons.Default.Warning,
                    label = "Privacy Policy",
                    isSelected = currentRoute == "privacy"
                ) {
                    navController.navigate("privacy") { popUpTo("calculator") }
                    scope.launch { drawerState.close() }
                }

                DrawerItem(
                    icon = Icons.Default.Email,
                    label = "Contact",
                    isSelected = false
                ) {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:ritikthakur@duck.com")
                    }
                    context.startActivity(Intent.createChooser(intent, "Send Email"))
                    scope.launch { drawerState.close() }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "calculator",
            modifier = Modifier.fillMaxSize().background(containerColor)
        ) {
            composable("calculator") {
                CalculatorScreen(
                    viewModel = calculatorViewModel,
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }
            composable("currency") {
                CurrencyScreen(
                    viewModel = currencyViewModel,
                    isDark = isDark,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable("history") {
                HistoryScreen(
                    viewModel = calculatorViewModel,
                    isDark = isDark,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable("settings") {
                SettingsScreenWrapper(
                    viewModel = calculatorViewModel,
                    isDark = isDark,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable("about") {
                AboutScreen(
                    isDark = isDark,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable("privacy") {
                PrivacyScreen(
                    isDark = isDark,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Orange.copy(alpha = 0.15f) else Color.Transparent
    val contentColor = if (isSelected) Orange else MaterialTheme.colorScheme.onBackground

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(backgroundColor)
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        Icon(icon, contentDescription = label, tint = contentColor)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = contentColor
        )
    }
}
