package com.cmj.practica1_pm

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
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


private lateinit var sharedPreferences: SharedPreferences

private val operandoA = mutableIntStateOf(0)
private val operandoB = mutableIntStateOf(0)
private lateinit var valorMinimo: MutableIntState
private lateinit var valorMaximo: MutableIntState
private val posiblesOperaciones = listOf("+", "-", "*")
private val operacion = mutableStateOf("+")

private var respuestaOperacion = mutableStateOf("")
private var aciertos = mutableIntStateOf(0)
private var fallos = mutableIntStateOf(0)

class CalculaTronActivity : ComponentActivity() {
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
                        CalculaTron(
                            Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

fun generarOperacion(){
    operandoA.intValue = (valorMinimo.intValue..valorMaximo.intValue).random()
    operandoB.intValue = (valorMinimo.intValue..valorMaximo.intValue).random()
    operacion.value = posiblesOperaciones.random()
}

fun comprobarOperacion() {
    when(operacion.value){
        "+" -> {
            if(respuestaOperacion.value.toInt() == operandoA.intValue + operandoB.intValue) aciertos.intValue++
            else fallos.intValue++
        }
        "-" -> {
            if(respuestaOperacion.value.toInt() == operandoA.intValue - operandoB.intValue) aciertos.intValue++
            else fallos.intValue++
        }
        "*" -> {
            if(respuestaOperacion.value.toInt() == operandoA.intValue * operandoB.intValue) aciertos.intValue++
            else fallos.intValue++
        }
    }

    respuestaOperacion.value = ""
    generarOperacion()
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
        fontSize = 20.sp
    )
}

@Composable
fun Operacion(){
    Row(modifier = Modifier
        .width(200.dp)
        .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text("${operandoA.intValue} ${operacion.value} ${operandoB.intValue} = ?")
        OutlinedTextField(modifier = Modifier
            .size(width = 100.dp, height = 40.dp),
            value = respuestaOperacion.value,
            onValueChange = { respuestaOperacion.value = it },
            readOnly = true
        )
    }
}

@Composable
fun Tecla(
    contenido: String,
    altura: Dp = 40.dp,
    anchura: Dp = 40.dp,
    funcion: () -> (Unit) = {
        respuestaOperacion.value += contenido
    }
){
    Box(modifier = Modifier
        .size(width = anchura, height = altura)
        .padding(2.dp)
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
    LazyHorizontalStaggeredGrid(
        modifier = Modifier
            .wrapContentSize(),
        rows = StaggeredGridCells.Fixed(4),
        verticalArrangement = Arrangement.Center
    ) {
        item{
            Tecla("7")
        }
        item{
            Tecla("4")
        }
        item{
            Tecla("1")
        }
        item{
            Tecla("0")
        }
        item{
            Tecla("8")
        }
        item{
            Tecla("5")
        }
        item{
            Tecla("2")
        }
        item{
            Tecla("-")
        }
        item{
            Tecla("9")
        }
        item{
            Tecla("6")
        }
        item{
            Tecla("3")
        }
        item{
            Tecla("C", funcion = { respuestaOperacion.value = respuestaOperacion.value.dropLast(1) })
        }
        item{
            Tecla("CE", funcion = { respuestaOperacion.value = "" })
        }
        item(span = StaggeredGridItemSpan.FullLine){
            Tecla("=", funcion = { comprobarOperacion() })
        }
    }
}

@Composable
fun CalculaTron(modifier: Modifier = Modifier) {
    val contador = remember { mutableIntStateOf(20) }

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Contador(contador)
        Estadisticas()
        Operacion()
        Teclado()
    }

    LaunchedEffect(Unit) {
        generarOperacion()

        while(contador.intValue > 0){
            delay(1000L)
            contador.intValue--
        }
    }
}

@Preview(showBackground = true, widthDp = 900, heightDp = 400)
@Composable
fun CalculaTronPreview(){
    CalculaTron()
}