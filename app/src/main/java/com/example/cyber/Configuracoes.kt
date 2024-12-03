package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ConfiguracoesScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // Cabeçalho
        Text(
            text = "Configurações",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color(0xFF061233),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Opções de Configuração
        SettingItem(
            title = "Alterar Tema",
            onClick = { /* Ação para alterar tema */ }
        )
        SettingItem(
            title = "Alterar Senha",
            onClick = { /* Ação para alterar senha */ }
        )
        SettingItem(
            title = "Gerenciar Notificações",
            onClick = { /* Ação para gerenciar notificações */ }
        )
        SettingItem(
            title = "Sair da Conta",
            onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("login") // Redireciona para tela de login
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
                color = Color(0xFF061233),
                fontSize = 16.sp
            )
        )
    }
}
