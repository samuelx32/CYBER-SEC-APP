package com.example.cyber

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.cyber.DrawerContent
import androidx.compose.ui.draw.clip


class MainActivity : ComponentActivity() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res -> onSignInResult(res) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) } // Controle de tema dinâmico
            val navController = rememberNavController() // Controlador de navegação

            CyberTheme(darkTheme = isDarkTheme) { // Tema aplicado globalmente
                AppWithDrawer(
                    navController = navController,
                    darkThemeEnabled = isDarkTheme,
                    onThemeChange = { isDarkTheme = it } // Controle de alternância de tema
                )
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
fun CyberTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Alterna automaticamente com base no sistema
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF1D1E33),
            onPrimary = Color.White,
            secondary = Color(0xFF2A2B4F),
            onSecondary = Color.White,
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF061233),
            onPrimary = Color.White,
            secondary = Color(0xFF1D2B53),
            onSecondary = Color.White,
            background = Color(0xFFCBD6E2),
            surface = Color.White,
            onSurface = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppWithDrawer(
    navController: NavHostController,
    darkThemeEnabled: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                onClose = { scope.launch { drawerState.close() } },
                onThemeChange = onThemeChange,
                darkThemeEnabled = darkThemeEnabled
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Abrir Menu",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("login") }) {
                            val user = FirebaseAuth.getInstance().currentUser
                            val photoUrl = user?.photoUrl?.toString()

                            if (photoUrl != null) {
                                AsyncImage(
                                    model = photoUrl,
                                    contentDescription = "Perfil do Usuário",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray, CircleShape)
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
                        containerColor = Color(0xFFCBD6E2), // Alterar a cor da top bar
                        titleContentColor = MaterialTheme.colorScheme.primary // Cor do texto
                    )
                )
            },
            content = { paddingValues ->
                AppNavHost(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    darkThemeEnabled = darkThemeEnabled,
                    onThemeChange = onThemeChange
                )
            }
        )
    }
}


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    darkThemeEnabled: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") { TelaInicial(navigateTo = { navController.navigate(it) }) }
        composable("gerarSenha") { GeradorDeSenhas(navController) }
        composable("VerificacaoTela") { VerificacaoTela(navController = navController, navigateTo = { route -> navController.navigate(route) }) }
        composable("verificacaoEmail") { VerificacaoEmailScreen(navController, { navController.navigate(it) }) }
        composable("verificacaoUrl") { VerificacaoUrlScreen(navController, { navController.navigate(it) }) }
        composable("VerificacaoArquivos") { VerificacaoArquivoScreen(navController, { navController.navigate(it) }) }
        composable("chatSuporte") { ChatBotScreen(navController, { navController.navigate(it) }) }



                composable("resultadoSeguro") { ResultadoSeguroScreen { navController.navigate(it) } }
                composable("resultadoAlerta") { ResultadoAlertaScreen { navController.navigate(it) } }
                composable("login") { LoginScreen(navController = navController) }
                composable("configuracoes") { ConfiguracoesScreen(navController = navController) }
                composable("historico") { HistoricoScreen(navController = navController) } // Adicionei a rota do Histórico



    }
}
@Composable
fun DrawerContent(
    navController: NavHostController,
    onClose: () -> Unit,
    darkThemeEnabled: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Barra de Ferramentas",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            NavigationItem("Início", "home", navController, onClose)
            NavigationItem("Gerador de Senhas", "gerarSenha", navController, onClose)
            NavigationItem("Verificação de E-mails", "verificacaoEmail", navController, onClose)
            NavigationItem("Verificação de URLs", "verificacaoUrl", navController, onClose)
            NavigationItem("Chat de Suporte", "chatSuporte", navController, onClose)
            NavigationItem("Histórico", "historico", navController, onClose)
        }
        // Alternar tema
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("configuracoes")
                    onClose()
                }
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings), // Substitua pelo recurso de engrenagem
                contentDescription = "Configurações",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Configurações",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
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
            color = MaterialTheme.colorScheme.primary
        )
    }
}

