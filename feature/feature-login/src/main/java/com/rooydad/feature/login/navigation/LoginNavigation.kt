package com.rooydad.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.rooydad.feature.login.RooydadLogin

const val LOGIN_ROUTE = "login_route"


fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(route = LOGIN_ROUTE, navOptions)


fun NavGraphBuilder.rooydadLoginScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = LOGIN_ROUTE) {
        RooydadLogin(
            onShowSnackbar = onShowSnackbar,
        )

    }
}