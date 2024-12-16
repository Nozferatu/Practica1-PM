package com.cmj.practica1_pm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme

private var opcionesMarcadas = mutableStateListOf<Boolean>()

class CalculaTronSettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sharedPreferences = getSharedPreferences("CalculaTron", MODE_PRIVATE)

        setContent {
            Practica1PMTheme {
                Surface {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Settings(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OpcionCheckBox(operacion: String, operacionPermitida: MutableState<Boolean>){
    val (checkedState, onStateChange) = remember { mutableStateOf(true) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .toggleable(
                value = checkedState,
                onValueChange = {
                    onStateChange(!checkedState)
                    operacionPermitida.value = checkedState
                                },
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = null
        )
        Text(
            text = "Operación de $operacion",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun Settings(modifier: Modifier = Modifier) {
    var contadorTemp by rememberSaveable { mutableStateOf("" + contador.intValue) }
    var valorMinimoTemp by rememberSaveable { mutableStateOf("" + valorMinimo.intValue) }
    var valorMaximoTemp by rememberSaveable { mutableStateOf("" + valorMaximo.intValue) }

    val operacionesPermitidas = remember { mutableStateMapOf<String, MutableState<Boolean>>() }

    val modifierInput = Modifier
        .padding(vertical = 10.dp)

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
    ) {
        OutlinedTextField(
            modifier = modifierInput,
            value = contadorTemp,
            onValueChange = { contadorTemp = it },
            label = { Text("Valor mínimo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            modifier = modifierInput,
            value = valorMinimoTemp,
            onValueChange = { valorMinimoTemp = it },
            label = { Text("Valor mínimo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            modifier = modifierInput,
            value = valorMaximoTemp,
            onValueChange = { valorMaximoTemp = it },
            label = { Text("Valor máximo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        LazyColumn {
            items(posiblesOperaciones){ operacion ->
                val operacionPermitida = remember { mutableStateOf(true) }
                operacionesPermitidas[operacion] = operacionPermitida

                OpcionCheckBox(operacion, operacionPermitida)
            }
        }


        Button(
            onClick = {/*
                operacionesPermitidas.forEach { (operacion, valor) ->
                    println("$operacion ${valor.value}")
                }*/

                contador.intValue = contadorTemp.toIntOrNull() ?: 30
                valorMinimo.intValue = valorMinimoTemp.toIntOrNull() ?: 1
                valorMaximo.intValue = valorMaximoTemp.toIntOrNull() ?: 20

                sharedPreferences.edit {
                    putInt("contador", contador.intValue)
                    putInt("valorMinimo", valorMinimo.intValue)
                    putInt("valorMaximo", valorMaximo.intValue)

                    commit()
                }
            }
        ) {
            Text("Guardar")
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun SettingsPreview() {
    Practica1PMTheme {
        Settings()
    }
}