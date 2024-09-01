package com.arjunpscwala.pscwala.android.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arjunpscwala.pscwala.android.R
import com.arjunpscwala.pscwala.android.theme.dp_1
import com.arjunpscwala.pscwala.android.theme.dp_128
import com.arjunpscwala.pscwala.android.theme.dp_16
import com.arjunpscwala.pscwala.android.theme.dp_32
import com.arjunpscwala.pscwala.android.theme.dp_6
import com.arjunpscwala.pscwala.android.theme.dp_8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onNavigateToHome: () -> Unit) {
    var gender by remember {
        mutableIntStateOf(-1)
    }

    var city by remember {
        mutableStateOf("")
    }
    var course by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }


    Scaffold(topBar = {
        TopAppBar(title = { /*TODO*/ }, windowInsets = WindowInsets(
            top = dp_16, left = dp_16
        ), actions = {
            TextButton(onClick = { onNavigateToHome() }) {
                Text(text = stringResource(id = R.string.action_skip))
            }
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dp_32)
        ) {
            Text(
                text = stringResource(id = R.string.title_profile),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(dp_16))
            Text(
                text = stringResource(id = R.string.title_gender),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(dp_6))
            Row(
                Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(dp_6)
                    )
                    .padding(dp_6)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = gender == 1, onClick = {
                        gender = 1
                    })
                    Text(
                        text = stringResource(id = R.string.title_male),
                        style = MaterialTheme.typography.labelSmall
                    )

                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = gender == 2, onClick = { gender = 2 })
                    Text(
                        text = stringResource(id = R.string.title_female),
                        style = MaterialTheme.typography.labelSmall
                    )

                }
            }
            Spacer(modifier = Modifier.height(dp_16))
            OutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
                },
                label = {
                    Text(text = stringResource(id = R.string.hint_city))
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.hint_city))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
            )
            Spacer(modifier = Modifier.height(dp_16))
            OutlinedTextField(
                value = course,
                onValueChange = {
                    course = it
                },
                label = {
                    Text(text = stringResource(id = R.string.hint_course))
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.hint_course))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
            )
            Spacer(modifier = Modifier.height(dp_16))
            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                },
                label = {
                    Text(text = stringResource(id = R.string.hint_address))
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.hint_address))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
            )
            Spacer(modifier = Modifier.height(dp_16))
            ElevatedButton(
                onClick = { },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.action_continue))
            }
        }
    }
}