package com.example.cyber

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavHostController

@Composable
fun VerificacaoTela(navController: NavHostController, navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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
            onClick = {
                Log.d("VerificacaoTela", "Navegando para verificação de e-mail")
                navigateTo("verificacaoEmail")
            },
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

        // Divider com "or"
        OrDivider()

        // Botão URL da Página
        Button(
            onClick = {
                Log.d("VerificacaoTela", "Navegando para verificação de URL")
                navigateTo("verificacaoUrl")
            },
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

        // Divider com "or"
        OrDivider()

        // Botão Arquivos
        Button(
            onClick = {
                Log.d("VerificacaoTela", "Navegando para verificação de arquivos")
                navigateTo("VerificacaoArquivos")
            },
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

// Função para o divisor com o texto "or"
@Composable
fun OrDivider() {
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
}
