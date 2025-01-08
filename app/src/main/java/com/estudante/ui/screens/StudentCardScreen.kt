package com.estudante.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.estudante.models.StudentCard

class StudentCardScreen(private val studentCard: StudentCard) {

    @Composable
    fun BuildScreen(navController: NavHostController) {
        // Use os dados de studentCard para exibir na tela
        Column {
            studentCard.primaryLogo?.let { uri ->
                Image(painter = rememberAsyncImagePainter(uri), contentDescription = "Primary Logo")
            }
            Text(text = "Nome: ${studentCard.studentName}")
            Text(text = "Curso: ${studentCard.studentCourse}")
            // Outros elementos...
        }
    }
}