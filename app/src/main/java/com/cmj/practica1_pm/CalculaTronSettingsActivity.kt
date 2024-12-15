package com.cmj.practica1_pm

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme

private lateinit var sharedPreferences: SharedPreferences

private lateinit var valorMinimo: MutableIntState
private lateinit var valorMaximo: MutableIntState

class CalculaTronSettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sharedPreferences = getSharedPreferences("CalculaTron", MODE_PRIVATE)
        valorMinimo = mutableIntStateOf(sharedPreferences.getInt("valorMinimo", 1))
        valorMaximo = mutableIntStateOf(sharedPreferences.getInt("valorMaximo", 20))

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
    var valorMinimoTemp by rememberSaveable { mutableIntStateOf(0) }
    var valorMaximoTemp by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value =  valorMinimoTemp.toString(),
            onValueChange = { valorMinimoTemp = it.toInt() },
            label = { Text("Valor mínimo") },
            singleLine = true
        )

        OutlinedTextField(
            value =  valorMaximoTemp.toString(),
            onValueChange = { valorMaximoTemp = it.toInt() },
            label = { Text("Valor máximo") },
            singleLine = true
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun SettingsPreview() {
    Practica1PMTheme {
        Settings()
    }
}