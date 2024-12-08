package com.example.cyber

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailLock
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState



// Função de verificação de e-mail (movida para fora do composable)
fun verificaEmail(email: String, conteudo: String): Boolean {
    val dominiosConfiaveis = listOf(
        "gmail.com", "outlook.com", "yahoo.com", // Provedores populares
        "bradesco.com.br", "itau.com.br", "santander.com.br", "bb.com.br", "caixa.gov.br", // Bancos
        "nubank.com.br", "inter.com", "original.com.br", "banco.bradesco", "sicredi.com.br", // Bancos digitais e cooperativas
        "amazon.com", "apple.com", "facebook.com", "google.com", "microsoft.com", "twitter.com", "linkedin.com", // Big Techs
        "steam.com", "playstation.com", "xbox.com", "nintendo.com", "epicgames.com", "riotgames.com", // Empresas de jogos
        "gov.br", "senado.leg.br", "camara.leg.br", "jus.br" // Instituições governamentais no Brasil
    )

    val palavrasMaliciosas = listOf("oferta", "dinheiro", "urgente", "clique", "promoção", "grátis", "presente", "ganhe", "desconto", "prêmio")

    // Verificar se o email está vazio
    if (email.isEmpty()) return false

    // Verificar domínio do remetente
    val dominio = email.substringAfterLast("@", "")
    if (dominio.isEmpty() || !dominiosConfiaveis.contains(dominio)) {
        return false
    }

    // Verificar palavras maliciosas no conteúdo
    val score = palavrasMaliciosas.count { conteudo.contains(it, ignoreCase = true) }
    return score < 2
}

@Composable
fun VerificacaoEmailScreen(
    navController: NavHostController,
    navigateTo: (String) -> Unit,
    viewModel: HistoricoGeralViewModel // ViewModel compartilhado para salvar no histórico
) {
    var email by remember { mutableStateOf("") }
    var conteudo by remember { mutableStateOf("") }
    var links by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    // Função para verificar os campos e salvar o resultado no histórico
    fun verificarCampos() {
        if (email.isEmpty() && conteudo.isEmpty() && links.isEmpty()) {
            showSnackbar = true
        } else {
            showSnackbar = false
            val resultadoSeguro = verificaEmail(email, conteudo)
            val status = if (resultadoSeguro) "Seguro" else "Maligno"

            // Adiciona o resultado ao histórico
            viewModel.adicionarItem("E-mail", email, status)

            // Navega para a tela de resultado
            if (resultadoSeguro) {
                navigateTo("resultadoSeguro")
            } else {
                navigateTo("resultadoAlerta")
            }
        }
    }

    // Layout principal com rolagem vertical
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabeçalho com a seta de voltar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF061233),
                                Color(0xFF0A1C50)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Verificação de E-mail",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Insira as informações do e-mail para análise.",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Campos de entrada
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail do remetente") },
                placeholder = { Text("exemplo@dominio.com") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = conteudo,
                onValueChange = { conteudo = it },
                label = { Text("Conteúdo do e-mail") },
                placeholder = { Text("Digite o conteúdo completo do e-mail...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            OutlinedTextField(
                value = links,
                onValueChange = { links = it },
                label = { Text("Links anexados") },
                placeholder = { Text("Cole aqui os links presentes no e-mail...") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Botão Verificar
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

        // Snackbar de validação
        if (showSnackbar) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFF1D2B53)
            ) {
                Text("Por favor, preencha pelo menos um campo.", color = Color.White)
            }
        }
    }
}




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