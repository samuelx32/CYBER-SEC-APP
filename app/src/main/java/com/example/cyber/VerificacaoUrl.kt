package com.example.cyber

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
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
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun VerificacaoUrlScreen(

    navController: NavHostController,
    navigateTo: (String) -> Unit,
    historicoViewModel: HistoricoGeralViewModel,
    virusTotalViewModel: ViewModelVirusTotal = viewModel()
) {
    var url by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }

    val estadoAnalise by virusTotalViewModel.estadoAnalise.collectAsState()

    // Monitora mudanças no estado da análise
    LaunchedEffect(estadoAnalise) {
        when (estadoAnalise) {
            is EstadoAnaliseUrl.Sucesso -> {
                val resultado = estadoAnalise as EstadoAnaliseUrl.Sucesso
                val status = if (resultado.urlEhSegura) "Seguro" else "Malicioso"

                // Adiciona ao histórico
                historicoViewModel.adicionarItem("URL", url, status)

                // Navega para a tela correspondente
                if (resultado.urlEhSegura) {
                    navigateTo("resultadoSeguro")
                } else {
                    navigateTo("alertUrl")
                }
            }
            is EstadoAnaliseUrl.Erro -> {
                showSnackbar = true
                mensagemErro = (estadoAnalise as EstadoAnaliseUrl.Erro).mensagem
            }
            else -> {}
        }
    }

    // Função para verificar a URL
    fun verificarCampos() {
        if (url.isBlank()) {
            showSnackbar = true
            mensagemErro = "Por favor, insira uma URL válida."
        } else {
            showSnackbar = false
            virusTotalViewModel.analisarUrl(url)
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
                        text = "Insira a URL para análise de segurança via VirusTotal.",
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
            if (estadoAnalise is EstadoAnaliseUrl.Carregando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Verificar URL", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Snackbar para validação e erros
        if (showSnackbar) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFFD4D8E2),
                contentColor = Color.Red
            ) {
                Text(mensagemErro, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun AlertaUrlScreen(navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("ALERTA!", color = Color.Red, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "A URL foi identificada como maliciosa pelo VirusTotal!",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Recomenda-se fortemente evitar o acesso a esta URL.",
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navigateTo("home") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Voltar para Home", color = Color.Black)
        }
    }
}