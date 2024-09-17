package com.rooydad.feature.featureGoogleAuth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rooydad.feature.featureGoogleAuth.di.ResultCallback


class MyGoogleSignIn(initializer: FirebaseApp?) : SignInTemplate {


    fun getClientID(context: Context, clientToken: String): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientToken).requestEmail().requestProfile().build()
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

    override fun signIn(authModel: AuthModel, resultCallBack: ResultCallback<GoogleAuthUserModel>) {
        auth.signInWithEmailAndPassword(authModel.username, authModel.password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    resultCallBack.onSuccess(result.result?.user.createGoogleAuthUser())
                } else {
                    resultCallBack.onError(result.exception?.message ?: "Unknown error")
                }
            }
    }

    override fun signOut() = auth.signOut()
    override fun currentUser() = auth.currentUser?.createGoogleAuthUser()
    override fun createUser(
        authModel: AuthModel,
        resultCallBack: ResultCallback<GoogleAuthUserModel>
    ) {
        auth.createUserWithEmailAndPassword(authModel.username, authModel.password)
            .onResultListener(resultCallBack)
    }

    override fun isSignedIn(): Boolean = auth.currentUser != null


    fun firebaseAuthWithGoogle(
        idToken: String,
        resultCallBack: ResultCallback<GoogleAuthUserModel>,
    ) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).onResultListener(resultCallBack)
    }


    fun Task<AuthResult>.onResultListener(resultCallBack: ResultCallback<GoogleAuthUserModel>) {
        addOnCompleteListener { result ->
            if (result.isSuccessful) {
                resultCallBack.onSuccess(result.result?.user.createGoogleAuthUser())
            } else {
                resultCallBack.onError(result.exception?.message ?: "Unknown error")
            }

        }
    }
}

