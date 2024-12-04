package com.cmj.practica1_pm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private var cartasEscogidas = mutableMapOf<Int, Int>() //Indice - Imagen
private var elecciones = mutableListOf(-1, -1)
private val cartasVolteadas = mutableStateListOf<Boolean>()

class MemoryTronActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val listaCartas = mutableListOf(
            R.drawable.carta1,
            R.drawable.carta2,
            R.drawable.carta3,
            R.drawable.carta4,
            R.drawable.carta5,
            R.drawable.carta6
        )

        setContent {
            var indice = 0
            var imagenCarta: Int
            var imagenValida: Boolean
            var ocurrenciasImagen: Int

            while(cartasEscogidas.size<12){
                imagenValida = false

                while(!imagenValida){
                    imagenCarta = listaCartas[listaCartas.indices.random()]

                    ocurrenciasImagen = cartasEscogidas.values.count{ it == imagenCarta  }

                    if(ocurrenciasImagen < 2){
                        cartasEscogidas[indice] = imagenCarta

                        cartasVolteadas.add(false)

                        indice++
                    }

                    imagenValida = true
                }
            }

            Practica1PMTheme {
                Surface {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        MemoryTron(
                            innerPadding
                        )
                    }
                }
            }
        }
    }
}

suspend fun comprobarElecciones(cartasVolteadas: MutableList<Boolean>){
    val eleccion1 = cartasEscogidas[elecciones[0]]
    val eleccion2 = cartasEscogidas[elecciones[1]]

    Log.d("Cartas", "$eleccion1 - $eleccion2")

    if(eleccion1 != eleccion2){
        Log.d("Cartas", "No son iguales, se voltean de vuelta")
        delay(1000L)

        cartasVolteadas[elecciones[0]] = false
        cartasVolteadas[elecciones[1]] = false
    }

    elecciones[0] = -1
    elecciones[1] = -1
}


@Composable
fun Carta(index: Int, imagenVolteada: Int){
    val coroutineScope = rememberCoroutineScope()

    val imagenActual = when(cartasVolteadas[index]){
        true -> imagenVolteada
        false -> R.drawable.reverso_naipe
    }

    Image(
        painterResource(imagenActual),
        "Carta",
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                cartasVolteadas[index] = true

                if(elecciones[0] == -1){
                    Log.d("Cartas", "Primera elección: $index")
                    elecciones[0] = index
                }else if(elecciones[0] != index && elecciones[1] == -1){
                    Log.d("Cartas", "Segunda elección: $index")
                    elecciones[1] = index

                    coroutineScope.launch {
                        comprobarElecciones(cartasVolteadas)
                    }
                }
            }
    )
}

@Composable
fun MemoryTron(innerPadding: PaddingValues) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF09810F))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.wrapContentHeight()

                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            items(cartasEscogidas.toList()) {
                val indice = it.first
                val imagenCarta = it.second

                Carta(indice, imagenCarta)
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    for(i in cartasVolteadas.indices){
                        cartasVolteadas[i] = false
                    }
                }
            ) {
                Text("Reiniciar")
            }
        }

    }

}
