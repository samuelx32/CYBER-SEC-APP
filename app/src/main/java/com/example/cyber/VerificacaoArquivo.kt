package com.example.cyber

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
fun VerificacaoArquivoScreen(navigateTo: (String) -> Unit) {
    var arquivoSelecionado by remember { mutableStateOf("Nenhum arquivo selecionado") }

    // Launcher para selecionar um arquivo
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            arquivoSelecionado = uri?.lastPathSegment ?: "Nenhum arquivo selecionado"
        }
    )

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
            text = "Verifique Seu Arquivo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF123456),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Descrição
        Text(
            text = "Selecione um arquivo para análise. Arquivos suspeitos serão identificados.",
            color = Color(0xFF123456),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Botão para escolher o arquivo
        Button(
            onClick = {
                launcher.launch("*/*") // Abre o seletor de arquivos para todos os tipos de arquivo
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Escolher Ficheiro", color = Color.White, fontSize = 18.sp)
        }

        // Exibir nome do arquivo selecionado
        Text(
            text = arquivoSelecionado,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Botão para verificar o arquivo
        Button(
            onClick = {
                if (verificarArquivo(arquivoSelecionado)) {
                    navigateTo("resultadoSeguro")
                } else {
                    navigateTo("alertArquivo")
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1D2B53),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Verificar Arquivo", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// Função de verificação de arquivo (simulação)
fun verificarArquivo(arquivo: String): Boolean {
    val maliciosos = listOf("virus.zip", "malware.exe", "dangerous.doc")
    return !maliciosos.any { arquivo.contains(it, ignoreCase = true) }
}


// Tela de alerta para Arquivo suspeito
@Composable
fun AlertaArquivoScreen(navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("ALERT!", color = Color.Red, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("O arquivo que você tentou verificar foi identificado como malicioso!", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Recomenda-se excluir o arquivo imediatamente.", color = Color.White, fontSize = 16.sp)

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
