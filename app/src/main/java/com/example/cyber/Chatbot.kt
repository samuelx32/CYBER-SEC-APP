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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.animation.AnimatedVisibility



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotScreen(navController: NavHostController, navigateTo: (String) -> Unit) {
    var messages by remember { mutableStateOf(listOf("Olá! Em que posso ajudar com segurança cibernética?")) }
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Cabeçalho com ícones de menu e perfil
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Seta de voltar
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart) // Alinha à esquerda
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color(0xFF1D2B53)
                )
            }

            // Texto centralizado
            Text(
                text = "Chat de Suporte",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    color = Color(0xFF1D2B53)
                ),
                modifier = Modifier.align(Alignment.Center) // Centraliza no Box
            )
        }

        // Displaying messages
        // Displaying messages
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            messages.forEachIndexed { index, message ->
                val visible = remember { mutableStateOf(false) }
                LaunchedEffect(index) {
                    kotlinx.coroutines.delay(index * 100L)
                    visible.value = true
                }
                ChatBubble(text = message, isBot = index % 2 == 0, index = index, visible = visible.value)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Input field and send button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                label = { Text("Digite sua mensagem...") },
                colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFEEEEEE))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (inputText.isNotEmpty()) {
                        messages = messages + inputText
                        val botResponse = generateBotResponse(inputText)
                        messages = messages + botResponse
                        inputText = ""
                    }
                },
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Enviar", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ChatBubble(text: String, isBot: Boolean, index: Int, visible: Boolean) {
    val backgroundColor = if (isBot) Color(0xFF1D2B53) else Color(0xFF2196F3)
    val textColor = Color.White

    AnimatedVisibility(
        visible = visible,
        enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandHorizontally(),
        exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.shrinkHorizontally()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .wrapContentWidth(if (isBot) Alignment.Start else Alignment.End)
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
        }
    }
}


// Função para gerar respostas automáticas baseadas em palavras-chave
fun generateBotResponse(input: String): String {
    val lowerCaseInput = input.lowercase()

    return when {
        lowerCaseInput.contains("senha") -> "Recomendo usar uma senha com pelo menos 12 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais."
        lowerCaseInput.contains("phishing") -> "Cuidado com e-mails ou mensagens que pedem informações pessoais. Sempre verifique o remetente e nunca clique em links desconhecidos."
        lowerCaseInput.contains("antivírus") -> "Manter um antivírus atualizado ajuda a proteger seu dispositivo contra malwares."
        lowerCaseInput.contains("site seguro") -> "Certifique-se de que o site começa com 'https' e tem um cadeado na barra de endereço antes de inserir dados pessoais."
        lowerCaseInput.contains("vpn") -> "Usar uma VPN é uma ótima forma de proteger sua privacidade ao navegar na internet em redes públicas."
        lowerCaseInput.contains("dicas") -> "Algumas dicas de segurança: evite clicar em links suspeitos, mantenha seus dispositivos atualizados e use autenticação de dois fatores sempre que possível."
        lowerCaseInput.contains("ransomware") -> "Ransomware é um tipo de malware que criptografa seus arquivos e exige um pagamento. Evite baixar arquivos suspeitos e faça backups regularmente."
        lowerCaseInput.contains("backup") -> "Fazer backups regularmente é essencial para proteger seus dados em caso de perda ou ataque cibernético."
        lowerCaseInput.contains("2fa") || lowerCaseInput.contains("autenticação") -> "Autenticação de dois fatores adiciona uma camada extra de segurança às suas contas. Ative essa opção sempre que disponível."
        lowerCaseInput.contains("golpe financeiro") -> "Fique atento a mensagens que oferecem ganhos financeiros fáceis ou pedem transferências urgentes. Confirme sempre a veracidade da solicitação."
        lowerCaseInput.contains("wifi público") -> "Evite acessar informações sensíveis em redes Wi-Fi públicas. Sempre que possível, use uma VPN para proteger sua conexão."
        lowerCaseInput.contains("engenharia social") -> "Engenharia social é uma técnica usada por golpistas para manipular pessoas. Desconfie de solicitações inesperadas de informações pessoais."
        else -> "Desculpe, não entendi. Poderia reformular a pergunta ou fornecer mais detalhes?"
    }
}

