package com.rooydad.feature.featureGoogleAuth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MyGoogleSignIn(
    private val onAuthCallback: OnAuthCallback,
    private var clientId: String,
    private val initializerFirebase: FirebaseApp? = null
) {


    class Builder {
        private var onAuthCallback: OnAuthCallback? = null
        private var clientId: String? = null
        private var initializerFirebase: FirebaseApp? = null

        fun setOnAuthCallback(onAuthCallback: OnAuthCallback) = apply {
            this.onAuthCallback = onAuthCallback
        }

        fun setClientId(clientId: String) = apply {
            this.clientId = clientId
        }

        fun setFirebaseApp(firebaseApp: FirebaseApp) = apply {
            this.initializerFirebase = firebaseApp
        }


        fun build(): MyGoogleSignIn {
            if (clientId == null) {
                throw IllegalArgumentException("clientId cannot be null")
            }

            return MyGoogleSignIn(
                onAuthCallback = onAuthCallback!!,
                clientId = clientId!!,
                initializerFirebase = initializerFirebase
            )
        }
    }


    private val gso =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(clientId)
            .requestEmail().requestProfile().build()


    fun getClientID(context: Context): Intent = GoogleSignIn.getClient(context, gso).signInIntent


    fun getIdTokenFromIntent(intent: Intent?): String? {
        try {

            return GoogleSignIn.getSignedInAccountFromIntent(intent)
                .getResult(ApiException::class.java).idToken
        } catch (e: ApiException) {
            onAuthCallback.onError(e.message ?: "Unknown error")
            return null
        }

    }


    fun firebaseAuthWithGoogle(idToken: String) {
        val auth =
            if (initializerFirebase == null) FirebaseAuth.getInstance() else FirebaseAuth.getInstance(
                initializerFirebase
            )
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onAuthCallback.onSuccess(task.result?.user.createGoogleAuthUser())
            } else {
                // If sign in fails, display a message to the user.
                onAuthCallback.onError(task.exception?.message ?: "Unknown error")
            }
        }
    }

}

