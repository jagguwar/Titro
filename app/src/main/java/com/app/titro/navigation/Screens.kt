package com.app.titro.navigation

import androidx.navigation.NavHostController
import com.app.titro.util.Action
import com.app.titro.util.Constants.LIST_SCREEN
import com.app.titro.util.Constants.SPLASH_SCREEN

class Screens(navController: NavHostController) {

    val splash: () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }
    val list: (Int) -> Unit = { noteId ->
        navController.navigate(route = "note/$noteId")
    }
    val note: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

}