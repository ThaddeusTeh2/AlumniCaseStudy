# AlumniCaseStudy – Next Steps / TODOs (Per File)

This document lists next steps for each file to guide implementation of the MVP.

## Root Gradle and Settings
- `build.gradle.kts`
  - Add Firebase BOM and dependencies for Authentication and Firestore.
  - Apply Google services plugin if using `google-services.json`.
  - Ensure Kotlin/Compose versions align with Firebase.
- `settings.gradle.kts`
  - Verify plugin management and repositories.

## App Module Gradle and Manifest
- `app/build.gradle.kts`
  - Add dependencies:
    - platform("com.google.firebase:firebase-bom:<version>")
    - implementation("com.google.firebase:firebase-auth-ktx")
    - implementation("com.google.firebase:firebase-firestore-ktx")
  - Apply plugin: id("com.google.gms.google-services") if using Google services.
  - Min/target SDK already set; confirm Compose and Navigation versions.
- `app/src/main/AndroidManifest.xml`
  - Add INTERNET permission if required (usually not for Firebase SDKs).
  - Verify application package name matches Firebase project settings.
  - Ensure default launcher activity is `MainActivity`.

## Data Layer
- `data/domain/models/User.kt`
  - Define data class with MVP fields (uid, name, name_lowercase, email, graduation_year, department, job_title, company, status, role, created_at).
  - Consider serialization annotations if needed.
- `data/auth/FirebaseAuthService.kt`
  - Implement email/password sign-up and login, sign-out, current user getter.
  - Expose auth state listener if helpful.
- `data/firestore/FirestoreService.kt`
  - Implement createUserDocument(uid, user), getUser(uid), queryApprovedUsers(namePrefix), queryPendingUsers(), approveUser(uid).
  - Add simple error handling (Result/Either) and timestamps.
- `data/domain/repo/AuthRepository.kt`
  - Implement register(...) → create Auth user, then Firestore `users/{uid}` with status=pending, role=user.
  - Implement login(email, password) → fetch profile and return.
  - Implement getCurrentUserProfile(), approveUser(uid), isAdmin(user).

## DI
- `di/RepositoryProvider.kt`
  - Provide singletons/factories for FirebaseAuthService, FirestoreService, and AuthRepository.
  - Optionally integrate with Hilt later; for MVP keep it simple.

## Navigation
- `ui/nav/Screens.kt`
  - Keep route constants. No code change required.

## UI – Activity
- `MainActivity.kt`
  - Set up NavHost with startDestination = Screens.Login.route.
  - Define composable destinations for Login, Register, Directory, PendingGate, AdminPendingList, Profile (read-only minimal).
  - Inject/construct ViewModels and repositories.

## UI – Screens
- `ui/screens/login/LoginScreen.kt`
  - Replace label placeholders with Email & Password.
  - Wire Login button → AuthRepository.login, then route based on profile.status.
  - Add basic error and loading states.
  - "Sign up!" navigates to Register.
- `ui/screens/register/RegisterScreen.kt`
  - Trim fields to MVP-required ones: name, email, graduation_year, department, job_title, company, password (+confirm local-only).
  - Validate inputs; call AuthRepository.register(...).
  - On success, navigate to PendingGate.
- `ui/screens/pending/PendingGateScreen.kt`
  - Simple static message: "Pending admin approval." and a Logout button.
- `ui/screens/directory/DirectoryScreen.kt`
  - Query and display approved users: name, year, job, company.
  - Add search box that filters by name (using name_lowercase starts-with).
  - Navigate to read-only Profile on item tap.
- `ui/screens/profile/ProfileScreen.kt`
  - Display minimal profile info; email visible only.
- `ui/screens/admin/AdminPendingListScreen.kt`
  - Display list where status=pending.
  - Approve action → FirestoreService.approveUser(uid) and refresh list.
  - Visible only if user.role == admin; otherwise hide route.
- `ui/screens/HomeScreen.kt`
  - Optional: remove if DirectoryScreen is used in its own file; or keep as wrapper.

## ViewModels
- `ui/viewmodels/HomeViewModel.kt`
  - Expose currentUserProfile and authState.
  - Provide functions: loadApprovedUsers(query), loadPendingUsers(), approveUser(uid).
  - Route state: approved vs pending.

## Theming
- `ui/theme/Color.kt`, `ui/theme/Theme.kt`, `ui/theme/Type.kt`
  - No changes required for MVP; ensure Material3 setup is fine.

## Resources
- `app/src/main/res/**`
  - Confirm app name in `strings.xml`.
  - No additional assets required for MVP.

## Project Utilities
- Create `util/README.md` if needed to capture conventions.
- Add simple logging wrapper later if helpful.

## Firebase Setup (Repo-level TODO)
- Add `google-services.json` under `app/` (not committed if private secrets policy applies).
- Enable Authentication (Email/Password) and Firestore in Firebase Console.
- Create at least one admin user and set role="admin" in Firestore (manual for MVP).

## Acceptance Checkpoints
- Register → user created in Auth and Firestore with status=pending.
- Login → approved users reach Directory; others see Pending screen.
- Directory shows approved users and supports name search.
- Admin can approve pending users from AdminPendingList.

