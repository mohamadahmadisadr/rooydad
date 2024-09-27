package com.rooydad.feature.featureGoogleAuth.di

interface ResultCallback<GoogleAuthUserModel> {
    fun onSuccess(result: GoogleAuthUserModel)
    fun onError(error: String)

}