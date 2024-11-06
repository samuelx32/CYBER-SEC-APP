package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotScreen(navigateTo: (String) -> Unit) {
    var messages by remember { mutableStateOf(listOf("Em que posso ajudar!")) }
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
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
