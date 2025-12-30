# AlumniCaseStudy – Project Summary and Next Steps (MVP Sprint 1)

Executive Summary
- Product: A closed alumni directory where approved alumni can log in, view/search the directory, and see basic profiles. Admins approve accounts.
- MVP goal: Demonstrate closed access, manual admin approval, and searchable directory using Firebase Auth + Firestore only.
- Status: Compose + Navigation scaffolded; Firebase configured (google-services.json, plugin, SHA1, Google Sign-In). In-memory data/repo/DI implemented; screens partially built; wiring pending.

Architecture at a Glance (MVP)
- Mobile: Android (Kotlin, Jetpack Compose, Navigation).
- Backend: Firebase-only (Auth: Email/Password; Firestore: users collection).
- DI: Simple provider object (no Hilt in MVP).
- Navigation: Login → Register → PendingGate or Directory; Admin view for pending approvals.

Status (current)
- Gradle/Manifest:
  - Google services plugin applied in `app/build.gradle.kts`.
  - `google-services.json` present under `app/`.
  - Firebase BOM added; Firebase Auth present; Firestore dependency not yet added.
- Navigation:
  - `ui/nav/navGraph.kt` exists; startDestination is currently set to Directory (for testing). It should be Login for MVP.
- UI:
  - Login/Register/Pending/Directory/Admin screens exist as placeholders; no repository wiring yet.
- Data/Repo/DI:
  - Implemented in-memory: `FirebaseAuthService`, `FirestoreService`, `AuthRepository`, and `RepositoryProvider` singletons.
  - `User` model uses camelCase properties with Firestore snake_case mapping helpers.
  - `AuthRepository` includes a `RegisterData` DTO to avoid long parameter lists.
- ViewModel:
  - `HomeViewModel.kt` exists but is a placeholder (loaders/state not implemented).
- Theming/Resources: Ready.
- Firebase Console: Project connected; SHA1 added; Google Sign-In enabled; Email/Password + Firestore enablement pending.

Decisions and Constraints (MVP)
- Roles: "user" and "admin".
- Statuses: "pending" and "approved".
- Index: name_lowercase for case-insensitive prefix search.
- Admin: Approve-only in-app; admin role set manually in Firestore.
- Security rules: Basic MVP rules (approved users read; users write own doc; admin status updates) post-enable.

Environment Checklist (blocking)
- google-services.json under app/. (Done)
- Enable Google Sign-In in Firebase Auth. (Done)
- Add SHA1 fingerprint to Firebase project settings. (Done)
- Enable Email/Password in Firebase Auth. (Pending)
- Create Firestore database (Production or Test). (Pending)
- Seed at least one admin user; set role="admin" in users/{uid}. (Pending)

1‑Week MVP Plan (Milestones)
Day 1: Gradle + Firebase setup
- Add Firestore dependency (firebase-firestore-ktx). (Pending)
- Implement MVP data model (User). (Done; camelCase + toMap/fromMap)
- Implement FirebaseAuthService and FirestoreService skeletons. (Done in-memory)

Day 2: Repository + DI + Nav start
- Implement AuthRepository (register/login/get/approve/isAdmin). (Done in-memory; added RegisterData DTO)
- Wire RepositoryProvider. (Done)
- Verify Nav startDestination = Login; route based on status. (Pending; currently Directory)

Day 3: Login/Register flows
- Validate inputs; wire login/register to repository. (Pending)
- On register → PendingGate; on login approved → Directory else → PendingGate. (Pending)

Day 4: Directory + Search
- DirectoryScreen: list approved; prefix search by name_lowercase. (Service support ready; UI pending)
- Minimal ProfileScreen (read-only). (Pending)

Day 5: Admin Pending List
- AdminPendingListScreen: list pending; Approve action; refresh. (Service support ready; UI pending)
- Gate by role == admin. (Pending)

Day 6: Cleanup + QA
- Loading/error/empty states; simple retry. (Pending)
- Tidy ViewModel state. (Pending)

Day 7: Buffer + Acceptance
- Manual test; demo data prepared. (Pending)

Acceptance Criteria (MVP)
- Register creates Auth user and Firestore doc with status=pending, role=user.
- Login routes approved → Directory; pending → PendingGate.
- Directory lists approved users with prefix (case-insensitive) search.
- Admin (role=admin) can approve pending users from AdminPendingList.

Consolidated TODOs

Done
- Compose Material3 scaffold and theme
- Navigation routes scaffolded
- Screens and data/repo/di files created
- Manifest launcher activity configured
- Firebase project connected; google-services.json added
- Google services plugin applied
- SHA1 fingerprint added
- Google Sign-In enabled in Firebase Auth
- In-memory data layer implemented: FirebaseAuthService, FirestoreService (prefix search), AuthRepository (DTO + helpers)
- Simple DI provider added (RepositoryProvider)

Needs Change (modify existing code)
- app/build.gradle.kts: add Firestore dependency.
- ui/nav/navGraph.kt: set startDestination = Login (currently Directory).
- ui/screens/login/LoginScreen.kt: wire login; loading/error; navigate by status.
- ui/screens/register/RegisterScreen.kt: MVP fields; validation; wire register; navigate PendingGate (remove non-MVP extras like domain/city/photo for now).
- ui/viewmodels/HomeViewModel.kt: state; loaders; approveUser; routing helpers.
- data/auth/FirebaseAuthService.kt: replace in-memory with real Firebase Auth SDK later.
- data/firestore/FirestoreService.kt: replace in-memory with real Firebase Firestore SDK later.
- data/domain/repo/AuthRepository.kt: keep API stable; swap to real services later.
- ui/screens/pending/PendingGateScreen.kt: add Logout; polish text.
- ui/screens/directory/DirectoryScreen.kt: list + search + tap → Profile.
- ui/screens/admin/AdminPendingListScreen.kt: approve list (admin-only).
- ui/screens/profile/ProfileScreen.kt: minimal read-only profile.

Still To Do (external)
- Enable Email/Password and Firestore in Firebase Console.
- Basic Firestore Security Rules aligned to MVP roles/status.
- Seed one admin user (manual role=admin).
- Optional: remove or repurpose HomeScreen.kt.

Post‑MVP Backlog (Phase 2)
- Data: location, stack, contact fields, visibility flags, bio, photo URL, skills, past jobs; updated_at.
- States/Roles: rejected, inactive, alumni naming; rejection_reason.
- Admin: reject, edit, deactivate/restore, metrics, filtered admin search, role management.
- Directory: filters, combined queries, sorting, pagination, composite indexes.
- UX: profile edit, photo upload (Storage), contact actions, rejected screen.
- Backend: Cloud Functions (roles, notifications), FCM.
- Non-functional: richer Security Rules, offline caching, privacy toggles.

Progress Markers (update as you implement)
- Build: Firestore dependency resolves; app compiles.
- Auth: Register/login flows execute; Firebase console shows users.
- Firestore: users/{uid} created with status=pending, role=user; admin can approve.
- UI: Navigation routes correctly gate pending vs approved.
- Search: name_lowercase written; prefix search returns expected rows.

Dev Verification Checklist
- After register: verify Firestore doc fields: uid, name, name_lowercase, email, graduation_year, department, job_title, company, status=pending, role=user, created_at.
- After admin set role=admin (manual): AdminPendingList shows pending users; approve flips status to approved.
- After login approved: Directory shows user list; search by lowercase prefix.
- Security rules smoke test: approved users can read directory; users can write only their own doc; non-admins cannot approve.
