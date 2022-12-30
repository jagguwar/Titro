package com.app.titro.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.titro.data.models.Priority
import com.app.titro.data.models.TitroNote
import com.app.titro.data.repositories.DataStoreRepository
import com.app.titro.data.repositories.TitroRepository
import com.app.titro.util.Action
import com.app.titro.util.Constants.MAX_TITLE_LENGTH
import com.app.titro.util.RequestState
import com.app.titro.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: TitroRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var action by mutableStateOf(Action.NO_ACTION)
        private set

    var id by mutableStateOf(0)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var priority by mutableStateOf(Priority.LOW)
        private set

    var searchAppBarState by mutableStateOf(SearchAppBarState.CLOSED)
        private set

    var searchTextState by mutableStateOf("")
        private set

    private val _allNotes =
        MutableStateFlow<RequestState<List<TitroNote>>>(RequestState.Idle)
    val allNotes: StateFlow<RequestState<List<TitroNote>>> = _allNotes

    private val _searchedNotes =
        MutableStateFlow<RequestState<List<TitroNote>>>(RequestState.Idle)
    val searchedNotes: StateFlow<RequestState<List<TitroNote>>> = _searchedNotes

    private val _sortState =
        MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    init {
        getAllNotes()
        readSortState()
    }

    fun searchDatabase(searchQuery: String) {
        _searchedNotes.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect { searchedNotes ->
                        _searchedNotes.value = RequestState.Success(searchedNotes)
                    }
            }
        } catch (e: Exception) {
            _searchedNotes.value = RequestState.Error(e)
        }
        searchAppBarState = SearchAppBarState.TRIGGERED
    }

    val lowPriorityNotes: StateFlow<List<TitroNote>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val highPriorityNotes: StateFlow<List<TitroNote>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    private fun getAllNotes() {
        _allNotes.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllNotes.collect {
                    _allNotes.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allNotes.value = RequestState.Error(e)
        }
    }

    private val _selectedNote: MutableStateFlow<TitroNote?> = MutableStateFlow(null)
    val selectedNote: StateFlow<TitroNote?> = _selectedNote

    fun getSelectedNote(noteId: Int) {
        viewModelScope.launch {
            repository.getSelectedNote(noteId = noteId).collect { note ->
                _selectedNote.value = note
            }
        }
    }

    private fun addNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoNote = TitroNote(
                title = title,
                description = description,
                priority = priority
            )
            repository.addNote(titroNote = toDoNote)
        }
        searchAppBarState = SearchAppBarState.CLOSED
    }

    private fun updateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoNote = TitroNote(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.updateNote(titroNote = toDoNote)
        }
    }

    private fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoNote = TitroNote(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.deleteNote(titroNote = toDoNote)
        }
    }

    private fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADDED -> {
                addNote()
            }
            Action.UPDATED -> {
                updateNote()
            }
            Action.DELETED -> {
                deleteNote()
            }
            Action.DELETE_ALL -> {
                deleteAllNotes()
            }
            Action.UNDONE -> {
                addNote()
            }
            else -> {

            }
        }
    }

    fun updateNoteFields(selectedNote: TitroNote?) {
        if (selectedNote != null) {
            id = selectedNote.id
            title = selectedNote.title
            description = selectedNote.description
            priority = selectedNote.priority
        } else {
            id = 0
            title = ""
            description = ""
            priority = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title = newTitle
        }
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
    }

    fun updateAction(newAction: Action) {
        action = newAction
    }

    fun updateAppBarState(newState: SearchAppBarState) {
        searchAppBarState = newState
    }

    fun updateSearchText(newText: String) {
        searchTextState = newText
    }

    fun validateFields(): Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }

}