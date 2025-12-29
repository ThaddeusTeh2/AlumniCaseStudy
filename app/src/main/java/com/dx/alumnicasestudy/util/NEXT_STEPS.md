# AlumniCaseStudy – Project Summary and Next Steps (MVP Sprint 1)

Executive Summary
- Product: A closed alumni directory where approved alumni can log in, view/search the directory, and see basic profiles. Admins approve accounts.
- MVP (Sprint 1) goal: Prove closed access, manual admin approval, and searchable directory by name using Firebase Auth + Firestore only (no Functions, Storage, or FCM).
- Status: App scaffolded with Compose + Navigation; Firebase connected (google-services.json added, Google services plugin applied); SHA1 fingerprint added; Google Sign-In enabled; data/repo/DI mostly placeholders; screens partially built but unwired.

Architecture at a Glance (MVP)
- Mobile: Android (Kotlin, Jetpack Compose, Navigation).
- Backend: Firebase-only
  - Auth: Email/Password
  - Firestore: users collection (MVP fields only)
  - No Cloud Functions, FCM, or Storage in MVP.
- DI: Simple provider object (no Hilt for MVP).
- Navigation: Login → Register → PendingGate or Directory; Admin view for pending approvals.

Current Status Snapshot (Dec 24)
- Gradle/Manifest: ~90% (Google services plugin applied; google-services.json present; Firebase BOM/Auth added; Firestore dependency still to add).
- Navigation: ~90% (startDestination should be Login).
- UI: ~30% (Login/Register laid out, unwired; other screens placeholders).
- Data/Repo/DI: ~20% (files present, not implemented).
- ViewModel: ~20% (placeholder).
- Theming/Resources: 100%.
- Firebase Console setup: ~50% (project connected, SHA1 added, Google Sign-In enabled; Email/Password + Firestore DB enablement pending).

Critical Gaps vs Full SRS (non-optional in full, out-of-scope for MVP)
- Data fields absent: city, country, primary tech stack, contact preference and links, visibility flags, bio, photo URL, past jobs, skills/tags.
- User states/roles: rejected and inactive statuses; alumni vs user role naming.
- Admin workflows: reject, edit profiles, deactivate/restore, rejection_reason, metrics, role management, filtered admin search.
- Directory/search: filters (stack, location, year), combined filters, sorting, pagination, indexes.
- UI: profile editing, profile photos, contact actions, visibility toggles, rejected screen.
- Backend: Cloud Functions, FCM notifications, Storage.
- Non-functional: Security rules for roles/status; privacy toggles; offline caching.

Decisions and Constraints (MVP)
- Roles: Keep role = "user" and "admin" in MVP. "alumni" naming deferred to Phase 2.
- Statuses: Use "pending" and "approved" only for MVP; rejected/inactive deferred.
- Indexing: Only name_lowercase for case-insensitive prefix search in MVP.
- Admin: Minimal approve-only flow in-app; admin assignment set manually in Firestore.
- Security rules: Basic read rules for approved users; write restrictions to own doc; admin-only status changes planned post-MVP.

Environment Checklist (blocking)
- Add google-services.json in app/. (Done)
- Enable Google Sign-In in Firebase Auth. (Done)
- Generate and add SHA1 fingerprint in Firebase project settings. (Done)
- Enable Email/Password in Firebase Auth. (Pending)
- Create Firestore database (Production or Test). (Pending)
- Create at least one admin account; set role="admin" manually on users/{uid}. (Pending)

1‑Week MVP Plan (Milestones)
Day 1: Gradle + Firebase setup
- Add Firebase BOM, Auth, Firestore; apply Google services plugin; place google-services.json. (Plugin + json done; add Firestore dependency next.)
- Implement data model (User).
- Implement FirebaseAuthService and FirestoreService skeletons.

Day 2: Repository + DI + Nav start
- Implement AuthRepository (register/login/get/approve/isAdmin).
- Wire RepositoryProvider.
- Set startDestination to Login; route on login based on status.

Day 3: Login/Register flows
- Fix labels; validation; call repository.
- On register success → PendingGate; on login approved → Directory else → PendingGate.
- Minimal loading/error handling.

Day 4: Directory + Search
- Build DirectoryScreen: list approved users; search by name_lowercase prefix.
- Minimal ProfileScreen (read-only).

Day 5: Admin Pending List
- AdminPendingListScreen: list pending; Approve action; refresh.
- Gate route by role == admin.

