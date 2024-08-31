package com.arjunpscwala.pscwala

import android.app.Activity
import java.util.UUID

actual fun randomUUID(): Long = UUID.randomUUID().mostSignificantBits


