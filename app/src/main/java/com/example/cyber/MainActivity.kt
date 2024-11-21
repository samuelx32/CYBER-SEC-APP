package com.example.cyber

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage




class MainActivity : ComponentActivity() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res -> onSignInResult(res) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyberTheme {
                val navController = rememberNavController()
                AppWithDrawer(navController)
            }
        }
    }

     fun startSignIn() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            Toast.makeText(
                this,
                "Bem-vindo, ${user?.displayName ?: "Usuário"}!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this, "Erro no login com Google", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun CyberTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF061233),
            onPrimary = Color.White,
            secondary = Color(0xFF1D2B53),
            onSecondary = Color.White,
            background = Color(0xFFD4D8E2),
            surface = Color.White,
            onSurface = Color.Black
        ),
        typography = Typography(),
        content = content
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppWithDrawer(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController, onClose = { scope.launch { drawerState.close() } })
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") }, // Adicionando o parâmetro 'title' com texto vazio
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Abrir Menu",
                                modifier = Modifier.size(30.dp) // Define o tamanho do ícone
                            )
                        }
                    },
                    actions = {
                        // Insira o botão atualizado com imagem de perfil
                        IconButton(onClick = { navController.navigate("login") }) {
                            val user = FirebaseAuth.getInstance().currentUser
                            val photoUrl = user?.photoUrl?.toString()

                            if (photoUrl != null) {
                                AsyncImage(
                                    model = photoUrl,
                                    contentDescription = "Perfil do Usuário",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color.Gray, CircleShape) // Opcional
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_profile),
                                    contentDescription = "Perfil",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    },

                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color(0xFFCBD6E2), // Cor de fundo definida como 0xFFD4D8E2
                        titleContentColor = Color.Black // Define a cor dos ícones e do texto
                    )
                )
            },
            content = { paddingValues ->
                AppNavHost(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        )

    }
}

@Composable
fun DrawerContent(navController: NavHostController, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Cabeçalho do menu
        Column {
            Text(
                text = "Menu",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF061233),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Opções do menu
            NavigationItem("Início", "home", navController, onClose)
            NavigationItem("Gerador de Senhas", "gerarSenha", navController, onClose)
            NavigationItem("Verificação de E-mails", "verificacaoEmail", navController, onClose)
            NavigationItem("Verificação de URLs", "verificacaoUrl", navController, onClose)
            NavigationItem("Verificação de Arquivos", "VerificacaoArquivos", navController, onClose)
            NavigationItem("Chat de Suporte", "chatSuporte", navController, onClose)
        }

        // Rodapé do menu
        Text(
            text = "Configurações",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF061233),
            modifier = Modifier.clickable {
                navController.navigate("configuracoes")
                onClose()
            }
        )
    }
}

@Composable
fun NavigationItem(
    title: String,
    route: String,
    navController: NavHostController,
    onClose: () -> Unit
) {
    TextButton(
        onClick = {
            navController.navigate(route)
            onClose()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF1D2B53)
        )
    }
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") { TelaInicial(navigateTo = { navController.navigate(it) }) }
        composable("gerarSenha") { GeradorDeSenhas(navController = navController) }
        composable("VerificacaoTela") { VerificacaoTela(navController = navController, navigateTo = { route -> navController.navigate(route) }) }
        composable("verificacaoEmail") { VerificacaoEmailScreen(navController, { navController.navigate(it) }) }
        composable("verificacaoUrl") { VerificacaoUrlScreen(navController, { navController.navigate(it) }) }
        composable("VerificacaoArquivos") { VerificacaoArquivoScreen(navController, { navController.navigate(it) }) }
        composable("chatSuporte") { ChatBotScreen(navController, { navController.navigate(it) }) }
        composable("resultadoSeguro") { ResultadoSeguroScreen { navController.navigate(it) } }
        composable("resultadoAlerta") { ResultadoAlertaScreen { navController.navigate(it) } }
        composable("login") {
            LoginScreen(navController = navController)
        }
    }
}
