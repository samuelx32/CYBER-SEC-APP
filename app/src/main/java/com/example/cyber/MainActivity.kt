package com.example.cyber

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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



import androidx.compose.ui.draw.clip

import android.app.Activity

import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email

import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


class MainActivity : ComponentActivity() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res -> onSignInResult(res) }

    // Navegação entre telas (Definição do navController)
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

    private fun startSignIn() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val context = this
        if (result.resultCode == Activity.RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            Toast.makeText(
                context,
                "Bem-vindo, ${user?.displayName ?: "Usuário"}!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                "Erro no login com Google",
                Toast.LENGTH_SHORT
            ).show()
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
                        IconButton(onClick = {
                            val user = FirebaseAuth.getInstance().currentUser // Verifica se o usuário está logado
                            if (user != null) {
                                navController.navigate("perfil") // Redireciona para a tela de perfil
                            } else {
                                navController.navigate("login") // Redireciona para a tela de login
                            }
                        }) {
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
    // Inicialize o ViewModel compartilhado
    val historicoViewModel: HistoricoGeralViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            TelaInicial(navigateTo = { navController.navigate(it) })
        }

        composable("gerarSenha") {
            GeradorDeSenhas(navController, historicoViewModel)
        }

        composable("VerificacaoTela") {
            VerificacaoTela(navController = navController, navigateTo = { route -> navController.navigate(route) })
        }

        composable("verificacaoEmail") {
            VerificacaoEmailScreen(navController, { navController.navigate(it) }, historicoViewModel)
        }

        composable("verificacaoUrl") {
            VerificacaoUrlScreen(navController, { navController.navigate(it) }, historicoViewModel)
        }

        composable("VerificacaoArquivos") {
            VerificacaoArquivoScreen(navController, { navController.navigate(it) }, historicoViewModel)
        }

        composable("chatSuporte") {
            ChatBotScreen(navController, { navController.navigate(it) })
        }

        composable("resultadoSeguro") {
            ResultadoSeguroScreen { navController.navigate(it) }
        }

        composable("resultadoAlerta") {
            ResultadoAlertaScreen { navController.navigate(it) }
        }

        composable("login") {
            LoginScreen(navController = navController)
        }

        composable("configuracoes") {
            ConfiguracoesScreen(navController = navController)
        }

        composable(route = "historico") {
            HistoricoScreen(historicoViewModel)
        }

        composable("telaCadastro") {
            TelaCadastro(navController = navController)
        }

        composable("perfil") {
            PerfilScreen(navController = navController)
        }
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
            .width(160.dp)
            .background(Color(0xFFCBD6E2))
            .statusBarsPadding() // Adiciona padding para a status bar
            .navigationBarsPadding() // Adiciona padding para a navigation bar
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Cabeçalho com margem superior
        Text(
            text = "Ferramentas",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Container para os itens de navegação
        Column(
            modifier = Modifier
                .weight(1f) // Ocupa o espaço disponível
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp) // Reduz o espaçamento entre itens
        ) {
            NavigationRow("Início", Icons.Default.Home, "home", navController, onClose)
            NavigationRow("Senhas", Icons.Default.Lock, "gerarSenha", navController, onClose)
            NavigationRow("E-mails", Icons.Default.Email, "verificacaoEmail", navController, onClose)
            NavigationRow("URLs", Icons.Default.Link, "verificacaoUrl", navController, onClose)
            NavigationRow("Arquivos", Icons.Default.InsertDriveFile, "VerificacaoArquivos", navController, onClose)
            NavigationRow("Suporte", Icons.Default.Chat, "chatSuporte", navController, onClose)
            NavigationRow("Histórico", Icons.Default.History, "historico", navController, onClose)
        }

        // Configurações sempre na parte inferior
        NavigationRow("Config", Icons.Default.Settings, "configuracoes", navController, onClose)
    }
}

@Composable
private fun NavigationRow(
    text: String,
    icon: ImageVector,
    route: String,
    navController: NavHostController,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp) // Altura fixa para cada item
            .clickable {
                navController.navigate(route)
                onClose()
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp) // Ícone um pouco menor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun NavigationItem(
    title: String,
    route: String,
    icon: ImageVector,
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