Day 6: Cleanup + QA
- Empty states, error toasts/snacks, simple retry.
- Tidy ViewModel state management.

Day 7: Buffer + Acceptance
- Manual test against acceptance checklist; fix bugs.
- Prepare demo data.

Acceptance Criteria (MVP)
- New users can register; Auth user and Firestore doc created with status=pending, role=user.
- Login routes: approved → Directory; pending → PendingGate.
- Directory lists approved users and supports prefix name search (case-insensitive).
- Admin (role=admin) can see pending list and approve users.

Consolidated TODOs

Done
- Compose Material3 scaffold and theme
- Navigation routes scaffolded
- Screens and data/repo/di files created as placeholders
- Manifest launcher activity configured
- Firebase project connected; google-services.json added
- Google services plugin applied
- SHA1 fingerprint added
- Google Sign-In enabled in Firebase Auth

Needs Change (modify existing code)
- app/build.gradle.kts: add Firestore dependency (BOM/Auth already added; Google services plugin applied).
- ui/nav/navGraph.kt: startDestination → Login.
- ui/screens/login/LoginScreen.kt: fix field labels, wire login, add loading/error, navigate by status.
- ui/screens/register/RegisterScreen.kt: trim to MVP fields, validate, wire register, navigate to PendingGate.
- ui/viewmodels/HomeViewModel.kt: implement state + loaders + approveUser.
- di/RepositoryProvider.kt: provide services + repository.
- data/auth/FirebaseAuthService.kt: implement signUp/signIn/signOut/currentUser.
- data/firestore/FirestoreService.kt: implement create/get/query/approve.
- data/domain/repo/AuthRepository.kt: implement register/login/get/approve/isAdmin.
- ui/screens/pending/PendingGateScreen.kt: implement message + Logout.
- ui/screens/directory/DirectoryScreen.kt: implement list + search + tap → Profile.
- ui/screens/admin/AdminPendingListScreen.kt: implement approve list (admin-only).
- ui/screens/profile/ProfileScreen.kt: minimal read-only profile.

Still To Do (net new or external)
- Enable Email/Password and Firestore in Firebase Console (Google Sign-In already enabled).
- Basic Firestore Security Rules aligned to MVP roles/status.
- Seed one admin user (manual role=admin).
- Optional: remove HomeScreen.kt or repurpose.

Post‑MVP Backlog (Phase 2, to satisfy Full SRS)
- Data: location, stack, contact fields, visibility flags, bio, photo URL, skills, past jobs; updated_at.
- States/Roles: rejected, inactive, alumni naming; rejection_reason.
- Admin: reject, edit, deactivate/restore, metrics, filtered admin search, role management.
- Directory: filters (stack/location/year), combined queries, sorting, pagination, composite indexes.
- UX: profile edit, photo upload (Storage), contact actions, rejected screen.
- Backend: Cloud Functions (roles, notifications), FCM.
- Non-functional: richer Security Rules, offline caching, privacy toggles.

--- 

# AlumniCaseStudy – Next Steps / TODOs (Per File)

This document lists next steps for each file to guide implementation of the MVP.

## Root Gradle and Settings
- `build.gradle.kts`
  - Add Firebase BOM and dependencies for Authentication and Firestore.
  - Apply Google services plugin if using `google-services.json`. (Applied)
  - Ensure Kotlin/Compose versions align with Firebase.
- `settings.gradle.kts`
  - Verify plugin management and repositories.

## App Module Gradle and Manifest
- `app/build.gradle.kts`
  - Add dependencies:
    - platform("com.google.firebase:firebase-bom:<version>") (Added)
    - implementation("com.google.firebase:firebase-auth-ktx") (Added)
    - implementation("com.google.firebase:firebase-firestore-ktx") (Pending)
  - Apply plugin: id("com.google.gms.google-services") if using Google services. (Applied)
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
- Add `google-services.json` under `app/` (Done).
- Enable Authentication (Email/Password) and Firestore in Firebase Console (Google Sign-In already enabled).
- Create at least one admin user and set role="admin" in Firestore (manual for MVP).

## Acceptance Checkpoints
- Register → user created in Auth and Firestore with status=pending.
- Login → approved users reach Directory; others see Pending screen.
- Directory shows approved users and supports name search.
- Admin can approve pending users from AdminPendingList.
