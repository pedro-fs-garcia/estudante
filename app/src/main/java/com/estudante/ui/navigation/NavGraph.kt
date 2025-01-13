package com.estudante.ui.navigation

//import com.estudante.ui.screens.FatecCardScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.estudante.models.StudentCard
import com.estudante.repository.StudentCardRepository
import com.estudante.ui.screens.CreateNewScreen
import com.estudante.ui.screens.HomeScreen
import com.estudante.ui.screens.StudentCardScreen

class NavGraph {

    @Composable
    fun ScreenNavigation(navController: NavHostController){
        NavHost(navController = navController, startDestination = "home"){
            composable("home") {
                val context = LocalContext.current
                HomeScreen().BuildScreen(navController = navController, context)
            }
            composable(
                route = "studentCard/{studentId}",
                arguments = listOf(navArgument("studentId") { type = NavType.LongType })
            ) { backStackEntry ->
                val context = LocalContext.current
                val cardId = backStackEntry.arguments?.getLong("studentId") ?:0L

                val repository = StudentCardRepository(context)
                val studentCard = repository.loadStudentCardFromId(cardId)
                StudentCardScreen().BuildScreen(navController = navController, studentCard = studentCard ?: StudentCard())

            }
            composable("fatec"){
                //FatecCardScreen().BuildScreen(navController = navController)
            }
            composable("create_new"){
                val context = LocalContext.current
                CreateNewScreen().BuildScreen(navController = navController, context = context)
            }
        }
    }
}