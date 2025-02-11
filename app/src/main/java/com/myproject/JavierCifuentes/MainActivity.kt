package com.myproject.JavierCifuentes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.myproject.JavierCifuentes.Data.local.DataStore.DataStoreManager
import com.myproject.JavierCifuentes.Presentation.Navigation.LoginDestination
import com.myproject.JavierCifuentes.Presentation.Navigation.RecetasListDestiantion2
import com.myproject.JavierCifuentes.Presentation.Navigation.loginScreen
import com.myproject.JavierCifuentes.Presentation.Navigation.recetasListScreen2
import com.myproject.JavierCifuentes.ui.theme.ReceFacilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceFacilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    var loggedIn by remember { mutableStateOf(false) }
                    val context = LocalContext.current
                    val dataStoreManager = DataStoreManager(context)

                    LaunchedEffect(Unit) {
                        dataStoreManager.getUserName().collect { name ->
                            loggedIn = !name.isNullOrEmpty()
                        }
                    }

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = if (loggedIn) RecetasListDestiantion2 else LoginDestination,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            loginScreen(
                                onLoginClick = {
                                    navController.navigate(RecetasListDestiantion2) {
                                        popUpTo(LoginDestination) { inclusive = true }
                                    }
                                }
                            )

                            recetasListScreen2(
                                navController = navController,
                                onBackToLogin = {
                                    navController.navigate(LoginDestination) {
                                        popUpTo(LoginDestination) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

