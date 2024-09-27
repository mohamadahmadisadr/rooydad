package com.rooydad.feature.featureGoogleAuth

import android.content.Context
import android.content.Intent
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import androidx.credentials.GetCredentialResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rooydad.feature.featureGoogleAuth.di.ResultCallback


class MyGoogleSignIn(initializer: FirebaseApp?) : SignInTemplate {

    var signInWithGoogleOption: GetSignInWithGoogleOption? = null


    var request: GetCredentialRequest? = null


    @Deprecated("User credential manager instead of this")
    fun getClientID(context: Context, clientToken: String): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientToken).requestEmail().requestProfile().build()

        return GoogleSignIn.getClient(context, gso).signInIntent
    }


    fun handleSignIn(
        getCredentialResponse: GetCredentialResponse,
        resultCallBack: ResultCallback<GoogleAuthUserModel>
    ) {

        val credential = getCredentialResponse.credential
        when (credential) {

            is GoogleIdTokenCredential -> {
                firebaseAuthWithGoogle(credential.idToken, resultCallBack)
            }

//            is PublicKeyCredential -> {
//                println("PublicKeyCredential ${credential.authenticationResponseJson}")
//            }
//
//            is PasswordCredential -> {
//                println("PasswordCredential ${credential.id}")
//            }

            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract the ID to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)

                        var idToken = googleIdTokenCredential.idToken
                        firebaseAuthWithGoogle(idToken, resultCallBack)
                    } catch (e: GoogleIdTokenParsingException) {

                        resultCallBack.onError(e.message ?: "Unknown error")
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    resultCallBack.onError("Unknown error")
                }
            }

            else -> {
                resultCallBack.onError("Unknown credential type")
            }

        }

    }

    suspend fun signInWithGoogle(
        context: Context, resultCallBack: ResultCallback<GoogleAuthUserModel>, clientId: String
    ) {
        if (signInWithGoogleOption == null) {
            signInWithGoogleOption = GetSignInWithGoogleOption.Builder(clientId)
                .build()
        }
        if (request == null) {
            request = Builder().addCredentialOption(signInWithGoogleOption!!).build()

        }

        val response =
            CredentialManager.create(context).getCredential(context = context, request = request!!)
        handleSignIn(response, resultCallBack)
    }

    @Deprecated("Use signInWithGoogle instead of this")
    fun getIdTokenFromIntent(intent: Intent?, onError: (String) -> Unit): String? {
        try {

            return GoogleSignIn.getSignedInAccountFromIntent(intent)
                .getResult(ApiException::class.java).idToken
        } catch (e: ApiException) {
            onError(e.message ?: "Unknown error")
            return null
        }

    }

    fun forgetPassword(email: String, resultCallBack: ResultCallback<GoogleAuthUserModel>) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                resultCallBack.onError("Successfully Sent")
            } else {
                resultCallBack.onError(result.exception?.message ?: "Unknown error")
            }
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
        authModel: AuthModel, resultCallBack: ResultCallback<GoogleAuthUserModel>
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

