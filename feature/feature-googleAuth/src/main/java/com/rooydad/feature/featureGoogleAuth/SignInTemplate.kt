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
    var username: String,
    val usernameError: String? = null,
    var password: String,
    val passwordError: String? = null,

    )