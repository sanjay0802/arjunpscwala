package com.arjunpscwala.pscwala.android.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arjunpscwala.pscwala.android.R
import com.arjunpscwala.pscwala.android.theme.dp_16
import com.arjunpscwala.pscwala.android.theme.dp_32
import com.arjunpscwala.pscwala.android.theme.dp_96

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {

    Scaffold { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(dp_32), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                modifier = Modifier.size(256.dp),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(dp_16))
            Text(
                text = stringResource(id = R.string.title_welcome_hint),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
                , textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dp_16))
            ElevatedButton(
                onClick = { onContinue() },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.action_login_with_phone))
            }
        }
    }
}