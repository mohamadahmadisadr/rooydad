package com.rooydad.feature.featureGoogleAuth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MyGoogleSignIn(initializer: FirebaseApp?) : SignInTemplate {


    fun getClientID(context: Context, clientToken: String): Intent {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientToken)
                .requestEmail()
                .requestProfile().build()
        return GoogleSignIn.getClient(context, gso).signInIntent
    }


    fun getIdTokenFromIntent(intent: Intent?, onError: (String) -> Unit): String? {
        try {

            return GoogleSignIn.getSignedInAccountFromIntent(intent)
                .getResult(ApiException::class.java).idToken
        } catch (e: ApiException) {
            onError(e.message ?: "Unknown error")
            return null
        }

    }

    val auth =
        if (initializer != null) FirebaseAuth.getInstance(initializer) else FirebaseAuth.getInstance()

    override fun signOut() = auth.signOut()
    override fun currentUser() = auth.currentUser?.createGoogleAuthUser()

    override fun isSignedIn(): Boolean = auth.currentUser != null


    fun firebaseAuthWithGoogle(
        idToken: String,
        onError: (String) -> Unit,
        onSuccess: (GoogleAuthUserModel?) -> Unit,
    ) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess(task.result?.user.createGoogleAuthUser())
            } else {
                // If sign in fails, display a message to the user.
                onError(task.exception?.message ?: "Unknown error")
            }
        }
    }

}

