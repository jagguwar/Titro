package com.app.titro.ui.screens.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.app.titro.ui.theme.fabBackgroundColor
import com.app.titro.ui.viewmodels.SharedViewModel
import com.app.titro.util.Action
import com.app.titro.util.SearchAppBarState
import kotlinx.coroutines.launch
import com.app.titro.R

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    action: Action,
    navigateToNoteScreen: (noteId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {

    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseActions(action = action)
    }

    val allNotes by sharedViewModel.allNotes.collectAsState()
    val searchedNotes by sharedViewModel.searchedNotes.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityNotes by sharedViewModel.lowPriorityNotes.collectAsState()
    val highPriorityNotes by sharedViewModel.highPriorityNotes.collectAsState()

    val searchAppBarState: SearchAppBarState = sharedViewModel.searchAppBarState
    val searchTextState: String = sharedViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onComplete = { sharedViewModel.updateAction(newAction = it) },
        onUndoClicked = { sharedViewModel.updateAction(newAction = it) },
        noteTitle = sharedViewModel.title,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            ListContent(
                allNotes = allNotes,
                searchedNotes = searchedNotes,
                lowPriorityNotes = lowPriorityNotes,
                highPriorityNotes = highPriorityNotes,
                sortState = sortState,
                searchAppBarState = searchAppBarState,
                onSwipeToDelete = { action, note ->
                    sharedViewModel.updateAction(newAction = action)
                    sharedViewModel.updateNoteFields(selectedNote = note)
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                navigateToNoteScreen = navigateToNoteScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToNoteScreen)
        }
    )

}

@Composable
fun ListFab(
    onFabClicked: (noteId: Int) -> Unit
) {

    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(
                id = R.string.add_button
            ),
            tint = Color.White
        )
    }

}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    noteTitle: String,
    action: Action
) {

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, noteTitle = noteTitle),
                    actionLabel = setActionLabel(action = action)
                )
                undoDeletedNote(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }

}

private fun setMessage(action: Action, noteTitle: String): String {

    return when (action) {
        Action.DELETE_ALL -> "All Notes Removed"
        else -> "${action.name}: $noteTitle"
    }

}

private fun setActionLabel(action: Action): String {

    return if (action.name == "DELETED") {
        "UNDO"
    } else {
        "OK"
    }

}

private fun undoDeletedNote(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {

    if (snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETED
    ) {
        onUndoClicked(Action.UNDONE)
    }

}