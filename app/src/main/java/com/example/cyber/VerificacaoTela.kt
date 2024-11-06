package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerificacaoTela(navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Por favor, selecione o que você deseja verificar",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = Color(0xFF123456),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Botão E-Mail
        Button(
            onClick = { navigateTo("verificacaoEmail") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "E-Mail", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Divider com "ou"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                color = Color(0xFF1D2B53),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "or",
                color = Color(0xFF1D2B53),
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 14.sp
            )
            Divider(
                color = Color(0xFF1D2B53),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão URL da Página
        Button(
            onClick = { navigateTo("verificacaoUrl") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "URL da Página", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Divider com "ou"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                color = Color(0xFF1D2B53),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "or",
                color = Color(0xFF1D2B53),
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 14.sp
            )
            Divider(
                color = Color(0xFF1D2B53),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão Arquivos
        Button(
            onClick = { navigateTo("verificacaoArquivos") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Arquivos", color = Color.White, fontSize = 18.sp)
        }
    }
}
