package com.estudante.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.estudante.models.StudentCard
import com.estudante.repository.StudentCardRepository
import com.estudante.ui.screens.CreateNewScreen
import com.estudante.ui.screens.FatecCardScreen
import com.estudante.ui.screens.HomeScreen
import com.estudante.ui.screens.StudentCardScreen

class NavGraph {

    @Composable
    fun ScreenNavigation(navController: NavHostController){
        val context = LocalContext.current
        NavHost(navController = navController, startDestination = "home"){
            composable("home") {
                HomeScreen().BuildScreen(navController = navController)
            }
            composable(
                route = "studentCard/{studentId}",
                arguments = listOf(navArgument("studentId") { type = NavType.LongType })
            ) { backStackEntry ->
                val context = LocalContext.current
                val cardId = backStackEntry.arguments?.getLong("studentId")
                val studentCard = remember { StudentCard() } // Instância temporária para armazenar os dados
                val studentCardRepository = StudentCardRepository()

                if (cardId != null) {
                    LaunchedEffect(cardId) {
                        val loadedCard = studentCardRepository.loadStudentCard(cardId, context)
                        if (loadedCard != null) {
                            studentCard.apply {
                                primaryLogo = loadedCard.primaryLogo
                                secondaryLogo = loadedCard.secondaryLogo
                                profileImage = loadedCard.profileImage
                                studentName = loadedCard.studentName
                                studentId = loadedCard.studentId
                                studentCourse = loadedCard.studentCourse
                                studentCicle = loadedCard.studentCicle
                                year = loadedCard.year
                            }
                        }
                    }
                }

                StudentCardScreen(studentCard).BuildScreen(navController = navController)
            }
            composable("fatec"){
                FatecCardScreen().BuildScreen(navController = navController)
            }
            composable("create_new"){
                CreateNewScreen().BuildScreen(navController = navController)
            }
        }
    }
}