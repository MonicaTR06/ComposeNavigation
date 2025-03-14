package com.example.task.presentation.create

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.task.presentation.create.ui.UserDataModel

class TaskScreenPreviewProvider : PreviewParameterProvider<UserDataModel> {
    override val values: Sequence<UserDataModel> = sequenceOf(
        UserDataModel( title = "Título de prueba", isTaskOpen = true, priority = "HIGH")
    )
    override val count: Int = values.count()
}