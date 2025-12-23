package com.dx.alumnicasestudy.ui.screens

// Directory/Home screen scaffolding
// Purpose:
// - Show list of approved alumni (name, graduation year, job title, company)
// - Provide a simple search by name (case-insensitive using name_lowercase)
// Composables to define:
// - @Composable fun DirectoryScreen(...)
// - @Composable fun PendingGateScreen() // shows "Pending admin approval"
// - @Composable fun AdminPendingListScreen(...) // list of users with status=pending, approve action
// ViewModel interactions:
// - Fetch approved users
// - Perform search queries
// - Approve pending users (admin)
// Navigation:
// - Enforce closed network: route to PendingGate if status != approved
