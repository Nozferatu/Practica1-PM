package com.cmj.practica1_pm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme
import kotlinx.coroutines.delay


lateinit var sharedPreferences: SharedPreferences

private val reinicio = mutableIntStateOf(0)
private val operandoA = mutableIntStateOf(0)
private val operandoB = mutableIntStateOf(0)
lateinit var contador: MutableIntState
lateinit var valorMinimo: MutableIntState
lateinit var valorMaximo: MutableIntState
val posiblesOperaciones = listOf("+", "-", "*")
private val operacion = mutableStateOf("+")

private var respuestaOperacion = mutableStateOf("")
private var aciertos = mutableIntStateOf(0)
private var fallos = mutableIntStateOf(0)

class CalculaTronActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sharedPreferences = getSharedPreferences("CalculaTron", MODE_PRIVATE)
        contador = mutableIntStateOf(sharedPreferences.getInt("contador", 30))
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

fun reiniciarContador(){
    reinicio.intValue = (0..20).random()
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
fun BarraSuperior(){
    val contexto = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(
            onClick = { reiniciarContador() }
        ) { Text("Reiniciar") }

        IconButton(
            onClick = {
                val intent = Intent(contexto, CalculaTronSettingsActivity::class.java)
                contexto.startActivity(intent)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                "Configuración"
            )
        }
    }
}

@Composable
fun Contador(contador: MutableIntState){
    Text(modifier = Modifier
        .padding(vertical = 20.dp),
        text = contador.intValue.toString(),
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
        Text("${operandoA.intValue} ${operacion.value} ${operandoB.intValue} = ?", fontSize = 16.sp)
        OutlinedTextField(modifier = Modifier
            .size(width = 100.dp, height = 50.dp),
            value = respuestaOperacion.value,
            onValueChange = { respuestaOperacion.value = it },
            readOnly = true
        )
    }
}

@Composable
fun Tecla(
    modifier: Modifier,
    contenido: String,
    altura: Int = 45,
    anchura: Int = 70,
    funcion: () -> (Unit) = {
        respuestaOperacion.value += contenido
    }
){
    Button(
        onClick = { funcion() },
        modifier = modifier
        //.size(width = anchura.dp, height = altura.dp)
            .requiredSize(height = altura.dp, width = anchura.dp)
        .padding(2.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Magenta,
            contentColor = Color.White
        )
    ) {
        Text(
            contenido,
            color = Color.White,
            //fontWeight = FontWeight.Bold,
            //textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun Teclado(){
    ConstraintLayout(
        modifier = Modifier
            .padding(4.dp)
            .wrapContentSize()
    ) {
        val (
            siete, ocho, nueve, ce,
            cuatro, cinco, seis,
            uno, dos, tres,
            cero, menos, c, igual
        ) = createRefs()

        Tecla(
            Modifier.constrainAs(siete) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(ocho.start)
                bottom.linkTo(cuatro.top)
            }, "7"
        )
        Tecla(
            Modifier.constrainAs(ocho) {
                top.linkTo(parent.top)
                start.linkTo(siete.end)
                end.linkTo(nueve.start)
                bottom.linkTo(cinco.top)
            },"8"
        )
        Tecla(
            Modifier.constrainAs(nueve) {
                top.linkTo(parent.top)
                start.linkTo(ocho.end)
                end.linkTo(ce.start)
                bottom.linkTo(seis.top)
            },"9"
        )
        Tecla(
            Modifier.constrainAs(ce) {
                top.linkTo(parent.top)
                start.linkTo(nueve.end)
                end.linkTo(parent.end)
                bottom.linkTo(igual.top)
            },"CE",
            funcion = { respuestaOperacion.value = "" }
        )
        Tecla(
            Modifier.constrainAs(cuatro) {
                top.linkTo(siete.bottom)
                start.linkTo(parent.start)
                end.linkTo(cinco.start)
                bottom.linkTo(uno.top)
            },"4"
        )
        Tecla(
            Modifier.constrainAs(cinco) {
                top.linkTo(ocho.bottom)
                start.linkTo(cuatro.end)
                end.linkTo(seis.start)
                bottom.linkTo(dos.top)
            },"5"
        )
        Tecla(
            Modifier.constrainAs(seis) {
                top.linkTo(nueve.bottom)
                start.linkTo(cinco.end)
                //end.linkTo(igual.start)
                bottom.linkTo(tres.top)
            },"6"
        )
        Tecla(
            Modifier.constrainAs(uno) {
                top.linkTo(cuatro.bottom)
                start.linkTo(parent.start)
                end.linkTo(dos.start)
                bottom.linkTo(cero.top)
            },"1"
        )
        Tecla(
            Modifier.constrainAs(dos) {
                top.linkTo(cinco.bottom)
                start.linkTo(uno.end)
                end.linkTo(tres.start)
                bottom.linkTo(menos.top)
            },"2"
        )
        Tecla(
            Modifier.constrainAs(tres) {
                top.linkTo(seis.bottom)
                start.linkTo(dos.end)
                //end.linkTo(igual.start)
                bottom.linkTo(c.top)
            },"3"
        )
        Tecla(
            Modifier.constrainAs(cero) {
                top.linkTo(uno.bottom)
                start.linkTo(parent.start)
                end.linkTo(menos.start)
                bottom.linkTo(parent.bottom)
            },"0"
        )
        Tecla(
            Modifier.constrainAs(menos) {
                top.linkTo(dos.bottom)
                start.linkTo(cero.end)
                end.linkTo(c.start)
                bottom.linkTo(parent.bottom)
            },"-"
        )
        Tecla(
            Modifier.constrainAs(c) {
                top.linkTo(tres.bottom)
                start.linkTo(menos.end)
                //end.linkTo(igual.start)
                bottom.linkTo(parent.bottom)
            },"C",
            funcion = { respuestaOperacion.value = respuestaOperacion.value.dropLast(1) }
        )
        Tecla(
            Modifier.constrainAs(igual) {
                top.linkTo(ce.bottom)
                //start.linkTo(ce.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },"=",
            altura = 135,
            funcion = { comprobarOperacion() }
        )
    }
}

@Composable
fun CalculaTron(modifier: Modifier = Modifier) {
    val contexto = LocalContext.current
    val contadorActual = remember { mutableIntStateOf(contador.intValue) }

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        BarraSuperior()

        Spacer(Modifier.weight(1f))

        Contador(contadorActual)
        Estadisticas()
        Operacion()
        Teclado()
    }

    LaunchedEffect(
        key1 = reinicio.intValue,
        key2 = valorMaximo.intValue,
        key3 = valorMinimo.intValue,
    ) {
        generarOperacion()

        contadorActual.intValue = contador.intValue

        while(contadorActual.intValue > 0){
            delay(1000L)
            contadorActual.intValue--
        }

        //if(contadorActual.intValue == 0){
            delay(1000L)

            val intent = Intent(contexto, CalculaTronResultadoActivity::class.java)
            intent.putExtra("aciertos", aciertos.intValue)
            intent.putExtra("fallos", fallos.intValue)

            contexto.startActivity(intent)
        //}
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun CalculaTronPreview(){
    CalculaTron()
}