package com.example.cyber

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController



@Composable
fun VerificacaoUrlScreen(
    navController: NavHostController,
    navigateTo: (String) -> Unit,
    viewModel: HistoricoGeralViewModel // ViewModel compartilhado
) {
    var url by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    // Função para verificar a URL e salvar no histórico
    fun verificarCampos() {
        if (url.isBlank()) {
            showSnackbar = true
        } else {
            showSnackbar = false
            val seguro = verificarUrl(url)
            val status = if (seguro) "Seguro" else "Maligno"

            // Adiciona ao histórico
            viewModel.adicionarItem("URL", url, status)

            // Navega para a tela correspondente
            if (seguro) {
                navigateTo("resultadoSeguro")
            } else {
                navigateTo("alertUrl")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cabeçalho com a seta de voltar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color(0xFF1D2B53)
                )
            }
        }

        // Card com título e descrição
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF061233), Color(0xFF0A1C50))
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Verificador de URL",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Insira a URL para análise de segurança.",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Campo de entrada para a URL
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL do site") },
            placeholder = { Text("https://exemplo.com") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3949AB),
                unfocusedBorderColor = Color(0xFF9FA8DA)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para verificar a URL
        ElevatedButton(
            onClick = { verificarCampos() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFF061233),
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Verificar URL", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        // Snackbar para validação
        if (showSnackbar) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFFD4D8E2),
                contentColor = Color.Red
            ) {
                Text("Por favor, insira uma URL válida.", textAlign = TextAlign.Center)
            }
        }
    }
}

// Função de verificação de URL (simulação)
fun verificarUrl(url: String): Boolean {
    val palavrasMaliciosas = listOf("malware", "phishing", "suspeito", "virus", "fake")
    return !palavrasMaliciosas.any { url.contains(it, ignoreCase = true) }
}

// Tela de alerta para URL suspeita
@Composable
fun AlertaUrlScreen(navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("ALERT!", color = Color.Red, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("A URL que você tentou acessar foi identificada como maliciosa!", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Recomenda-se evitar o acesso a esta URL.", color = Color.White, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navigateTo("home") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Voltar", color = Color.Black)
        }
    }
}

