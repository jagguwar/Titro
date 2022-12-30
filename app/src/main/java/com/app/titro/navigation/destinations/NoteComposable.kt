package com.app.titro.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.app.titro.ui.screens.note.NoteScreen
import com.app.titro.ui.viewmodels.SharedViewModel
import com.app.titro.util.Action
import com.app.titro.util.Constants.NOTE_ARGUMENT_KEY
import com.app.titro.util.Constants.NOTE_SCREEN
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.noteComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {

    composable(
        route = NOTE_SCREEN,
        arguments = listOf(navArgument(NOTE_ARGUMENT_KEY) {
            type = NavType.IntType
        }),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = 300
                )
            )
        }
    ) { navBackStackEntry ->
        val noteId = navBackStackEntry.arguments!!.getInt(NOTE_ARGUMENT_KEY)
        LaunchedEffect(key1 = noteId) {
            sharedViewModel.getSelectedNote(noteId = noteId)
        }

        val selectedNote by sharedViewModel.selectedNote.collectAsState()
        LaunchedEffect(key1 = selectedNote) {
            if (selectedNote != null || noteId == -1) {
                sharedViewModel.updateNoteFields(selectedNote = selectedNote)
            }
        }

        NoteScreen(
            selectedNote = selectedNote,
            sharedViewModel = sharedViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }

}