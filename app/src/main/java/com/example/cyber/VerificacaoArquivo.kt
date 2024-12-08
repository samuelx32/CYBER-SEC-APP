package com.example.cyber

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack





@Composable
fun VerificacaoArquivoScreen(
    navController: NavHostController,
    navigateTo: (String) -> Unit,
    viewModel: HistoricoGeralViewModel // ViewModel compartilhado
) {
    var arquivoSelecionado by remember { mutableStateOf("Nenhum arquivo selecionado") }
    var showSnackbar by remember { mutableStateOf(false) }

    // Launcher para selecionar um arquivo
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            arquivoSelecionado = uri?.lastPathSegment ?: "Nenhum arquivo selecionado"
        }
    )

    // Função para verificar o arquivo e salvar no histórico
    fun verificarCampos() {
        if (arquivoSelecionado == "Nenhum arquivo selecionado") {
            showSnackbar = true
        } else {
            showSnackbar = false
            val seguro = verificarArquivo(arquivoSelecionado)
            val status = if (seguro) "Seguro" else "Maligno"

            // Adiciona ao histórico
            viewModel.adicionarItem("Arquivo", arquivoSelecionado, status)

            // Navega para a tela correspondente
            if (seguro) {
                navigateTo("resultadoSeguro")
            } else {
                navigateTo("alertArquivo")
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

        // Título e Descrição
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF061233), Color(0xFF0A1C50))
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Verificação de Arquivo",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Selecione o arquivo para análise.",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Botão para selecionar arquivo
        ElevatedButton(
            onClick = { launcher.launch("*/*") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFF061233),
                contentColor = Color.White
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.UploadFile,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Selecionar Arquivo", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Nome do arquivo selecionado
        Text(
            text = "Arquivo Selecionado: $arquivoSelecionado",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        // Botão para verificar
        ElevatedButton(
            onClick = { verificarCampos() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFF061233),
                contentColor = Color.White
            )
        ) {
            Text("Verificar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        // Snackbar para validação
        if (showSnackbar) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                contentColor = Color.Red,
                containerColor = Color(0xFFD4D8E2)
            ) {
                Text("Por favor, selecione um arquivo.", textAlign = TextAlign.Center)
            }
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