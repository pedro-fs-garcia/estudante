package com.estudante

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.estudante.ui.navigation.NavGraph
import com.estudante.ui.theme.EstudanteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EstudanteTheme {
                PageBuilder()
            }
        }
    }
}

@Composable
fun PageBuilder(){
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(layoutDirection),
                    end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(layoutDirection),
                    bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding(),
                )
                .statusBarsPadding()
        ){
            ScreenBuilder()
        }
    }
}


@Composable
fun ScreenBuilder(){
    val modifier = Modifier
    val navController = rememberNavController()
    val navGraph = NavGraph()
    Column(
        modifier = modifier
            .fillMaxSize()
    ){
        //conteúdo que compoe o corpo da pagina
        navGraph.ScreenNavigation(navController)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EstudanteTheme {
        PageBuilder()
    }
}