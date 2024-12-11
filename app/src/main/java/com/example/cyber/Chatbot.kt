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


fun generateBotResponse(input: String): String {
    val lowerCaseInput = input.lowercase()

    return when {
        // Senhas e Autenticação
        lowerCaseInput.contains("senha") -> """
            Para criar uma senha forte, siga estas recomendações:
            - Use pelo menos 12 caracteres
            - Combine letras maiúsculas, minúsculas, números e símbolos
            - Evite informações pessoais como datas ou nomes
            - Use senhas diferentes para cada conta
            - Considere usar um gerenciador de senhas confiável
        """.trimIndent()

        // Phishing e Engenharia Social
        lowerCaseInput.contains("phishing") || lowerCaseInput.contains("golpe") -> """
            Fique atento aos sinais de phishing:
            - E-mails urgentes pedindo ação imediata
            - Erros de português ou formatação estranha
            - Remetentes desconhecidos ou que imitam empresas
            - Links suspeitos ou diferentes do oficial
            - Solicitações de dados pessoais ou bancários
            Sempre verifique a autenticidade antes de clicar em links.
        """.trimIndent()

        // Proteção contra Malware
        lowerCaseInput.contains("antivírus") || lowerCaseInput.contains("malware") -> """
            Para proteção efetiva contra malware:
            1. Mantenha seu antivírus sempre atualizado
            2. Faça varreduras regulares no sistema
            3. Evite downloads de fontes não confiáveis
            4. Mantenha seu sistema operacional atualizado
            5. Ative o firewall do sistema
        """.trimIndent()

        // Segurança em Sites
        lowerCaseInput.contains("site") || lowerCaseInput.contains("navegação") -> """
            Para navegar com segurança:
            - Verifique se o site usa HTTPS (cadeado verde)
            - Confira se a URL está correta
            - Evite fazer compras em sites desconhecidos
            - Não salve dados de cartão em navegadores
            - Use conexões seguras (evite Wi-Fi público)
        """.trimIndent()

        // VPN e Privacidade
        lowerCaseInput.contains("vpn") || lowerCaseInput.contains("privacidade") -> """
            Benefícios de usar uma VPN:
            - Protege seus dados em redes públicas
            - Mascara seu endereço IP
            - Evita rastreamento online
            - Permite acesso seguro a conteúdo restrito
            - Protege durante transações online
        """.trimIndent()

        // Ransomware e Backups
        lowerCaseInput.contains("ransomware") || lowerCaseInput.contains("backup") -> """
            Proteção contra ransomware:
            - Faça backups regulares em local seguro
            - Não abra anexos suspeitos
            - Mantenha software atualizado
            - Use soluções anti-ransomware
            Em caso de ataque, não pague o resgate e procure ajuda profissional.
        """.trimIndent()

        // Autenticação de Dois Fatores
        lowerCaseInput.contains("2fa") || lowerCaseInput.contains("autenticação") -> """
            Sobre autenticação de dois fatores (2FA):
            - Ative em todas as contas importantes
            - Use apps autenticadores em vez de SMS
            - Guarde os códigos de backup
            - Não compartilhe códigos com ninguém
            - Mantenha seu celular protegido
        """.trimIndent()

        // Golpes Financeiros
        lowerCaseInput.contains("golpe financeiro") || lowerCaseInput.contains("banco") -> """
            Prevenção contra golpes financeiros:
            - Desconfie de ofertas muito vantajosas
            - Não clique em links de SMS ou WhatsApp
            - Verifique sempre a fonte da mensagem
            - Use apenas apps oficiais dos bancos
            - Nunca compartilhe senhas ou códigos
        """.trimIndent()

        // Wi-Fi Público
        lowerCaseInput.contains("wifi") || lowerCaseInput.contains("rede") -> """
            Cuidados com Wi-Fi público:
            - Evite acessar bancos ou fazer compras
            - Use sempre uma VPN confiável
            - Desative compartilhamento de arquivos
            - Verifique se está na rede correta
            - Mantenha seu dispositivo atualizado
        """.trimIndent()

        // Engenharia Social
        lowerCaseInput.contains("engenharia social") -> """
            Proteção contra engenharia social:
            - Verifique a identidade de quem pede informações
            - Não forneça dados pessoais por telefone
            - Desconfie de pressão psicológica
            - Confirme solicitações por canais oficiais
            - Treine sua equipe para reconhecer ataques
        """.trimIndent()

        // Resposta padrão para perguntas não reconhecidas
        else -> """
            Não encontrei uma resposta específica para sua pergunta. 
            Posso ajudar com:
            - Senhas e autenticação
            - Proteção contra golpes
            - Segurança em dispositivos
            - Navegação segura
            - Privacidade online
            Como posso auxiliar?
        """.trimIndent()
    }
}

