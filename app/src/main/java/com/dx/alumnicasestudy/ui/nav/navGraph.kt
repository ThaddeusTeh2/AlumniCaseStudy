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
import com.dx.alumnicasestudy.ui.screens.profile.ProfileScreen
import com.dx.alumnicasestudy.ui.screens.register.RegisterScreen
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel

@Suppress("FunctionNaming")
@Composable
fun NavGraph(navController: NavHostController, vm: HomeViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route,
    ) {
        composable(Screens.Login.route) { LoginScreen(navController = navController, vm = vm) }
        composable(Screens.Register.route) { RegisterScreen(navController = navController, vm = vm) }
        composable(Screens.Home.route) { HomeScreen(navController = navController) }
        composable(Screens.Directory.route) { DirectoryScreen(navController = navController, vm = vm) }
        composable(Screens.PendingGate.route) { PendingGateScreen(navController = navController, vm = vm) }
        composable(Screens.AdminApprovals.route) { AdminPendingListScreen(vm = vm) }
        composable(Screens.Profile.route) { ProfileScreen(navController = navController, vm= vm) }
    }
}