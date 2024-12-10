package com.example.cyber

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PerfilScreen(navController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Título no retângulo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(20.dp, shape = RoundedCornerShape(16.dp)) // Sombra aplicada à Box
                .background(
                    Color(0xFF6476A3),
                    shape = RoundedCornerShape(16.dp)
                ) // Retângulo com cantos arredondados
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Meu Perfil",
                color = Color(0Xff061233),
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(80.dp)) // Espaço entre o título e a imagem

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
                .shadow(40.dp, shape = RoundedCornerShape(16.dp)) // Sombra aplicada à Box
                .clip(RoundedCornerShape(16.dp)) // Recorte com cantos arredondados
                .background(Color(0xFF6476A3)), // Cor do fundo
            contentAlignment = Alignment.Center // Centraliza o conteúdo na Box
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Olá, ${user?.displayName ?: "Usuário"}!",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0Xff061233),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                user?.let {
                    Text(text = "Nome: ${it.displayName ?: "Desconhecido"}", fontSize = 18.sp, color = Color.White)
                    Text(text = "Email: ${it.email ?: "Desconhecido"}", fontSize = 18.sp, color = Color.White)
                }
            }
        }




        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth() // Ocupa toda a largura disponível
                .padding(16.dp), // Adiciona um espaçamento ao redor
            horizontalArrangement = Arrangement.SpaceBetween, // Espaço entre os botões
        ) {
            // Botão "Ver Histórico"
            Button(
                onClick = { navController.navigate("historico") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f) // Divide o espaço proporcionalmente entre os botões
                    .padding(end = 8.dp) // Adiciona espaçamento entre os botões
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    ) // Adiciona sombra
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("Ver Histórico", color = Color.White)
            }

            // Botão "Sai da Conta"
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("login") { popUpTo("home") { inclusive = true } }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .weight(1f) // Divide o espaço proporcionalmente entre os botões
                    .padding(end = 8.dp) // Adiciona espaçamento entre os botões
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    ) // Adiciona sombra
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("Sair da Conta", color = Color.White)
            }
        }
    }
}
