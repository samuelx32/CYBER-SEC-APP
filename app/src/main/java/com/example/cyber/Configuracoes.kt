package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import android.widget.Toast


@Composable
fun ConfiguracoesScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Estados para gerenciamento
    var isDarkTheme by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Cabeçalho
        Text(
            text = "Configurações",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF061233)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Seção de informações do usuário
        if (user != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Usuário Logado:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Email: ${user.email}", fontSize = 14.sp, color = Color.Black)
                }
            }

            // Alterar Tema
            SettingItem(
                title = if (isDarkTheme) "Modo Claro" else "Modo Escuro",
                onClick = { isDarkTheme = !isDarkTheme }
            )

            // Gerenciar Notificações
            SettingItem(
                title = if (notificationsEnabled) "Desativar Notificações" else "Ativar Notificações",
                onClick = { notificationsEnabled = !notificationsEnabled }
            )

            // Alterar Senha
            SettingItem(
                title = "Alterar Senha",
                onClick = { showChangePasswordDialog = true }
            )

            // Sair da Conta
            SettingItem(
                title = "Sair da Conta",
                onClick = {
                    auth.signOut()
                    navController.navigate("login")
                }
            )
        } else {
            // Se o usuário não estiver logado
            Text(
                text = "Você precisa estar logado para acessar as configurações.",
                color = Color.Red,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53))
            ) {
                Text("Ir para Login", color = Color.White)
            }
        }
    }

    // Diálogo de alteração de senha
    if (showChangePasswordDialog) {
        ChangePasswordDialog(
            onDismiss = { showChangePasswordDialog = false },
            onChangePassword = { newPassword ->
                scope.launch {
                    user?.updatePassword(newPassword)
                        ?.addOnSuccessListener {
                            showChangePasswordDialog = false
                            Toast.makeText(context, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                        ?.addOnFailureListener {
                            Toast.makeText(context, "Erro: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        )
    }
}

@Composable
fun SettingItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                color = Color(0xFF061233)
            )
        )
    }
}

@Composable
fun ChangePasswordDialog(onDismiss: () -> Unit, onChangePassword: (String) -> Unit) {
    var newPassword by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Alterar Senha") },
        text = {
            Column {
                Text("Digite a nova senha:")
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onChangePassword(newPassword) }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
