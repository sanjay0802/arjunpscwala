package com.arjunpscwala.pscwala.android.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arjunpscwala.pscwala.android.R

@Composable
fun LoadingDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                Row(
                    Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(stringResource(id = R.string.label_loading))
                }


            }
        }
    }
}