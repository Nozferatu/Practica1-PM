package com.cmj.practica1_pm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme

private var aciertosAnteriores = 0
private var fallosAnteriores = 0
private var aciertosTotales = mutableIntStateOf(0)
private var fallosTotales = mutableIntStateOf(0)

class CalculaTronResultadoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        aciertosAnteriores = intent.getIntExtra("aciertos", 0)
        fallosAnteriores = intent.getIntExtra("fallos", 0)

        aciertosTotales.intValue += aciertosAnteriores
        fallosTotales.intValue += fallosAnteriores

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
    //val porcentajeAciertosAnteriores = aciertosAnteriores + fallosAnteriores

    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Partida anterior:", fontSize = 24.sp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 100.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Aciertos: $aciertosAnteriores")
            Text("Fallos: $fallosAnteriores")
        }
        //Text("Porcentaje de aciertos: ${aciertosAnteriores}")

        Text("Total:", fontSize = 24.sp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 100.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Aciertos: ${aciertosTotales.intValue}")
            Text("Aciertos: ${fallosTotales.intValue}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Practica1PMTheme {
        Resultado()
    }
}