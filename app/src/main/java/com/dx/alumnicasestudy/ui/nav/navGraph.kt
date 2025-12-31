package com.dx.alumnicasestudy.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dx.alumnicasestudy.ui.screens.HomeScreen
import com.dx.alumnicasestudy.ui.screens.admin.AdminPendingListScreen
import com.dx.alumnicasestudy.ui.screens.directory.DirectoryScreen
import com.dx.alumnicasestudy.ui.screens.login.LoginScreen
import com.dx.alumnicasestudy.ui.screens.pending.PendingGateScreen
import com.dx.alumnicasestudy.ui.screens.register.RegisterScreen

@Suppress("FunctionNaming")
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route,
    ) {
        composable(Screens.Login.route) { LoginScreen(navController = navController) }
        composable(Screens.Register.route) { RegisterScreen(navController = navController) }
        composable(Screens.Home.route) { HomeScreen(navController = navController) }
        composable(Screens.Directory.route) { DirectoryScreen(navController = navController) }
        composable(Screens.PendingGate.route) { PendingGateScreen(navController = navController) }
        composable(Screens.AdminPendingList.route) { AdminPendingListScreen() }
    }
}
