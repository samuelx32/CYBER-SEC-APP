package com.example.cyber
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun TelaInicial(navigateTo: (String) -> Unit, onMenuClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Espaço para o ícone do menu e perfil
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Ícone do menu (hambúrguer)
            Image(
                painter = painterResource(id = R.drawable.ic_menu), // Substitua com o ícone correspondente
                contentDescription = "Menu",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onMenuClick() } // Abre o Drawer
            )

            // Ícone do perfil
            Image(
                painter = painterResource(id = R.drawable.ic_profile), // Substitua com o ícone correspondente
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navigateTo("login") } // Navega para a tela de login
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Logo do aplicativo
        Image(
            painter = painterResource(id = R.drawable.logo_cyber), // Substitua com o ID correto do logo
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Texto principal
        Text(
            text = "Ferramentas para sua segurança online.",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            color = Color(0xFF123456), // Ajuste a cor do texto de acordo com o design
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botões de navegação (layout 2x1)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Primeira linha com dois botões lado a lado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botão Gerar Senhas
                Button(
                    onClick = { navigateTo("gerarSenha") },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(120.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_key), // Substitua com o ícone de senha
                            contentDescription = "Gerar Senhas",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Gerar Senhas", color = Color.White)
                    }
                }

                // Botão Verificação de Segurança
                Button(
                    onClick = { navigateTo("verificacaoSeguranca") },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(120.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_shield), // Substitua com o ícone de segurança
                            contentDescription = "Verificação de Segurança",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Verificação de Segurança", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Segunda linha com um botão
            Button(
                onClick = { navigateTo("chatSuporte") },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chat), // Substitua com o ícone de chat
                        contentDescription = "Chat de Suporte",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Chat de Suporte", color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
