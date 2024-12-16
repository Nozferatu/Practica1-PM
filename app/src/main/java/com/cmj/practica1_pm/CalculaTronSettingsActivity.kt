package com.cmj.practica1_pm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.edit
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme


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
fun Settings(modifier: Modifier = Modifier) {
    var valorMinimoTemp by rememberSaveable { mutableStateOf("") }
    var valorMaximoTemp by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        OutlinedTextField(
            value =  valorMinimoTemp,
            onValueChange = { valorMinimoTemp = it },
            label = { Text("Valor mínimo") },
            singleLine = true
        )

        OutlinedTextField(
            value =  valorMaximoTemp,
            onValueChange = { valorMaximoTemp = it },
            label = { Text("Valor máximo") },
            singleLine = true
        )

        Button(
            onClick = {
                valorMinimo.intValue = valorMinimoTemp.toInt()
                valorMaximo.intValue = valorMaximoTemp.toInt()

                sharedPreferences.edit {
                    putInt("valorMinimo", valorMinimoTemp.toInt())
                    putInt("valorMaximo", valorMaximoTemp.toInt())

                    commit()
                }
            }
        ) {
            Text("Guardar")
        }
    }

    BackHandler {
        //contador.intValue = 30
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun SettingsPreview() {
    Practica1PMTheme {
        Settings()
    }
}