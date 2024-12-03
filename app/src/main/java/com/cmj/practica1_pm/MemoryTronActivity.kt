package com.cmj.practica1_pm

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme

class MemoryTronActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val listaCartas = mutableListOf(
            R.drawable.carta1,
            R.drawable.carta2,
            R.drawable.carta3
        )

        setContent {
            val cartasEscogidas = remember { mutableStateMapOf<Int, Int>() } //Indice - Imagen
            var cartasVolteadas = remember { mutableStateListOf<Boolean>() }

            var indice = 0
            var imagenCarta: Int
            var imagenValida: Boolean
            var ocurrenciasImagen: Int

            while(cartasEscogidas.size<6){
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
                            innerPadding,
                            cartasEscogidas,
                            cartasVolteadas
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Carta(index: Int, volteada: Boolean, imagen: Int){
    Image(
        painterResource(imagen),
        "Carta",
        modifier = Modifier
            .padding(10.dp),
    )
}

@Composable
fun MemoryTron(innerPadding: PaddingValues, cartasEscogidas: Map<Int, Int>, cartasVolteadas: List<Boolean>) {
    var eleccion1 = rememberSaveable { mutableStateOf("") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.wrapContentHeight()
            .background(Color(0xFF09810F))
            .padding(innerPadding)
            .padding(horizontal = 20.dp)
    ) {
        items(cartasEscogidas.toList()) {
            val indice = it.first
            val imagenCarta = it.second

            Carta(indice, cartasVolteadas[indice], imagenCarta)
        }
    }
}
