// 1. Primeiro os imports
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import coil.compose.AsyncImage
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

// 2. Definição da MainActivity
class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res -> onSignInResult(res) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemInDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by remember {
                mutableStateOf(systemInDarkTheme)
            }
            var isLoading by remember { mutableStateOf(false) }
            val navController = rememberNavController()

            CyberTheme(darkTheme = isDarkTheme) {
                LoadingOverlay(isLoading = isLoading) {
                    AppWithDrawer(
                        navController = navController,
                        darkThemeEnabled = isDarkTheme,
                        onThemeChange = { newTheme ->
                            isDarkTheme = newTheme
                        }
                    )
                }
            }
        }
    }

    // Funções de autenticação
    private fun startSignIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.ic_profile)
            .setTheme(R.style.Theme_Cyber)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                FirebaseAuth.getInstance().currentUser?.let { user ->
                    showWelcomeMessage(user.displayName)
                }
            }
            else -> showLoginError()
        }
    }

    private fun showWelcomeMessage(userName: String?) {
        Toast.makeText(
            this,
            "Bem-vindo, ${userName ?: "Usuário"}!",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoginError() {
        Toast.makeText(
            this,
            "Erro no login. Tente novamente.",
            Toast.LENGTH_SHORT
        ).show()
    }
}

// 3. Componentes de UI
@Composable
fun LoadingOverlay(
    isLoading: Boolean,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// 4. Definições de tema
private val AppTypography = Typography(
    titleLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    )
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF061233),
    onPrimary = Color.White,
    secondary = Color(0xFF1D2B53),
    onSecondary = Color.White,
    background = Color(0xFFCBD6E2),
    surface = Color.White,
    onSurface = Color.Black,
    tertiary = Color(0xFF3949AB),
    onTertiary = Color.White,
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF1D1D1D)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1D1E33),
    onPrimary = Color.White,
    secondary = Color(0xFF2A2B4F),
    onSecondary = Color.White,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    tertiary = Color(0xFF3949AB),
    onTertiary = Color.White,
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color.White
)

@Composable
fun CyberTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

// 5. Componentes de Navegação
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
                AppTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onProfileClick = {
                        val route = if (FirebaseAuth.getInstance().currentUser != null) {
                            "perfil"
                        } else {
                            "login"
                        }
                        navController.navigate(route)
                    }
                )
            }
        ) { paddingValues ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
                darkThemeEnabled = darkThemeEnabled,
                onThemeChange = onThemeChange
            )
        }
    }
}

// 6. Componentes do Drawer
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
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Ferramentas",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            NavigationRow("Início", Icons.Default.Home, "home", navController, onClose)
            NavigationRow("Senhas", Icons.Default.Lock, "gerarSenha", navController, onClose)
            NavigationRow("E-mails", Icons.Default.Email, "verificacaoEmail", navController, onClose)
            NavigationRow("URLs", Icons.Default.Link, "verificacaoUrl", navController, onClose)
            NavigationRow("Arquivos", Icons.Default.InsertDriveFile, "VerificacaoArquivos", navController, onClose)
            NavigationRow("Suporte", Icons.Default.Chat, "chatSuporte", navController, onClose)
            NavigationRow("Histórico", Icons.Default.History, "historico", navController, onClose)
        }

        NavigationRow("Config", Icons.Default.Settings, "configuracoes", navController, onClose)
    }
}

// 7. Componentes de Navegação
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
            .height(40.dp)
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
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// 8. TopBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = { Text("") },
        navigationIcon = {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Abrir Menu",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier.padding(8.dp)
            ) {
                val user = FirebaseAuth.getInstance().currentUser
                val photoUrl = user?.photoUrl?.toString()

                if (photoUrl != null) {
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = "Perfil do Usuário",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "Perfil",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

// 9. NavHost
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    darkThemeEnabled: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val historicoViewModel: HistoricoGeralViewModel = viewModel()

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
        composable("historico") {
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