package com.example.cyber

import android.net.Uri
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File

@Composable
fun VerificacaoArquivoScreen(
    navController: NavHostController,
    navigateTo: (String) -> Unit,
    historicoViewModel: HistoricoGeralViewModel,
    virusTotalViewModel: ViewModelVirusTotalArquivo = viewModel()
) {
    val TAG = "VerificacaoArquivoScreen"
    val context = LocalContext.current
    var arquivoUri by remember { mutableStateOf<Uri?>(null) }
    var nomeArquivo by remember { mutableStateOf("Nenhum arquivo selecionado") }
    var showSnackbar by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }

    val estadoAnalise by virusTotalViewModel.estadoAnalise.collectAsState()

    // Launcher para selecionar um arquivo
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            arquivoUri = it
            nomeArquivo = getFileName(context, it) ?: "Arquivo selecionado"
            Log.d(TAG, "Arquivo selecionado: $nomeArquivo")
        }
    }

    // Monitora mudanças no estado da análise
    LaunchedEffect(estadoAnalise) {
        when (estadoAnalise) {
            is EstadoAnaliseArquivo.Sucesso -> {
                val resultado = estadoAnalise as EstadoAnaliseArquivo.Sucesso
                val status = if (resultado.arquivoEhSeguro) "Seguro" else "Malicioso"

                historicoViewModel.adicionarItem("Arquivo", nomeArquivo, status)
                Log.d(TAG, "Análise concluída: $status")

                if (resultado.arquivoEhSeguro) {
                    navigateTo("resultadoSeguro")
                } else {
                    navigateTo("alertArquivo")
                }
            }
            is EstadoAnaliseArquivo.Erro -> {
                showSnackbar = true
                mensagemErro = (estadoAnalise as EstadoAnaliseArquivo.Erro).mensagem
                Log.e(TAG, "Erro na análise: $mensagemErro")
            }
            else -> {}
        }
    }

    // Função para verificar o arquivo
    fun verificarArquivo() {
        val uri = arquivoUri
        if (uri == null) {
            showSnackbar = true
            mensagemErro = "Por favor, selecione um arquivo."
            return
        }

        try {
            // Copia o arquivo para um arquivo temporário
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File(context.cacheDir, "temp_${System.currentTimeMillis()}")

            inputStream?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            Log.d(TAG, "Iniciando análise do arquivo: ${tempFile.absolutePath}")
            virusTotalViewModel.analisarArquivo(tempFile.absolutePath)

        } catch (e: Exception) {
            Log.e(TAG, "Erro ao processar arquivo", e)
            showSnackbar = true
            mensagemErro = "Erro ao processar arquivo: ${e.message}"
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
        // Cabeçalho
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

        // Card de título
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Selecione o arquivo para análise via VirusTotal.",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Botão de seleção de arquivo
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

        // Informações do arquivo
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Arquivo Selecionado:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = nomeArquivo,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        // Botão de verificação
        ElevatedButton(
            onClick = { verificarArquivo() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = arquivoUri != null && estadoAnalise !is EstadoAnaliseArquivo.Carregando,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFF061233),
                contentColor = Color.White
            )
        ) {
            if (estadoAnalise is EstadoAnaliseArquivo.Carregando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Verificar Arquivo", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Status da análise
        if (estadoAnalise is EstadoAnaliseArquivo.Carregando) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A237E))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Analisando arquivo...",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Snackbar para erros
        if (showSnackbar) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                contentColor = Color.White,
                containerColor = Color(0xFFB71C1C)
            ) {
                Text(mensagemErro, textAlign = TextAlign.Center)
            }
        }
    }
}

// Função auxiliar para obter o nome do arquivo a partir da Uri
private fun getFileName(context: Context, uri: Uri): String? {
    return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex("_display_name")
        cursor.moveToFirst()
        cursor.getString(nameIndex)
    }
}

// Tela de alerta atualizada
@Composable
fun AlertaArquivoScreen(
    navigateTo: (String) -> Unit,
    viewModel: ViewModelVirusTotalArquivo
) {
    val estadoAnalise by viewModel.estadoAnalise.collectAsState()
    val resultado = (estadoAnalise as? EstadoAnaliseArquivo.Sucesso)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Alerta",
            tint = Color.Red,
            modifier = Modifier.size(64.dp)
        )

        Text(
            "ALERTA!",
            color = Color.Red,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "O arquivo foi identificado como malicioso!",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        // Detalhes da análise
        resultado?.let { res ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Resultados da Análise:",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    res.estatisticas.forEach { (tipo, quantidade) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                tipo.capitalize(),
                                color = Color.Gray
                            )
                            Text(
                                quantidade.toString(),
                                color = when (tipo) {
                                    "malicioso" -> Color.Red
                                    "suspeito" -> Color(0xFFFFA000)
                                    else -> Color.White
                                }
                            )
                        }
                    }
                }
            }
        }

        Text(
            "Recomenda-se excluir o arquivo imediatamente.",
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navigateTo("home") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Voltar para Home", color = Color.Black)
        }
    }
}