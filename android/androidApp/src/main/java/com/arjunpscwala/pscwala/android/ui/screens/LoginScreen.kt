package com.arjunpscwala.pscwala.android.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.util.trace
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arjunpscwala.pscwala.PhoneAuthInfo
import com.arjunpscwala.pscwala.android.R
import com.arjunpscwala.pscwala.android.theme.dp_1
import com.arjunpscwala.pscwala.android.theme.dp_128
import com.arjunpscwala.pscwala.android.theme.dp_16
import com.arjunpscwala.pscwala.android.theme.dp_32
import com.arjunpscwala.pscwala.android.ui.components.AppSnackbarHost
import com.arjunpscwala.pscwala.android.ui.components.LoadingDialog
import com.arjunpscwala.pscwala.android.ui.components.ShowError
import com.arjunpscwala.pscwala.login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateUp: () -> Unit,
    onVerifyOTP: (phoneAuthInfo: PhoneAuthInfo?) -> Unit,
    loginViewModel: LoginViewModel = viewModel {
        LoginViewModel()
    }
) {

    val localKeyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState by remember {
        mutableStateOf(SnackbarHostState())
    }

    var mobileNumber by rememberSaveable {
        mutableStateOf("")
    }
    val loginUIState by loginViewModel.loginUIState.collectAsState()
    
    LaunchedEffect(loginUIState.phoneAuthInfo) { // Use LaunchedEffect to trigger actions based on state
        if (loginUIState.phoneAuthInfo != null) {
            onVerifyOTP(loginUIState.phoneAuthInfo)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            loginViewModel.dispose()
        }
    }


    Scaffold(
        snackbarHost = {
            AppSnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(title = { /*TODO*/ }, windowInsets = WindowInsets(
                top = dp_16, left = dp_16
            ), navigationIcon = {
                IconButton(
                    onClick = { onNavigateUp() },
                    modifier = Modifier.border(
                        width = dp_1,
                        shape = RoundedCornerShape(dp_16),
                        color = Color.LightGray
                    )
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        stringResource(id = R.string.action_back)
                    )
                }
            })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dp_32)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        localKeyboardController?.hide()
                    })

                }
        ) {
            Spacer(modifier = Modifier.height(dp_128))
            Text(
                text = stringResource(id = R.string.title_mobile),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(text = stringResource(id = R.string.subtitle_login_mobile))
            Spacer(modifier = Modifier.height(dp_16))
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = {
                    mobileNumber = it.take(10)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.hint_mobile_number))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
            )
            Spacer(modifier = Modifier.height(dp_16))
            ElevatedButton(
                onClick = {

                    localKeyboardController?.hide()
                    loginViewModel.sendOTP(mobileNumber)
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (loginUIState.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(text = stringResource(id = R.string.action_continue))
                }

            }
        }
    }

    ShowError(
        snackbarHostState = snackbarHostState,
        uiState = loginUIState,
        errorMessages = loginUIState.errorMessages, onMessageDismiss = {
            loginViewModel.retryLogin(mobileNumber,it)
        }
    )

}

