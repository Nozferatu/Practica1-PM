package com.cmj.practica1_pm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme
import kotlinx.coroutines.delay

private var respuestaOperacion = mutableStateOf("")
private var aciertos = mutableIntStateOf(0)
private var fallos = mutableIntStateOf(0)

class CalculaTronActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            barajarCartas()

            Practica1PMTheme {
                Surface {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        CalculaTron(
                            innerPadding
                        )
                    }
                }
            }
        }
    }
}

fun comprobarOperacion() {
    if(respuestaOperacion.value.toInt() == 4) aciertos.intValue++
    else fallos.intValue++
}

@Composable
fun Contador(contador: MutableState<Int>){
    Text(modifier = Modifier
        .padding(vertical = 20.dp),
        text = contador.value.toString(),
        fontSize = 30.sp
    )
}

@Composable
fun Estadisticas(){
    Text(
        "Aciertos: ${aciertos.intValue} Fallos: ${fallos.intValue}",
        fontSize = 20.sp,
        modifier = Modifier.padding(vertical = 20.dp)
    )
}

@Composable
fun Operacion(){
    Row(modifier = Modifier
        .width(200.dp)
        .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        Text("2 + 2 = ?")
        OutlinedTextField(modifier = Modifier
            .size(width = 125.dp, height = 50.dp),
            value = respuestaOperacion.value,
            onValueChange = { respuestaOperacion.value = it },
            readOnly = true
        )
    }
}

@Composable
fun Tecla(
    contenido: String,
    altura: Dp = 50.dp,
    anchura: Dp = 50.dp,
    funcion: () -> (Unit) = {
        respuestaOperacion.value += contenido
    }
){
    Box(modifier = Modifier
        .size(width = anchura, height = altura)
        .padding(4.dp)
        .background(Color.Black)
        .clickable { funcion() },
        contentAlignment = Alignment.Center

    ) {
        Text(
            contenido,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun Teclado(){
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .width(200.dp)
            .wrapContentSize(),
        columns = StaggeredGridCells.Fixed(4)
    ) {
        item{
            Column {
                Tecla("7")
                Tecla("4")
                Tecla("1")
                Tecla("0", anchura = 100.dp)
            }
        }

        item{
            Column {
                Tecla("8")
                Tecla("5")
                Tecla("2")
                Tecla("C", funcion = { respuestaOperacion.value = respuestaOperacion.value.dropLast(1) })
            }
        }

        item{
            Column {
                Tecla("9")
                Tecla("6")
                Tecla("3")
                Tecla("-")
            }
        }

        item{
            Column {
                Tecla("CE", funcion = { respuestaOperacion.value = "" })
                Tecla("=", altura = 150.dp, funcion = { comprobarOperacion() })
            }
        }
    }
}

@Composable
fun CalculaTron(innerPadding: PaddingValues) {
    val contador = remember { mutableIntStateOf(20) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Contador(contador)
        Estadisticas()
        Operacion()
        Teclado()
    }

    LaunchedEffect(Unit) {
        while(contador.intValue > 0){
            delay(1000L)
            contador.intValue--
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun CalculaTronPreview(){
    CalculaTron(PaddingValues(20.dp))
}