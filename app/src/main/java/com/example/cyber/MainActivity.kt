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
import com.example.cyber.ui.theme.CyberTheme
import androidx.activity.result.contract.ActivityResultContracts
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
class MainActivity : ComponentActivity() {
    // 1. Adicionar o launcher para autenticação
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        onSignInResult(res)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppWithDrawer(navController = navController)
        }
    }
    // 2. Função para iniciar o login
    fun startSignIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }
    // 3. Função para tratar o resultado do login
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            // Login bem sucedido
            val user = FirebaseAuth.getInstance().currentUser
            // Aqui você pode navegar para outra tela
            // Por exemplo: navController.navigate("home")
        } else {
            // Login falhou
            Toast.makeText(
                this,
                "Erro no login com Google",
                Toast.LENGTH_SHORT
            ).show()
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
            // Conteúdo do menu lateral, se necessário
        }
    ) {
        AppNavHost(navController = navController)
    }
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable(route = "home") {
            TelaInicial(navigateTo = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "gerarSenha") {
            GeradorDeSenhas(navController = navController)
        }
        composable(route = "login") {
            LoginScreen(navController = navController)
        }
        composable(route = "VerificacaoTela") {
            VerificacaoTela(navController = navController ,navigateTo = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "verificacaoEmail") {
            VerificacaoEmailScreen(navController = navController,navigateTo = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "chatSuporte") { // Verifique se a rota é "chatSuporte"
            ChatBotScreen(navController = navController, navigateTo = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "verificacaoUrl") {
            VerificacaoUrlScreen(navigateTo = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "VerificacaoArquivos") {
            VerificacaoArquivoScreen(navigateTo = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "resultadoSeguro") {
            ResultadoSeguroScreen(navigateTo = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "resultadoAlerta") {
            ResultadoAlertaScreen(navigateTo = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "alertUrl") {
            AlertaUrlScreen(navigateTo = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "alertArquivo") {
            AlertaArquivoScreen(navigateTo = { route ->
                navController.navigate(route)
            })
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
