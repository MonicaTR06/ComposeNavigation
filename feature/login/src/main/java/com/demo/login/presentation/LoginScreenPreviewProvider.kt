package com.demo.login.presentation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.demo.login.presentation.ui.UserDataModel

class LoginScreenPreviewProvider : PreviewParameterProvider<UserDataModel> {
    override val values: Sequence<UserDataModel> = sequenceOf(
        UserDataModel( username = "Cherrell", password = "0Akash.")
    )
    override val count: Int = values.count()
}