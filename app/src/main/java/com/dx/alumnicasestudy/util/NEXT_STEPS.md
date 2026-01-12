# AlumniCaseStudy – MVP Todo Checklist (Sanity-checked)

Last checked: Jan 7

High-level
- Goal: Closed alumni directory (Auth + Firestore). Pending users see gate; approved users see directory; admin approves.
- Tech: Kotlin + Compose + Navigation; Firebase (Auth, Firestore).

Repositories & Build
- [Done] `google-services.json` present under `app/`.
- [Done] Google services plugin applied in `app/build.gradle.kts`.
- [Done] Firebase Auth + Firestore dependencies present.
- [Partial] Version catalog in use, but several direct strings remain (Firebase BOM, Firestore, Coil 2/3, Material Icons). Unify via `libs.versions.toml`.
- [Pending] Migrate Kotlin `jvmTarget` to the `compilerOptions` DSL.
- [Pending] DI decision: either fully adopt Hilt (plugins, deps, @HiltAndroidApp) or remove Hilt annotations. Currently `androidx.hilt.navigation.compose` is present but no Hilt setup, and a Hilt `HomeViewModel` exists unused.

Navigation
- [Done] `ui/nav/navGraph.kt` exists with `startDestination = Login` and routes.
- [Pending] MainActivity wires NavHost. Current `MainActivity` still renders a placeholder `Greeting` instead of `NavGraph`.
- [Pending] Provide a single shared `HomeViewModel` instance to NavHost. Some screens create/expect a shared VM, but MainActivity does not wire it yet.

UI Screens (wiring)
- Login
  - [Done] Labels: Email/Password; calls `vm.login` and routes by role/status.
  - [Pending] Show loading/error feedback (none shown yet in Login).
- Register
  - [Done] MVP fields only: name, email, password(+confirm), graduation_year, department, job_title, company.
  - [Done] Simple validation; calls `vm.register`; routes by status (pending → PendingGate).
  - [Pending] UX polish (errors/snackbar, disabled states, keyboard types).
- PendingGate
  - [Done] Logout button → Login. If admin, link to Home.
  - [Pending] Copy polish.
- Directory
  - [Done] List approved users; search by name prefix via `vm.loadApproved(namePrefix)`.
  - [Partial] Filter dropdown UI added (TechStack/Location/Graduation, Asc/Desc) but NOT wired to sorting use case; it only updates selection and reloads `vm.loadApproved()`.
  - [Pending] Navigate to read-only Profile on item tap (currently top app bar Person icon navigates to Profile, not item rows).
  - [Pending] Empty state / loading feedback.
- Admin Approvals
  - [Done] List pending users; Approve action; refresh list; guarded by admin check.
- Profile (read-only)
  - [Partial] Screen scaffold exists with placeholder content and image. Not bound to a selected user; mixed Coil v2/v3 imports.

ViewModel
- [Done] Legacy `ui/viewmodels/HomeViewModel` implements login, register, loadApproved, loadPending, approveUser, signOut; used by screens.
- [Partial] A second `ui/screens/home/HomeViewModel` (Hilt) holds sorting `orderBy` state and calls `GetUsersUseCase`, but is not collected (`launchIn`) and is not used by any screen/NavHost.
- [Pending] Unify to a single `HomeViewModel` and integrate sorting state end-to-end. Either:
  - Fold sorting into the existing VM and repo, or
  - Finish Hilt setup and migrate screens to the Hilt VM using `stateFlow` and `collectAsState`.
- [Pending] Basic loading/empty/error state plumbing for lists.

Data Layer
- `User`
  - [Current] Uses snake_case fields (graduation_year, job_title, name_lowercase, created_at) matching Firestore schema.
  - [Optional] Later: camelCase properties with map adapters.
- `FirebaseAuthService`
  - [Done] Email/Password flows with `await()` wrappers; `authState` exposed.
- `FirestoreService`
  - [Done] Create/get/query/approve. Approved list supports name-prefix filtering and sorts by `name_lowercase`.
- `AuthRepository`
  - [Done] register/login/signOut/loadApproved/loadPending/approve wired to Firebase services; returns `Result` and lists.
- `GetUsersUseCase`
  - [Partial] Sorting implemented for three keys, but mappings don’t match UI labels: TechStack→`role`, Location→`company`, Graduation→`graduation_year`.
  - [Pending] Align sorting keys and UI labels (e.g., decide on Department/Job Title vs Tech Stack, which isn’t in the model) and plumb selection from Directory to the use case.
- `BaseUseCase`
  - [Pending] Replace `coil.network.HttpException`/`okio.IOException` with appropriate exceptions (e.g., retrofit2.HttpException or generic Exception) or remove if not using Retrofit.

DI
- [Done] `RepositoryProvider` provides singletons (Auth, Firestore, Repo) used by current screens.
- [Pending] Decide between RepositoryProvider-only DI (no Hilt) vs full Hilt. If Hilt: add Gradle plugin/deps, `@HiltAndroidApp` Application, injected constructors; otherwise remove Hilt annotations and unused deps.

Firebase Console
- [Done] Project connected (per config); `google-services.json` present.
- [Pending] Enable Email/Password in Auth (console).
- [Pending] Create Firestore database.
- [Pending] Seed an admin user with `role = "admin"` on `users/{uid}`.
- [Pending] MVP security rules (approved read; user can write own doc; admin approves).

Acceptance (MVP)
- [Partial] Register creates Auth user + Firestore `users/{uid}` with status=pending, role=user (app code ready; needs console setup).
- [Done] Login routing: admin → Home; approved → Directory; pending → PendingGate (app code wired).
- [Partial] Directory lists approved and supports case-insensitive prefix search; sorting UI present but not functional.
- [Partial] Admin can approve pending users in-app.

Quick dev notes
- Build cleanup: move remaining direct deps to the version catalog; remove duplicates; pick a single Coil major (v2 or v3) and fix imports accordingly.
- Duplicate components to reconcile:
  - Two `HomeViewModel` classes (only the legacy one is used). Merge or delete the unused Hilt VM after deciding DI.
  - Two `DirectoryScreen` composables exist (one placeholder in `ui/screens/HomeScreen.kt` and the real one in `ui/screens/directory/DirectoryScreen.kt`). The NavGraph uses the real one; consider removing/renaming the placeholder to avoid confusion.
- MainActivity: replace placeholder `Greeting` with `NavGraph(navController, vm)` and provide a single shared `HomeViewModel`.
- Sorting contract: finalize fields and propagate selection from Directory → VM → UseCase. If staying without use case, implement sort in VM over `approvedUsers`.

Immediate next actions (1–2 days)
1) Wire navigation in `MainActivity` and provide a shared `HomeViewModel` to `NavGraph`.
2) Decide DI path. If Hilt, add plugin/deps and `@HiltAndroidApp`, then convert screens to use `hiltViewModel()` and the Hilt `HomeViewModel`; else, remove Hilt VM and annotations.
3) Make Directory sorting work end-to-end: map dropdown selections to an `OrderBy` + `OrderType`, trigger a refresh, and render sorted results. Align labels with actual fields (e.g., Department/Company/Graduation Year).
4) Clean dependencies: remove one of Coil v2/v3 and fix imports; move direct deps to version catalog; optional: use plugin alias for Google Services.
5) Add minimal loading/empty/error UI for Login, Directory, and Pending/Admin lists.
