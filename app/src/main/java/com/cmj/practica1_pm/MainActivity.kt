package com.cmj.practica1_pm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmj.practica1_pm.ui.theme.Practica1PMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica1PMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MenuInicio(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MenuInicio(modifier: Modifier = Modifier) {
    val contexto = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(modifier = Modifier
            .padding(vertical = 30.dp)
            .width(200.dp),
            onClick = {
                val intent = Intent(contexto, MemoryTronActivity::class.java)
                contexto.startActivity(intent)
            }
        ) {
            Text(text = "MemoryTron")
        }

        Button(modifier = Modifier
            .padding(vertical = 30.dp)
            .width(200.dp),
            onClick = {

            }
        ) {
            Text(text = "CalculaTron")
        }
    }
}

@Preview(showBackground = true, heightDp = 915, widthDp = 412)
@Composable
fun MenuInicioPreview() {
    Practica1PMTheme {
        MenuInicio()
    }
}