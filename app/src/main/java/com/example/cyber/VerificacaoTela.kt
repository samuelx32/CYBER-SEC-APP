package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificacaoTela(navController: NavHostController, navigateTo: (String) -> Unit) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
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
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFCBD6E2),
                                Color(0xFFD4D8E2)
                            )
                        )
                    )
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 68.dp), // Reduzido o padding geral
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp) // Espaçamento uniforme entre os elementos
            ) {
                // Título
                Text(
                    text = "Escolha o que você deseja verificar",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    color = Color(0xFF123456)
                )

                // Botão E-Mail
                Button(
                    onClick = { navigateTo("verificacaoEmail") },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(text = "E-Mail", color = Color.White, fontSize = 18.sp)
                }

                // Divider com "or"
                OrDivider()

                // Botão URL da Página
                Button(
                    onClick = { navigateTo("verificacaoUrl") },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(text = "URL da Página", color = Color.White, fontSize = 18.sp)
                }

                // Divider com "or"
                OrDivider()

                // Botão Arquivos
                Button(
                    onClick = { navigateTo("VerificacaoArquivos") },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(text = "Arquivos", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    )
}



// Função para o divisor com o texto "or"
@Composable
fun OrDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            color = Color(0xFF1D2B53),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "ou",
            color = Color(0xFF1D2B53),
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 14.sp
        )
        Divider(
            color = Color(0xFF1D2B53),
            modifier = Modifier.weight(1f)
        )
    }
}

