# AlumniCaseStudy

# Alumni Directory Mobile App (MVP)

Closed alumni directory mobile application with admin-approved access.  
Built as a minimal, Firebase-only MVP to validate core workflows before expansion.

---

## Overview

This project implements a **closed alumni network** where:
- Users register with basic professional details
- Admins manually approve access
- Only approved users can view the alumni directory

This MVP intentionally limits scope to ensure fast delivery and low operational risk.

---

## MVP Features

### Authentication
- Email & password sign-up and login (Firebase Authentication)
- Duplicate accounts prevented by Firebase Auth

### Registration & Approval
- New users register with basic information
- Accounts are created in a **pending** state
- Admin approval required before directory access

### Access Control
- Pending or rejected users are blocked from the app
- Approved users can access the alumni directory
- Admin access enforced via role flag

### Alumni Directory
- View list of approved alumni
- Search by name (case-insensitive)
- Read-only profiles

### Admin Controls
- View pending registrations
- Approve users

---

## Tech Stack

- **Frontend:** Mobile app (implementation-dependent)
- **Backend:** Firebase (no custom server)
  - Firebase Authentication
  - Cloud Firestore

---

## Firebase Data Model (MVP)

### `users/{uid}`

```json
{
  "name": "Full Name",
  "name_lowercase": "full name",
  "email": "user@email.com",
  "graduation_year": 2022,
  "department": "Computer Science",
  "job_title": "Software Engineer",
  "company": "Company Name",
  "status": "pending | approved | rejected",
  "role": "user | admin",
  "created_at": "timestamp"
}
