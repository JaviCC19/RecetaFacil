package com.myproject.JavierCifuentes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.myproject.JavierCifuentes.Presentation.Navigation.LoginDestination
import com.myproject.JavierCifuentes.Presentation.Navigation.RecetasListDestination
import com.myproject.JavierCifuentes.Presentation.Navigation.loginScreen
import com.myproject.JavierCifuentes.Presentation.Navigation.recetasListScreen2
import com.myproject.JavierCifuentes.ui.theme.ReceFacilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceFacilTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = LoginDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        loginScreen(onLoginClick = {
                            navController.navigate(RecetasListDestination)
                        })
                        recetasListScreen2(
                            navController = navController,
                            onBackToLogin = {
                                navController.navigate("login") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    ReceFacilTheme {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier.padding(innerPadding)
            ) {
                loginScreen(onLoginClick = {
                    navController.navigate("recetasList")
                })
                recetasListScreen2(
                    navController = navController,
                    onBackToLogin = {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}