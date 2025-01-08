@file:OptIn(ExperimentalMaterial3Api::class)

package com.estudante.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.estudante.models.StudentCard
import com.estudante.repository.StudentCardRepository

class CreateNewScreen {
    var newStudentCard = StudentCard()

    @Composable
    fun BuildScreen(navController: NavController, modifier: Modifier = Modifier){
        val scrollState = rememberScrollState()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            StudentCardEditor(studentCard = newStudentCard, navController = navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCardEditor(studentCard: StudentCard, navController:NavController, modifier: Modifier = Modifier){
    var primaryLogoUri by remember { mutableStateOf<Uri?>(null) }
    var secondaryLogoUri by remember { mutableStateOf<Uri?>(null) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var studentNameField by remember { mutableStateOf("")}
    var studentIdField by remember { mutableStateOf("")}
    var studentCourseField by remember { mutableStateOf("")}
    var studentCicleField by remember { mutableStateOf("")}
    var yearField by remember { mutableStateOf("")}
    val context = LocalContext.current

    val primaryLogoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        primaryLogoUri = uri
    }

    val secondaryLogoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        secondaryLogoUri = uri
    }

    val profileImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }


    Column(){

        //campos de texto
        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
        ){
            Column(
                modifier = modifier
                    .padding(8.dp)
            ){
                Text(
                    text = "Insira os dados do estudante",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .fillMaxWidth()
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ){
                    TextField(
                        value = studentNameField,
                        onValueChange = {
                            studentNameField = it
                        },
                        shape = RoundedCornerShape(30.dp),
                        label = {Text("Nome do estudante")},
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ){
                    TextField(
                        value = studentIdField,
                        onValueChange = {
                            studentIdField = it
                        },
                        shape = RoundedCornerShape(30.dp),
                        label = {Text("Número de matricula")},
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ){
                    TextField(
                        value = studentCourseField,
                        onValueChange = {
                            studentCourseField = it
                        },
                        shape = RoundedCornerShape(30.dp),
                        label = {Text("Nome do curso")},
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ){
                    TextField(
                        value = studentCicleField,
                        onValueChange = {
                            studentCicleField = it
                        },
                        shape = RoundedCornerShape(30.dp),
                        label = {Text("Ciclo do curso")},
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ){
                    TextField(
                        value = yearField,
                        onValueChange = {
                            yearField = it
                        },
                        shape = RoundedCornerShape(30.dp),
                        label = {Text("Ano de validade")},
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = modifier
                            .fillMaxWidth()
                    )
                }
            }
        }

        //campos de imagem
        Column(

        ){
            Row(){
                Button(onClick = { primaryLogoLauncher.launch("image/*") }) {
                    Text(text = "Logo primário")
                }
                AsyncImage(
                    model = primaryLogoUri,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Row(){
                Button(onClick = { secondaryLogoLauncher.launch("image/*") }) {
                    Text(text = "Logo secundário")
                }
                AsyncImage(
                    model = secondaryLogoUri,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Row(){
                Button(onClick = { profileImageLauncher.launch("image/*") }) {
                    Text(text = "Imagem de perfil")
                }
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        //botão de criar
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Button(
                onClick = {
                    studentCard.primaryLogo = primaryLogoUri
                    studentCard.secondaryLogo = secondaryLogoUri
                    studentCard.profileImage = profileImageUri
                    studentCard.studentName = studentNameField
                    studentCard.studentCourse = studentCourseField
                    studentCard.studentId = studentIdField.toLong()
                    studentCard.studentCicle = studentCicleField
                    studentCard.year = yearField
                    val studentCardRepository = StudentCardRepository()
                    studentCardRepository.saveStudentCard(studentCard, context)
                    navController.navigate("studentCard/" + studentCard.studentId.toString())
                },
            ) {
                Icon(
                    Icons.Filled.Done,
                    contentDescription = ""
                )
                Spacer(modifier = modifier.width(4.dp))
                Text(text = "Criar carteirinha")
            }
        }

    }
}
