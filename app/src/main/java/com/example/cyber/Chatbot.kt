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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotScreen(navController: NavHostController, navigateTo: (String) -> Unit) {
    var messages by remember { mutableStateOf(listOf("Em que posso ajudar!")) }
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD4D8E2))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Cabeçalho com ícones de menu e perfil
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Seta de voltar
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color(0xFF1D2B53)
                )
            }
            // Ícone do menu
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { /* Ação do menu, caso necessário */ }
            )

            // Ícone do perfil
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navigateTo("login") }
            )
        }
        // Displaying messages
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            messages.forEach { message ->
                ChatBubble(text = message, isBot = true)
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
                        messages = messages + "Resposta automática."
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
fun ChatBubble(text: String, isBot: Boolean) {
    val backgroundColor = if (isBot) Color(0xFF1D2B53) else Color(0xFF2196F3)
    val textColor = Color.White

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
            modifier = Modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        )
    }
}
