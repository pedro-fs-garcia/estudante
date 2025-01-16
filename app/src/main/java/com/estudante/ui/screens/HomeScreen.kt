package com.estudante.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.estudante.R
import com.estudante.models.StudentCard
import com.estudante.repository.StudentCardRepository
import com.estudante.ui.theme.EstudanteTheme

class HomeScreen {

    @Composable
    fun BuildScreen(navController: NavController, context: Context, modifier : Modifier = Modifier){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(126.dp)
                ) {
                    // Cabeçalho fixo na parte superior
                    Text(
                        text = "e-studante",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.DarkGray
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    // Conteúdo que compõe o corpo da página
                    CardsList(navController, context)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    //Menu fixo na parte inferior
                    Icon(
                        Icons.Filled.AddCircle,
                        contentDescription = "Adicionar",
                        tint = Color.DarkGray,
                        modifier = modifier
                            .height(56.dp)
                            .width(56.dp)
                            .clickable{ navController.navigate("create_new")}
                    )
                }
            }
        }
    }

    @Composable
    fun CardsList(navController: NavController, context: Context, modifier: Modifier = Modifier){
        var studentCards by remember { mutableStateOf<List<StudentCard>>(emptyList()) }
        LaunchedEffect(Unit) {
            studentCards = loadCards(navController, context)
        }

        val scrollState = rememberScrollState()

        if (studentCards.isEmpty()) {
            Text("Carregando carteirinhas...", modifier = Modifier.fillMaxWidth())
        } else {
            Column (
                modifier.verticalScroll(scrollState)
            ) {
                for (card in studentCards){
                    StudentCardItem(card, navController, context, modifier)
                }
            }
        }
    }

    @Composable
    fun StudentCardItem(studentCard:StudentCard, navController: NavController, context:Context, modifier:Modifier){
        var expanded by remember { mutableStateOf(false) }
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(90.dp)
                .clickable { navController.navigate("studentCard/" + studentCard.studentId) }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ){
                    AsyncImage(
                        model = studentCard.primaryLogo ?: R.drawable.ic_launcher_background,
                        contentDescription = "primary logo",
                        alignment = Alignment.Center,
                        modifier = modifier
                            .size(60.dp)
                            .align(Alignment.CenterVertically)
                            .background(MaterialTheme.colorScheme.surface),
                        contentScale = ContentScale.Crop
                    )
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .padding(8.dp)
                    ) {
                        Text(text = studentCard.studentName)
                        Text(text = studentCard.year)
                    }

                    IconButton(
                        onClick = { expanded = !expanded },
                    ){
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },

                            ) {
                            DropdownMenuItem(
                                text = { Text("Deletar") },
                                onClick = {
                                    val repository = StudentCardRepository(context)
                                    repository.deleteStudentCardById(studentCard.studentId, context)
                                    navController.navigate("home")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Editar") },
                                onClick = {
                                    navController.navigate("edit_card/" + studentCard.studentId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }


    fun loadCards(navController:NavController, context: Context): List<StudentCard> {
        val repository = StudentCardRepository(context)
        val allEntitiesFromDatabase = repository.getAllStudentCards()
        return allEntitiesFromDatabase
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EstudanteTheme {
        val context = LocalContext.current
        val navController = rememberNavController()
        HomeScreen().BuildScreen(navController, context)
    }
}