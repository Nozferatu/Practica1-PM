package com.cmj.practica1_pm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme

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

@Composable
fun Tecla(
    contenido: String,
    altura: Dp = 50.dp,
    anchura: Dp = 50.dp,
    funcion: () -> (Unit) = {

    }
){
    Box(modifier = Modifier
        .size(width = anchura, height = altura)
        .padding(4.dp)
        .background(Color.Black)
        .clickable { funcion },
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
                Tecla("C")
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
                Tecla("CE")
                Tecla("=", altura = 150.dp)
            }
        }
    }
}

@Composable
fun CalculaTron(innerPadding: PaddingValues) {
    Column(modifier = Modifier
        .width(200.dp)
        .padding(innerPadding)
    ) {
        Teclado()
    }
}

@Preview(showBackground = true)
@Composable
fun TecladoPreview(){
    Teclado()
}