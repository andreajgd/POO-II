package com.example.form.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue

@Composable
fun Formulario(){
    var nombre by remember { mutableStateOf("") }
    var email by remember {mutableStateOf("")}
    var edad by remember { mutableStateOf("") } //se inicializa variable con vacío

    val nombreError = nombre.isBlank()
    val emailError = email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val edadError = edad.isBlank() || edad.toIntOrNull() == null
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val formularioValido = !nombreError && !emailError && !edadError && email.isNotEmpty()


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Formulario", style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                isError = nombreError,
                supportingText = {
                    if (nombreError) {
                        Text("No completo formulario")
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                isError = emailError,
                supportingText = {
                    if (emailError) {
                        Text("No completo formulario")
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                isError = edadError,
                supportingText = {
                    if (edadError) {
                        Text("No completo formulario")
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (!formularioValido) {
                            snackbarHostState.showSnackbar("Revise si hay errores")
                        } else {
                            snackbarHostState.showSnackbar("Formulario enviado")
                        }
                    }
                },
                enabled = formularioValido
            ) {
                Text("Enviar")
            }
        }
    }
}
