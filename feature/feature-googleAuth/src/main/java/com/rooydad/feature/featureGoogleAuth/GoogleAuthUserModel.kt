package com.rooydad.feature.featureGoogleAuth

import com.google.firebase.auth.FirebaseUser

data class GoogleAuthUserModel(
    val displayName: String? = null,
    val photoUrl: String? = null,
    val email: String? = null,
    val uid: String? = null,

)
fun FirebaseUser?.createGoogleAuthUser(): GoogleAuthUserModel{
    return GoogleAuthUserModel(
        displayName = this?.displayName,
        photoUrl = this?.photoUrl.toString(),
        email = this?.email,
        uid = this?.uid,
    )
}