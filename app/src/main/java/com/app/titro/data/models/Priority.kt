package com.app.titro.data.models

import androidx.compose.ui.graphics.Color
import com.app.titro.ui.theme.HighPriorityColor
import com.app.titro.ui.theme.LowPriorityColor
import com.app.titro.ui.theme.MediumPriorityColor
import com.app.titro.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {

    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)

}