package com.dx.alumnicasestudy.ui.nav

// Navigation routes scaffolding for MVP
// Define routes for:
// - Login
// - Register
// - Directory (Home)
// - PendingGate
// - AdminPendingList

sealed class Screens(val route: String) {
    object Login: Screens("login")
    object Register: Screens("register")
    object Directory: Screens("directory")
    object PendingGate: Screens("pending")
    object AdminPendingList: Screens("admin_pending")
}

// Note: Actual NavHost setup will be done in MainActivity with Compose Navigation.
