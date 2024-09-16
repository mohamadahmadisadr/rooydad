package com.rooydad.feature.featureGoogleAuth.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.rooydad.feature.featureGoogleAuth.MyGoogleSignIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object GoogleSignInModule {


    @Provides
    @Singleton
    fun provideGoogleSignIn(@ApplicationContext context: Context): MyGoogleSignIn = MyGoogleSignIn(
        initializer = FirebaseApp.initializeApp(context)
    )

}