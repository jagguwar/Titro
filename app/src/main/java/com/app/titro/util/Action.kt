package com.app.titro.util

enum class Action {

    ADDED,
    UPDATED,
    DELETED,
    DELETE_ALL,
    UNDONE,
    NO_ACTION

}

fun String?.toAction(): Action {
    return if (this.isNullOrEmpty()) Action.NO_ACTION else Action.valueOf(this)
}