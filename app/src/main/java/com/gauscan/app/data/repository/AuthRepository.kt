package com.gauscan.app.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.gauscan.app.data.model.UserProfile
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val currentUser: FirebaseUser? get() = auth.currentUser

    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(Exception(getFriendlyError(e.message)))
        }
    }

    suspend fun signUpWithEmail(email: String, password: String, name: String): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!!

            // Create user profile in Firestore
            val profile = UserProfile(
                uid = user.uid,
                name = name,
                email = email
            )
            firestore.collection("users").document(user.uid).set(profile).await()

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(Exception(getFriendlyError(e.message)))
        }
    }

    suspend fun signInWithGoogle(task: Task<GoogleSignInAccount>): Result<FirebaseUser> {
        return try {
            val account = task.await()
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user!!

            // Create/update profile
            val profile = UserProfile(
                uid = user.uid,
                name = user.displayName ?: "",
                email = user.email ?: "",
                photoUrl = user.photoUrl?.toString() ?: ""
            )
            firestore.collection("users").document(user.uid)
                .set(profile, com.google.firebase.firestore.SetOptions.merge())
                .await()

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(Exception("Google sign-in failed: ${e.message}"))
        }
    }

    fun signOut() = auth.signOut()

    private fun getFriendlyError(message: String?): String {
        return when {
            message?.contains("no user record") == true -> "No account found with this email"
            message?.contains("password is invalid") == true -> "Incorrect password"
            message?.contains("email address is already") == true -> "Email already registered"
            message?.contains("badly formatted") == true -> "Invalid email format"
            else -> message ?: "Authentication failed"
        }
    }
}