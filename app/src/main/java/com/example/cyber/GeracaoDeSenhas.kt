package com.example.cyber
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GeracaoDeSenhas() {
    var senha by remember { mutableStateOf("") }
    var tamanho by remember { mutableIntStateOf(8) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Gerador de Senhas", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha Gerada") },
            readOnly = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Slider(value = tamanho.toFloat(), onValueChange = { tamanho = it.toInt() }, valueRange = 8f..32f)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            senha = gerarSenha(tamanho)
        }) {
            Text(text = "Gerar Senha")
        }
    }
}

fun gerarSenha(tamanho: Int): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()"
    return (1..tamanho)
        .map { chars.random() }
        .joinToString("")
}
