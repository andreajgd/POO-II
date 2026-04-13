package com.example.formpedidoscomida.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class) //para ExposedDropdownMenuBox
@Composable
fun FormPedidos(){

    var nombreCliente by remember { mutableStateOf("") }
    var telefono by remember  { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var producto by remember {mutableStateOf(" ")}
    var cantidad by remember { mutableStateOf(0) }
    var notasAdicionales by remember { mutableStateOf("") }

    val opcionComida = listOf("Carne Asada", "Pollo Asado", "Cerdo Asado") //producto
    var expanded by remember { mutableStateOf(false) } //para lista desplegable, cuando se hace click se cambia a true
    var selectedOptionText by remember { mutableStateOf(opcionComida[0])}

    val nombreError = nombreCliente.isBlank() || nombreCliente.length < 3
    val telefonoError = telefono.isBlank() || telefono.toLongOrNull() == null  || telefono.length < 8
    val direccionError = direccion.isBlank()
    val cantidadError = cantidad <= 0
    val snackbarHostState = remember { SnackbarHostState() } //controlador de notificaciones
    val scope = rememberCoroutineScope() //para corrutinas (hilos secundarios)
    val formularioValido = !nombreError && !telefonoError && !direccionError && !cantidadError

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }, //mensajes flotantes
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF57C00))
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Formulario de Pedidos",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = nombreCliente,
                    onValueChange = { nombreCliente = it },
                    label = { Text("Nombre del Cliente") },
                    isError = nombreError,
                    supportingText = {
                        if (nombreError) {
                            Text("El nombre debe tener al menos 3 caracteres")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    isError = telefonoError,
                    supportingText = {
                        if (telefonoError) {
                            Text("El teléfono debe tener al menos 8 caracteres")
                        }
                    })
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección") },
                    isError = direccionError,
                    supportingText = {
                        if (direccionError) {
                            Text("La dirección no puede estar vacía")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = selectedOptionText,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Producto") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        opcionComida.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    selectedOptionText = selectionOption
                                    expanded = false
                                    producto = selectionOption
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = cantidad.toString(),
                    onValueChange = { cantidad = it.toIntOrNull() ?: 0 },
                    label = { Text("Cantidad") },
                    isError = cantidadError,
                    supportingText = {
                        if (cantidadError) {
                            Text("La cantidad debe ser mayor a 0")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = notasAdicionales,
                    onValueChange = { notasAdicionales = it },
                    label = { Text("Notas Adicionales") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            if (!formularioValido) {
                                snackbarHostState.showSnackbar("Revise si hay errores")
                            } else {
                                delay(2000)
                                snackbarHostState.showSnackbar("Pedido enviado correctamente")
                            }
                        }
                    },
                    enabled = formularioValido,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF57C00),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Enviar")
                }
            }
    }
}

