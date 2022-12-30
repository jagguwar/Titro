package com.app.titro.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.app.titro.navigation.destinations.listComposable
import com.app.titro.navigation.destinations.noteComposable
import com.app.titro.navigation.destinations.splashComposable
import com.app.titro.ui.viewmodels.SharedViewModel
import com.app.titro.util.Constants.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    val screen = remember(navController) {
        Screens(navController = navController)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToListScreen = screen.splash
        )
        listComposable(
            navigateToNoteScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
        noteComposable(
            navigateToListScreen = screen.note,
            sharedViewModel = sharedViewModel
        )
    }

}