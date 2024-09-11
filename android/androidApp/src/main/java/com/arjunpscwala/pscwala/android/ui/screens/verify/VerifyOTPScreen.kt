package com.arjunpscwala.pscwala.android.ui.screens.verify

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arjunpscwala.pscwala.PhoneAuthInfoAndroid
import com.arjunpscwala.pscwala.android.R
import com.arjunpscwala.pscwala.android.theme.dp_1
import com.arjunpscwala.pscwala.android.theme.dp_128
import com.arjunpscwala.pscwala.android.theme.dp_16
import com.arjunpscwala.pscwala.android.theme.dp_32
import com.arjunpscwala.pscwala.android.theme.dp_8
import com.arjunpscwala.pscwala.android.ui.components.AppSnackbarHost
import com.arjunpscwala.pscwala.android.ui.components.LoadingDialog
import com.arjunpscwala.pscwala.android.ui.components.ShowError
import com.arjunpscwala.pscwala.verifyOTP.VerifyOTPViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyOTPScreen(
    onNewUser: (phoneAuthInfo: PhoneAuthInfoAndroid) -> Unit,
    onExistingUser: () -> Unit,
    onNavigateUp: () -> Unit,
    verifyOTPViewModel: VerifyOTPViewModel = viewModel() {
        VerifyOTPViewModel()
    }
) {
    val localKeyboardController = LocalSoftwareKeyboardController.current
    val verifyOTPUIState by verifyOTPViewModel.verifyOTPUIState.collectAsState()
    val snackbarHostState by remember {
        mutableStateOf(SnackbarHostState())
    }

    LaunchedEffect(verifyOTPUIState) {

        if (verifyOTPUIState.navToRegister) {
            onNewUser(
                PhoneAuthInfoAndroid(
                    verifyOTPUIState.verificationId,
                    verifyOTPUIState.phoneNumber
                )
            )
        } else if (verifyOTPUIState.navToLogin) {
            onExistingUser()
        }
    }
    LoadingDialog(showDialog = verifyOTPUIState.isLoading) {

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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dp_32), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dp_128))
            if (!verifyOTPUIState.countdownFinished) {
                Text(
                    text = stringResource(
                        id = R.string.count_down_seconds, verifyOTPUIState.countdownText
                    ),
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            Text(text = stringResource(id = R.string.subtitle_verify_mobile))
            Spacer(modifier = Modifier.height(dp_16))
            OTPField(onOTPEntered = {
                localKeyboardController?.hide()
                verifyOTPViewModel.verifyUser()
            })
            Spacer(modifier = Modifier.height(dp_16))
            if (verifyOTPUIState.countdownFinished) {
                TextButton(onClick = {
                    verifyOTPViewModel.sendOTP()
                }) {
                    Text(text = stringResource(id = R.string.action_send_again))
                }
            }

        }
    }

    ShowError(
        snackbarHostState = snackbarHostState,
        uiState = verifyOTPUIState,
        errorMessages =
        verifyOTPUIState.errorMessages, onMessageDismiss = {
            verifyOTPViewModel.retryVerify(it)
        }
    )

}


@Composable
private fun OTPField(onOTPEntered: (otp: String) -> Unit) {
    val otp = remember {
        mutableStateListOf("", "", "", "", "", "")
    }
    val maxLength = 1
    val focusManager = LocalFocusManager.current

    Row(horizontalArrangement = Arrangement.spacedBy(dp_8)) {
        for (i in 0..<otp.size) {
            OutlinedTextField(
                value = otp[i],

                onValueChange = {
                    try {
                        //                    if (it.length > maxLength) {
//                        focusManager.moveFocus(FocusDirection.Next)
//                        return@OutlinedTextField
//                    }
                        if (it.isBlank() && i != 0) {
                            focusManager.moveFocus(FocusDirection.Previous)
                        }
                        if (it.isNotBlank() && it.length == maxLength && i != otp.size - 1) {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                        otp[i] = it.take(maxLength)
                        if (otp.all { it.isNotBlank() }) {
                            onOTPEntered(otp.joinToString(""))
                        }

                    } catch (e: Exception) {

                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Transparent,
                    unfocusedContainerColor = if (otp[i].isNotBlank()) MaterialTheme.colorScheme.primaryContainer else
                        Color.Transparent,
                    unfocusedBorderColor = if (otp[i].isNotBlank()) MaterialTheme.colorScheme.primaryContainer
                    else Color.LightGray,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedTextColor = Color.LightGray
                ),
                modifier = Modifier
                    .focusProperties {
                        this.next
                    }
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 24.sp
                )

            )
        }
    }


}
