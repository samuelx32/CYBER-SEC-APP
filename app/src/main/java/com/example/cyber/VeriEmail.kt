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
fun VerificacaoEmailScreen(navigateTo: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var conteudo by remember { mutableStateOf("") }

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
            text = "Verifique o E-mail Recebido",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF123456),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Insira o e-mail e o conteúdo para análise.",
            color = Color(0xFF123456),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de entrada para o E-mail
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail do Remetente") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Campo de entrada para o Conteúdo
        TextField(
            value = conteudo,
            onValueChange = { conteudo = it },
            label = { Text("Conteúdo do E-mail") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Botão para verificar o e-mail
        Button(
            onClick = {
                // Lógica de verificação de e-mail
                if (verificarEmail(email, conteudo)) {
                    navigateTo("resultadoSeguro")
                } else {
                    navigateTo("resultadoAlerta")
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Verificar E-Mail", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// Função de verificação de e-mail (simulação)
fun verificarEmail(email: String, conteudo: String): Boolean {
    val palavrasMaliciosas = listOf("oferta", "dinheiro", "urgente", "clique")
    return !palavrasMaliciosas.any { conteudo.contains(it, ignoreCase = true) }
}


// Tela verde (segura)
@Composable
fun ResultadoSeguroScreen(navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Boas Notícias!", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Nenhuma Violação encontrada!", color = Color.White, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navigateTo("home") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Voltar", color = Color(0xFF4CAF50))
        }
    }
}

// Tela preta de alerta (suspeito)
@Composable
fun ResultadoAlertaScreen(navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("ALERT!", color = Color.Red, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("O E-Mail inserido aparenta ser uma tentativa de golpe!", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Recomenda-se bloquear o remetente.", color = Color.White, fontSize = 16.sp)

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
