package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerificacaoUrlScreen(navigateTo: (String) -> Unit) {
    var url by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Título
        Text(
            text = "Verificador de URL Maliciosa",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF123456),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Verifique se a URL fornecida é segura. Insira uma URL para análise.",
            color = Color(0xFF123456),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de entrada para a URL
        TextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL do Site") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Botão para verificar a URL
        Button(
            onClick = {
                // Lógica de verificação de URL
                if (verificarUrl(url)) {
                    navigateTo("resultadoSeguro")
                } else {
                    navigateTo("alertUrl")
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Verificar URL", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// Função de verificação de URL (simulação)
fun verificarUrl(url: String): Boolean {
    val palavrasMaliciosas = listOf("malware", "phishing", "suspeito")
    return !palavrasMaliciosas.any { url.contains(it, ignoreCase = true) }
}


// Tela de alerta para URL suspeito
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
