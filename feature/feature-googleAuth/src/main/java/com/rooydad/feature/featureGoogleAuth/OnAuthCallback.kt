package com.rooydad.feature.featureGoogleAuth

interface OnAuthCallback {

    fun onSuccess(user: GoogleAuthUserModel)
    fun onError(error: String)
}