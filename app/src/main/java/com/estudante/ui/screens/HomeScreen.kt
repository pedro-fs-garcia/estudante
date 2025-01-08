package com.estudante.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.estudante.PageBuilder
import com.estudante.ui.theme.EstudanteTheme

class HomeScreen {

    @Composable
    fun BuildScreen(navController: NavController, modifier : Modifier = Modifier){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(90.dp)
                    .clickable { navController.navigate("fatec") }
            ) {
                Text("FATEC")
            }
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = "Adicionar",
                modifier = modifier
                    .height(56.dp)
                    .width(56.dp)
                    .clickable{ navController.navigate("create_new")}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EstudanteTheme {
        val navController = rememberNavController()
        HomeScreen().BuildScreen(navController)
    }
}