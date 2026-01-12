package com.dx.alumnicasestudy.data.domain.models

// MVP User data model placeholder
// Fields to include:
// - uid: String (Firebase Auth UID)
// - name: String
// - name_lowercase: String (for case-insensitive search)
// - email: String
// - graduation_year: Int
// - department: String
// - job_title: String
// - company: String
// - status: String ("pending" | "approved")
// - role: String ("user" | "admin")
// - created_at: Long or Timestamp
// Note: No implementation here; just outlining the structure.

// Assert: The following data class implements the above structure exactly with simple Kotlin types.
// Assert: name_lowercase is derived when using the factory but stored explicitly for Firestore-friendly schema.
// Assert: created_at uses epoch millis (Long) for simplicity and sorting.

data class User(
    val uid: String = "",
    val name: String = "",
    val name_lowercase: String = name.lowercase(),
    val email: String = "",
    val graduation_year: Int = 0,
    val department: String = "",
    val job_title: String = "",
    val company: String = "",
    val status: String = "pending", // allowed: "pending" | "approved"
    val role: String = "user", // allowed: "user" | "admin"
    val created_at: Long = System.currentTimeMillis()
) {
    companion object {
        // Role constants
        const val ROLE_USER = "user"
        const val ROLE_ADMIN = "admin"

        // Status constants
        const val STATUS_PENDING = "pending"
        const val STATUS_APPROVED = "approved"

        const val STATUS_REJECTED = "rejected"

        // Helper factory to ensure lowercase and defaults are set consistently
        fun create(
            uid: String,
            name: String,
            email: String,
            graduationYear: Int,
            department: String,
            jobTitle: String,
            company: String,
            status: String = "pending",
            role: String = "user",
            createdAt: Long = System.currentTimeMillis()
        ): User {
            return User(
                uid = uid,
                name = name,
                name_lowercase = name.lowercase(),
                email = email,
                graduation_year = graduationYear,
                department = department,
                job_title = jobTitle,
                company = company,
                status = status,
                role = role,
                created_at = createdAt
            )
        }
    }

    // Convenience checks
    val isApproved: Boolean get() = status == "approved"
    val isAdmin: Boolean get() = role == "admin"
}
