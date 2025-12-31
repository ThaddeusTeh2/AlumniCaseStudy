# AlumniCaseStudy – MVP Todo Checklist (Sanity-checked)

Last checked: Dec 31

High-level
- Goal: Closed alumni directory (Auth + Firestore). Pending users see gate; approved users see directory; admin approves.
- Tech: Kotlin + Compose + Navigation; Firebase (Auth, Firestore).

Repositories & Build
- [Done] `google-services.json` present under `app/`.
- [Done] Google services plugin applied in `app/build.gradle.kts`.
- [Done] Firebase BOM + Auth added.
- [Done] Firestore dependency added (explicit version).
- [Pending] Replace direct dependency strings with version catalog aliases (per `libs.versions.toml`).
- [Pending] Migrate Kotlin `jvmTarget` to compilerOptions DSL.

Navigation
- [Done] `ui/nav/navGraph.kt` sets `startDestination = Login`.
- [Pending] Use a shared `HomeViewModel` scoped to NavHost (avoid new VM per screen).

UI Screens (wiring)
- Login
  - [Done] Labels: Email/Password.
  - [Done] Call repo.login; route to Directory or PendingGate.
  - [Pending] Show loading/error feedback (minimal present, polish later).
- Register
  - [Done] MVP fields only: name, email, password(+confirm), graduation_year, department, job_title, company.
  - [Done] Simple validation; call repo.register; route to PendingGate.
  - [Pending] UX polish (errors/snackbar, disabled states, keyboard types).
- PendingGate
  - [Done] Add Logout button → back to Login.
  - [Pending] Copy polish.
- Directory
  - [Done] List approved users; search by name prefix.
  - [Pending] Navigate to read-only Profile on item tap.
  - [Pending] Empty state / loading feedback.
- AdminPendingList
  - [Done] List pending users; Approve action; refresh list.
  - [Pending] Gate route by role == admin (app-side check once real auth/profile wired).
- Profile (read-only)
  - [Pending] Minimal screen to show name, year, job, company, email.

ViewModel
- [Done] `HomeViewModel` implemented: login, register, loadApproved, loadPending, approveUser, signOut.
- [Pending] Scope a single `HomeViewModel` to the NavGraph for shared state.
- [Pending] Add basic loading/empty state flows for lists.

Data Layer
- `User`
  - [Current] Using snake_case fields in code (graduation_year, job_title, name_lowercase, created_at) to match Firestore doc shape.
  - [Optional] Refactor later: use Kotlin camelCase properties and map snake_case via `toMap/fromMap` (detekt-friendly), then update UI/repo references.
- `FirebaseAuthService`
  - [Done] Swapped to real Firebase Auth SDK (Email/Password); exposes authState and uid; suspend wrappers via `Task.await()`.
- `FirestoreService`
  - [Done] Swapped to real Firestore SDK; implemented create/get/query/approve; keeps `name_lowercase` index strategy and snake_case keys.
- `AuthRepository`
  - [Done] register/login/signOut/loadApproved/loadPending/approve wired to Firebase services; returns `Result` and lists.

DI
- [Done] `RepositoryProvider` provides singletons (Auth, Firestore, Repo). Hilt optional later.

Firebase Console
- [Done] Project connected; SHA1 added; Google Sign-In enabled.
- [Pending] Enable Email/Password (Auth).
- [Pending] Create Firestore database.
- [Pending] Seed one admin user and set `role = "admin"` on `users/{uid}`.
- [Pending] Basic Firestore Security Rules aligned to MVP (approved read; users write own doc; admin approves).

Acceptance (MVP)
- [Partial] Register creates Auth user + Firestore `users/{uid}` with status=pending, role=user. (App code ready; requires Auth Email/Password enabled and Firestore DB + rules.)
- [Partial] Login routing: approved → Directory; pending → PendingGate. (App code ready; depends on seeded data and ViewModel wiring.)
- [Partial] Directory lists approved users and supports case-insensitive prefix search. (Implemented in service; UI already lists.)
- [Partial] Admin can approve pending users in-app. (Service method implemented; screen hooks present.)

Quick dev notes
- Build warning cleanup: move hardcoded deps to version catalog; update jvmTarget DSL.
- Naming consistency: if you refactor `User` to camelCase, update all UI references (e.g., `graduationYear`, `jobTitle`) and keep Firestore keys snake_case in maps.
- Shared ViewModel: create `@Composable fun AppNavHost()` that provides a single `HomeViewModel` to all destinations.
- Real Firebase swap-in: Auth (Email/Password) and Firestore reads/writes are now backed by SDK; method shapes kept the same.
