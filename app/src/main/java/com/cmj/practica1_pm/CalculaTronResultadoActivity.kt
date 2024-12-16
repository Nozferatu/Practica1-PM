package com.cmj.practica1_pm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme

private var aciertosAnteriores = 0
private var fallosAnteriores = 0
private var aciertosTotales = mutableIntStateOf(0)
private var fallosTotales = mutableIntStateOf(0)

class CalculaTronResultadoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        aciertosTotales.intValue += intent.getIntExtra("aciertos", 0)
        fallosTotales.intValue += intent.getIntExtra("fallos", 0)

        setContent {
            Practica1PMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Resultado(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Resultado(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Practica1PMTheme {
        Resultado()
    }
}