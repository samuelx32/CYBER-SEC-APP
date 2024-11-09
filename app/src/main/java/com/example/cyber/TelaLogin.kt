package com.example.cyber

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController  // IMPORTAÇÃO NECESSÁRIA
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavHostController) { // Login com google implementado mas falta redirecionar o usuário logado
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Cabeçalho com ícones de menu e perfil
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Seta de voltar
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color(0xFF1D2B53)
                )
            }
        }
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo_cyber), // Substitua com o ID correto do logo
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))



        // Subtítulo
        Text(
            text = "Cadastre sua conta!\nInsira seu email para se cadastrar.",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp
            ),
            color = Color(0xFF123456),
            modifier = Modifier.padding(top = 8.dp, bottom = 20.dp),
            textAlign = TextAlign.Center // Centralizando o texto
        )

        // Campo de E-mail
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("email@domain.com") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Campo de Senha
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão "Continue"
        Button(
            onClick = { navController.navigate("home") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Continue", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Linha "or"
        Text(
            text = "or",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFF123456),
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Center // Centralizando o texto
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botão "Continue with Google"
        Button(
            onClick = { val activity = context as? MainActivity
                activity?.startSignIn()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            // Adicionar ícone do Google
            Image(
                painter = painterResource(id = R.drawable.ic_google), // Substitua pelo ícone do Google
                contentDescription = "Google",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Continue with Google", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Termos e políticas
        Text(
            text = "By clicking continue, you agree to our Terms of Service and Privacy Policy.",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp
            ),
            color = Color(0xFF123456),
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center // Centralizando o texto
        )
    }
}