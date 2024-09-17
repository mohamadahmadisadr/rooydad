package com.rooydad.feature.featureGoogleAuth.di

abstract class ResultCallback<GoogleAuthUserModel> {
    abstract fun onSuccess(result: GoogleAuthUserModel)
    abstract fun onError(error: String)

}