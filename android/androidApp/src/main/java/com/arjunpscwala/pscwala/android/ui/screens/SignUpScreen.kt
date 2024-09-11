package com.arjunpscwala.pscwala.android.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arjunpscwala.pscwala.android.R
import com.arjunpscwala.pscwala.android.theme.dp_1
import com.arjunpscwala.pscwala.android.theme.dp_16
import com.arjunpscwala.pscwala.android.theme.dp_32
import com.arjunpscwala.pscwala.android.ui.screens.signup.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigateToProfile: () -> Unit,
    onNavigateUp: () -> Unit,
    signupViewModel: SignUpViewModel = viewModel()
) {
    var userName by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }

    val registerUIState by signupViewModel.registerUIState.collectAsState()



    Scaffold(topBar = {
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
                .padding(innerPadding)
                .padding(dp_32)
        ) {
            Text(
                text = stringResource(id = R.string.title_signup),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(dp_16))
            OutlinedTextField(
                value = userName,
                onValueChange = {
                    userName = it
                },
                label = {
                    Text(text = stringResource(id = R.string.hint_username))
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.hint_username))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
            )
            Spacer(modifier = Modifier.height(dp_16))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text(text = stringResource(id = R.string.hint_name))
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.hint_name))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
            )
            Spacer(modifier = Modifier.height(dp_16))
            OutlinedTextField(
                value = registerUIState.phoneNumber,
                enabled = false,
                onValueChange = {
                    signupViewModel.onPhoneNumberChange(it)
                },
                label = {
                    Text(text = stringResource(id = R.string.hint_mobile))
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.hint_mobile))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
            )
            Spacer(modifier = Modifier.height(dp_16))
            ElevatedButton(
                onClick = { navigateToProfile() },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.action_save))
            }
        }
    }

}