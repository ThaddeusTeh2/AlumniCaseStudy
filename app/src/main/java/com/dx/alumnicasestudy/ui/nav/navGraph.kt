package com.dx.alumnicasestudy.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dx.alumnicasestudy.ui.screens.AdminPendingListScreen
import com.dx.alumnicasestudy.ui.screens.DirectoryScreen
import com.dx.alumnicasestudy.ui.screens.LoginScreen
import com.dx.alumnicasestudy.ui.screens.PendingGateScreen
import com.dx.alumnicasestudy.ui.screens.RegisterScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        // For testing: set Home (Directory) as the default screen
        startDestination = Screens.Directory.route,
    ) {
        composable(Screens.Login.route) { LoginScreen() }
        composable(Screens.Register.route) { RegisterScreen() }
        composable(Screens.Directory.route) { DirectoryScreen() }
        composable(Screens.PendingGate.route) { PendingGateScreen() }
        composable(Screens.AdminPendingList.route) { AdminPendingListScreen() }
    }
}

