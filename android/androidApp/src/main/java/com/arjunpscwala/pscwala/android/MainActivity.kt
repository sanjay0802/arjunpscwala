package com.arjunpscwala.pscwala.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arjunpscwala.pscwala.android.ui.screens.HomeScreen
import com.arjunpscwala.pscwala.android.ui.screens.LoginScreen
import com.arjunpscwala.pscwala.android.ui.screens.ProfileScreen
import com.arjunpscwala.pscwala.android.ui.screens.Screen
import com.arjunpscwala.pscwala.android.ui.screens.signup.SignUpScreen
import com.arjunpscwala.pscwala.android.ui.screens.WelcomeScreen
import com.arjunpscwala.pscwala.android.ui.screens.verify.VerifyOTPScreen
import com.arjunpscwala.pscwala.utils.Event
import com.arjunpscwala.pscwala.utils.EventBus
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                PSCWalaApp()
            }
        }
    }

    @Composable
    private fun PSCWalaApp(
        navHostController: NavHostController
        = rememberNavController(), modifier: Modifier = Modifier
    ) {

        Log.d("saurabhTAG", "Main Screen")
        NavHost(
            navController = navHostController,
            modifier = modifier,
            startDestination = Screen.Welcome
        ) {
            composable<Screen.Login> {
                LoginScreen(onNavigateUp = {
                    navHostController.navigateUp()
                }, onVerifyOTP = {
                    it?.let {
                        lifecycleScope.launch {
                            EventBus.emit(Event.NavigateEvent(it))
                        }
                        navHostController.navigate(Screen.VerifyOTP)
                    }

                })
            }

            composable<Screen.VerifyOTP> {
                VerifyOTPScreen(onNewUser = {
                    lifecycleScope.launch {
                        EventBus.emit(Event.NavigateEvent(it))
                    }
                    navHostController.navigate(Screen.SignUp) {
                        popUpTo(0)
                    }
                }, onExistingUser = {
                    navHostController.navigate(Screen.Home) {
                        popUpTo(0)
                    }
                }, onNavigateUp = {
                    navHostController.navigateUp()
                })
            }

            composable<Screen.Welcome> {
                WelcomeScreen(onContinue = {
                    navHostController.navigate(Screen.Login)
                })
            }

            composable<Screen.SignUp> {
                SignUpScreen(navigateToProfile = {
                    navHostController.navigate(Screen.Profile)
                }, onNavigateUp = {
                    navHostController.navigateUp()
                })
            }

            composable<Screen.Profile> {
                ProfileScreen(onNavigateToHome = {
                    navHostController.navigate(Screen.Home) {
                        popUpTo(0)
                    }
                })
            }

            composable<Screen.Home> {
                HomeScreen()
            }


        }
    }
}


