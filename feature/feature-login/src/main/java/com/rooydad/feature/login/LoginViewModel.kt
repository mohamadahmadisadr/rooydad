package com.rooydad.feature.login

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rooydad.feature.featureGoogleAuth.AuthModel
import com.rooydad.feature.featureGoogleAuth.GoogleAuthUserModel
import com.rooydad.feature.featureGoogleAuth.MyGoogleSignIn
import com.rooydad.feature.featureGoogleAuth.di.ResultCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val googleSignIn: MyGoogleSignIn
) : ViewModel(), ResultCallback<GoogleAuthUserModel> {


    private val _authModel = mutableStateOf(AuthModel(username = "", password = ""))
    val authModel: State<AuthModel> = _authModel
    private val _uiMode = mutableStateOf<UiMode>(UiMode.Login)
    val uiModeState: State<UiMode> = _uiMode


    private val _uiState = mutableStateOf<UiState>(UiState.Idle)
    val uiState: State<UiState> = _uiState

    fun setUserName(): (String) -> Unit = {
        _authModel.value = _authModel.value.copy(username = it)
    }

    fun setPassword(): (String) -> Unit = {
        _authModel.value = _authModel.value.copy(password = it)
    }

    fun changeUiMode() {
        _uiMode.value = if (_uiMode.value == UiMode.Login) UiMode.Register else UiMode.Login
    }


    fun onSignInWithGoogleClick(context: Context, clientId: String): () -> Unit = {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            googleSignIn.signInWithGoogle(
                context = context,
                resultCallBack = this@LoginViewModel,
                clientId = clientId,
            )
        }
    }


    fun onSignInOrSignUpClick(): () -> Unit = {
        _uiState.value = UiState.Loading
        if (uiModeState.value == UiMode.Login) {
            googleSignIn.signIn(authModel.value, this)
        } else {
            googleSignIn.createUser(authModel.value, this)
        }
    }


    override fun onSuccess(result: GoogleAuthUserModel) {
        _uiState.value = UiState.Idle
        println("MyGoogleSignInResult $result")
    }

    override fun onError(error: String) {
        _uiState.value = UiState.Idle
        println("MyGoogleSignInError $error")
    }

    fun onResetPasswordClick(): () -> Unit = {
        googleSignIn.forgetPassword(authModel.value.username, this)
    }

}

sealed class UiMode {
    object Login : UiMode()
    object Register : UiMode()
}

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
}