package com.example.cyber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import com.example.cyber.ui.theme.CyberTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()  // O NavController do aplicativo
            AppWithDrawer(navController = navController)  // Função que inclui o Drawer
        }
    }
}

@Composable
fun AppWithDrawer(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

        }
    ) {
        AppNavHost(navController = navController, onMenuClick = {
            scope.launch { drawerState.open() }
        })
    }
}


@Composable
fun AppNavHost(navController: NavHostController, onMenuClick: () -> Unit, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            TelaInicial(navigateTo = { route ->
                navController.navigate(route)  // Navega para a rota especificada
            }, onMenuClick = onMenuClick)
        }
        composable("gerarSenha") {
            GeracaoDeSenhas()
        }
        composable("login") {  // Certifique-se de que a rota é "login"
            LoginScreen(navController = navController)  // Passa o NavController para a tela de login
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CyberTheme {
        Greeting("Android")
    }
}
