package com.estudante.ui.screens

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.estudante.database.DatabaseInstance
import com.estudante.models.StudentCard
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
            CardsList(navController, context)
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

    @Composable
    fun CardsList(navController: NavController, context: Context, modifier: Modifier = Modifier){
        var studentCards by remember { mutableStateOf<List<StudentCard>>(emptyList()) }
        LaunchedEffect(Unit) {
            studentCards = loadCards(navController, context)
        }

        val scrollState = rememberScrollState()

        if (studentCards.isEmpty()) {
            Text("Carregando carteirinhas...", modifier = Modifier.fillMaxSize())
        } else {
            Column (
                modifier.verticalScroll(scrollState)
            ) {
                for (card in studentCards){
                    StudentCardItem(card, navController, modifier)
                }
            }
        }
    }

    @Composable
    fun StudentCardItem(studentCard:StudentCard, navController: NavController, modifier:Modifier){
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
            Text(studentCard.studentName)
        }
    }


    suspend fun loadCards(navController:NavController, context: Context): List<StudentCard> {
        val dao = DatabaseInstance.getDatabase(context).studentCardDao()
        val allEntitiesFromDatabase = dao.getAllStudentCards()
        val allCards = mutableListOf<StudentCard>()
        for (entity in allEntitiesFromDatabase){
            val newCard = StudentCard()
            newCard.studentName = entity.studentName
            newCard.studentCourse = entity.studentCourse
            newCard.studentCicle = entity.studentCicle
            newCard.studentId = entity.studentId
            newCard.primaryLogo = entity.primaryLogoUri?.toUri()
            newCard.secondaryLogo = entity.secondaryLogoUri?.toUri()
            newCard.profileImage = entity.profileImageUri?.toUri()
            allCards.add(newCard)
        }
        return allCards
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