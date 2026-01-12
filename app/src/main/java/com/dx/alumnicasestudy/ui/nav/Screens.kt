package com.dx.alumnicasestudy.ui.nav

// Navigation routes scaffolding for MVP
// Define routes for:
// - Login
// - Register
// - Directory (Home)
// - PendingGate
// - AdminApprovals (admin reviews and approves users)

sealed class Screens(val route: String) {
    object Login: Screens("login")
    object Register: Screens("register")
    object Home: Screens("home")
    object Directory: Screens("directory")
    object PendingGate: Screens("pending")
    object AdminApprovals: Screens("admin_approvals")
    object Profile: Screens("profile/{userId}") {
        fun createRoute(userId: String) = "profile/$userId"
    }

    object MyProfile: Screens("profile")
    object Reject: Screens("reject")
}

// Note: Actual NavHost setup will be done in MainActivity with Compose Navigation.