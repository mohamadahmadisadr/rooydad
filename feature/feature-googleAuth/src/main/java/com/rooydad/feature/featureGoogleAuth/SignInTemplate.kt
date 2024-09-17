package com.rooydad.feature.featureGoogleAuth

import com.rooydad.feature.featureGoogleAuth.di.ResultCallback

interface SignInTemplate {

    fun signIn(authModel: AuthModel, resultCallBack: ResultCallback<GoogleAuthUserModel>)
    fun signOut()
    fun isSignedIn(): Boolean
    fun currentUser(): GoogleAuthUserModel?
    fun createUser(authModel: AuthModel, resultCallBack: ResultCallback<GoogleAuthUserModel>)


}

data class AuthModel(
    val username: String,
    val usernameError: String? = null,
    val password: String,
    val passwordError: String? = null,

)