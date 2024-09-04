package com.arjunpscwala.pscwala.android.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.arjunpscwala.pscwala.android.R
import com.arjunpscwala.pscwala.models.ErrorMessage
import com.arjunpscwala.pscwala.models.state.UIState

@Composable
fun ShowError(
    snackbarHostState: SnackbarHostState,
    uiState: UIState,
    errorMessages: List<ErrorMessage>
) {
    // Process one error message at a time and show them as Snackbars in the UI
    if (errorMessages.isNotEmpty()) {

        // Remember the errorMessage to display on the screen
        val errorMessage = remember(uiState) { errorMessages[0] }

        // Get the text to show on the message from resources
        val errorMessageText: String = errorMessage.message
        val retryMessageText = stringResource(id = R.string.retry)


        // If onRefreshPosts or onErrorDismiss change while the LaunchedEffect is running,
        // don't restart the effect and use the latest lambda values.
        // val onRefreshPostsState by rememberUpdatedState(onRefreshPosts)
        //  val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        // Effect running in a coroutine that displays the Snackbar on the screen
        // If there's a change to errorMessageText, retryMessageText or snackbarHostState,
        // the previous effect will be cancelled and a new one will start with the new values
        LaunchedEffect(errorMessageText, retryMessageText, snackbarHostState) {
            val snackbarResult = snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessageText
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {

            }
            // Once the message is displayed and dismissed, notify the ViewModel
            //           onErrorDismissState(errorMessage.id)
        }
    }
}