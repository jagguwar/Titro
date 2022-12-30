package com.app.titro.ui.screens.note

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.app.titro.components.DisplayAlertDialog
import com.app.titro.data.models.Priority
import com.app.titro.ui.theme.topAppBarBackgroundColor
import com.app.titro.ui.theme.topAppBarContentColor
import com.app.titro.util.Action
import com.app.titro.R
import com.app.titro.data.models.TitroNote

@Composable
fun NoteAppBar(
    selectedNote: TitroNote?,
    navigateToListScreen: (Action) -> Unit
) {

    if (selectedNote == null) {
        NewNoteAppBar(navigateToListScreen = navigateToListScreen)
    } else {
        ExistingNoteAppBar(
            selectedNote = selectedNote,
            navigateToListScreen = navigateToListScreen
        )
    }

}

@Composable
fun NewNoteAppBar(
    navigateToListScreen: (Action) -> Unit
) {

    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = stringResource(id = R.string.add_note),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        }
    )

}

@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {

    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back_arrow),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}

@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
) {

    IconButton(onClick = { onAddClicked(Action.ADDED) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_note),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}

@Composable
fun ExistingNoteAppBar(
    selectedNote: TitroNote,
    navigateToListScreen: (Action) -> Unit
) {

    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = selectedNote.title,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ExistingNoteAppBarActions(
                selectedNote = selectedNote,
                navigateToListScreen = navigateToListScreen
            )
        }
    )

}

@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
) {

    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}

@Composable
fun ExistingNoteAppBarActions(
    selectedNote: TitroNote,
    navigateToListScreen: (Action) -> Unit
) {

    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(
            id = R.string.delete_note,
            selectedNote.title
        ),
        message = stringResource(
            id = R.string.delete_note_confirmation,
            selectedNote.title
        ),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { navigateToListScreen(Action.DELETED) }
    )

    DeleteAction(onDeleteClicked = { openDialog = true })
    UpdateAction(onUpdateClicked = navigateToListScreen)

}

@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit
) {

    IconButton(onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {

    IconButton(onClick = { onUpdateClicked(Action.UPDATED) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.update_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }

}


@Composable
@Preview
private fun NewNoteAppBarPreview() {
    NewNoteAppBar(
        navigateToListScreen = {}
    )
}

@Composable
@Preview
private fun ExistingNoteAppBarPreview() {
    ExistingNoteAppBar(
        selectedNote = TitroNote(
            id = 0,
            title = "Example note",
            description = "Some random text",
            priority = Priority.LOW
        ),
        navigateToListScreen = {}
    )
}