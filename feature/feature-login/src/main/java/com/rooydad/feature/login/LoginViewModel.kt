package com.rooydad.feature.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rooydad.feature.featureGoogleAuth.AuthModel
import com.rooydad.feature.featureGoogleAuth.GoogleAuthUserModel
import com.rooydad.feature.featureGoogleAuth.MyGoogleSignIn
import com.rooydad.feature.featureGoogleAuth.di.ResultCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val googleSignIn: MyGoogleSignIn
) : ViewModel(), ResultCallback<GoogleAuthUserModel> {


    private val _authModel = MutableStateFlow(AuthModel(username = "", password = ""))
    private val _uiMode = MutableStateFlow<UiMode>(UiMode.Login)

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)


    val state = combine(
        _authModel,
        _uiMode,
        _uiState,
        ::CombinedState,
    ).stateIn(
        scope = viewModelScope, initialValue = CombinedState(
            _authModel.value, _uiMode.value, _uiState.value,
        ), started = SharingStarted.WhileSubscribed(5_000)
    )


    fun setUserName(): (String) -> Unit = {
        if (_authModel.value.usernameError != null) {
            _authModel.value = _authModel.value.copy(usernameError = null)
        }
        _authModel.value = _authModel.value.copy(username = it)
    }

    fun setPassword(): (String) -> Unit = {
        if (_authModel.value.passwordError != null) {
            _authModel.value = _authModel.value.copy(passwordError = null)
        }
        _authModel.value = _authModel.value.copy(password = it)
    }

    fun changeUiMode() {
        _uiMode.value = if (_uiMode.value == UiMode.Login) UiMode.Register else UiMode.Login
    }


    fun setUserNameError(error: String) {
        _authModel.value = _authModel.value.copy(usernameError = error)

    }

    fun setPasswordError(error: String) {
        _authModel.value = _authModel.value.copy(passwordError = error)
    }


    fun onSignInWithGoogleClick(context: Context, clientId: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            googleSignIn.signInWithGoogle(
                context = context,
                resultCallBack = this@LoginViewModel,
                clientId = clientId,
            )
        }
    }


    fun onSignInOrSignUpClick() {
        if (!isUsernameValidOrNotEmpty(state.value.authModel.username)) {
            setUserNameError("Invalid Username")
            return
        }

        if (state.value.authModel.password.isEmpty()) {
            setPasswordError("Invalid Password")
            return
        }
        _uiState.value = UiState.Loading
        if (state.value.uiMode == UiMode.Login) {
            googleSignIn.signIn(state.value.authModel, this)
        } else {
            googleSignIn.createUser(state.value.authModel, this)
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

    fun onResetPasswordClick() {
        if (!isUsernameValidOrNotEmpty(state.value.authModel.username)) {
            setUserNameError("Invalid Username")
            return
        }
        googleSignIn.forgetPassword(state.value.authModel.username, this)
    }


    fun isUsernameValidOrNotEmpty(username: String): Boolean =
        username.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()

}

data class CombinedState(
    val authModel: AuthModel, val uiMode: UiMode, val uiState: UiState
)


sealed class UiMode {
    object Login : UiMode()
    object Register : UiMode()
}

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
}