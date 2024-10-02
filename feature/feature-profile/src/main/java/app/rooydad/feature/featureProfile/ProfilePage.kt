package app.rooydad.feature.featureProfile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        ProfileHeader(modifier = modifier)
        ProfileBody(modifier = modifier)

    }
}

@Composable
fun ProfileBody(modifier: Modifier) {

}

@Composable
fun ProfileHeader(modifier: Modifier) {
    TODO("Not yet implemented")
}