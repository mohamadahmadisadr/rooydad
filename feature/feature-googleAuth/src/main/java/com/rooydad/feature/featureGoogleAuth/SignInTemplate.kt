package com.rooydad.feature.featureGoogleAuth

interface SignInTemplate {

    fun signOut()
    fun isSignedIn(): Boolean
    fun currentUser(): GoogleAuthUserModel?



}