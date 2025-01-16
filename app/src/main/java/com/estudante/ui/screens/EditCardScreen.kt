package com.estudante.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.estudante.R
import com.estudante.models.StudentCard
import com.estudante.repository.StudentCardRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EditCardScreen (studentCard: StudentCard) {

    var studentCard = studentCard
    private var primaryLogoUri by mutableStateOf(studentCard.primaryLogo)
    private var secondaryLogoUri by mutableStateOf(studentCard.secondaryLogo)
    private var profileImageUri by mutableStateOf(studentCard.profileImage)
    private var studentNameField by mutableStateOf(studentCard.studentName)
    private var studentIdField by mutableStateOf(studentCard.studentId)
    private var studentCourseField by mutableStateOf(studentCard.studentCourse)
    private var studentCicleField by mutableStateOf(studentCard.studentCicle)
    private var yearField by mutableStateOf(studentCard.year)

    @Composable
    fun BuildScreen(navController: NavController, modifier:Modifier = Modifier){
        val context = LocalContext.current
        val scrollState = rememberScrollState()
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(color = Color.White)
                .verticalScroll(scrollState)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = "back",
                    tint = Color.Black,
                    modifier = modifier
                        .clickable {
                            navController.navigate("home")
                        }
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                ){
                    Text(
                        text = "Modo de edição",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.DarkGray
                    )
                    Text(text = "Clique nos itens para editá-los", color = Color.DarkGray)
                }
            }

            Spacer(modifier = modifier.height(8.dp))
            PrimaryLogoSelection(context)
            Spacer(modifier = modifier.height(16.dp))
            ProfilePictureSelection(context)
            Spacer(modifier = modifier.height(16.dp))
            InfoCard(context)
            Spacer(modifier = modifier.height(16.dp))
            SecondaryLogoSelection(context)
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
                        studentCard.studentId = studentIdField
                        studentCard.studentCicle = studentCicleField
                        studentCard.year = yearField
                        val studentCardRepository = StudentCardRepository(context)
                        studentCardRepository.updateStudentCardById(studentCard)
                        navController.navigate("home")
                    },
                ) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = ""
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    Text(text = "Salvar Alterações")
                }
            }
        }
    }

    @Composable
    fun InfoCard(context: Context, modifier: Modifier = Modifier){
        Card (
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = modifier
                .fillMaxWidth()
        ){
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier
                    .padding(8.dp)
            ){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp)
                ){
                    BasicTextField(
                        value = studentNameField,
                        onValueChange = {
                            studentNameField = it
                            studentCard.studentName = it
                        },
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = modifier
                            .wrapContentSize()
                            .align(Alignment.CenterVertically),
                        decorationBox = { innerTextField ->
                            Box{
                                innerTextField() // Renderiza o campo de texto sem decorações adicionais
                            }
                        }
                    )

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(top = 4.dp, bottom = 4.dp).wrapContentSize()
                ){
                    Text(
                        text = "Matrícula: ",
                        fontWeight = FontWeight.Bold
                    )
                    BasicTextField(
                        value = studentIdField,
                        onValueChange = {
                            studentIdField = it
                            studentCard.studentId = it
                        },
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                        modifier = modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            Box{
                                innerTextField() // Renderiza o campo de entrada sem decorações adicionais
                            }
                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(top = 4.dp, bottom = 4.dp).wrapContentSize()
                ){
                    Text(
                        text = "Curso: ",
                        fontWeight = FontWeight.Bold
                    )
                    BasicTextField(
                        value = studentCourseField,
                        onValueChange = {
                            studentCourseField = it
                            studentCard.studentCourse = it
                        },
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                        modifier = modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            Box{
                                innerTextField() // Renderiza o campo de entrada sem decorações adicionais
                            }
                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(top = 4.dp, bottom = 4.dp).wrapContentSize()
                ){
                    Text(
                        text = "Ciclo: ",
                        fontWeight = FontWeight.Bold
                    )
                    BasicTextField(
                        value = studentCicleField,
                        onValueChange = {
                            studentCicleField = it
                            studentCard.studentCicle = it
                        },
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                        modifier = modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            Box{
                                innerTextField() // Renderiza o campo de entrada sem decorações adicionais
                            }
                        }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp)
                ){
                    BasicTextField(
                        value = yearField,
                        onValueChange = {
                            yearField = it
                            studentCard.year = it
                        },
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = modifier
                            .wrapContentSize()
                            .align(Alignment.CenterVertically),
                        decorationBox = { innerTextField ->
                            Box{
                                innerTextField()
                            }
                        }
                    )
                }
            }
        }
    }


    @Composable
    fun PrimaryLogoSelection(context: Context, modifier: Modifier = Modifier){
        val imageUri = remember {
            mutableStateOf<Uri?>(Uri.parse(studentCard.primaryLogo)) // Carrega a imagem salva inicialmente
        }

        val launcherGallery = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                // Salva a imagem da galeria no armazenamento interno
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                val savedUri = saveImageToInternalStorage(context, bitmap)
                imageUri.value = savedUri // Atualiza o estado imediatamente
                studentCard.primaryLogo = savedUri.toString()
                primaryLogoUri = savedUri.toString()
            }
        }

        AsyncImage(
            model = imageUri.value ?: R.drawable.ic_launcher_background,
            contentDescription = "primary logo",
            modifier = modifier
                .height(128.dp)
                .background(color = Color.White)
                .clickable {
                    // Exemplo: lógica de seleção
                    val options = listOf("Galeria", "Câmera")
                    val selectedOption = options[0] // Substituir por lógica de seleção
                    when (selectedOption) {
                        "Galeria" -> launcherGallery.launch("image/*")
                    }
                }
        )
    }

    @Composable
    fun ProfilePictureSelection(context: Context, modifier: Modifier = Modifier) {
        // Estado para armazenar a imagem do perfil
        val imageUri = remember {
            mutableStateOf<Uri?>(Uri.parse(studentCard.profileImage)) // Carrega a imagem salva inicialmente
        }

        // Lançadores de Intent
        val launcherGallery = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                // Salva a imagem da galeria no armazenamento interno
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                val savedUri = saveImageToInternalStorage(context, bitmap)
                imageUri.value = savedUri // Atualiza o estado imediatamente
                studentCard.profileImage = savedUri.toString()
                profileImageUri = savedUri.toString()
            }
        }

        // Interface do usuário
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            AsyncImage(
                model = imageUri.value ?: R.drawable.ic_launcher_background,
                contentDescription = "profile picture",
                modifier = modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        // Exemplo: lógica de seleção
                        val options = listOf("Galeria", "Câmera")
                        val selectedOption = options[0] // Substituir por lógica de seleção
                        when (selectedOption) {
                            "Galeria" -> launcherGallery.launch("image/*")
                        }
                    },
                contentScale = ContentScale.Crop
            )
        }
    }

    @Composable
    fun SecondaryLogoSelection(context: Context, modifier: Modifier = Modifier){
        val imageUri = remember {
            mutableStateOf<Uri?>(Uri.parse(studentCard.secondaryLogo)) // Carrega a imagem salva inicialmente
        }
        val launcherGallery = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                val savedUri = saveImageToInternalStorage(context, bitmap)
                imageUri.value = savedUri // Atualiza o estado imediatamente
                studentCard.secondaryLogo = savedUri.toString()
                secondaryLogoUri = savedUri.toString()
            }
        }

        AsyncImage(
            model = imageUri.value ?: R.drawable.ic_launcher_background,
            contentDescription = "secondary logo",
            modifier = modifier
                .height(75.dp)
                .clickable {
                    // Exemplo: lógica de seleção
                    val options = listOf("Galeria", "Câmera")
                    val selectedOption = options[0] // Substituir por lógica de seleção
                    when (selectedOption) {
                        "Galeria" -> launcherGallery.launch("image/*")
                    }
                }
        )
    }

    // Função para salvar a imagem no armazenamento interno
    private fun saveImageToInternalStorage(context: Context, bitmap: Bitmap): Uri? {
        val filename = "image_${System.currentTimeMillis()}.png" // Nome do arquivo salvo
        val file = File(context.filesDir, filename) // Diretório interno do aplicativo
        return try {
            FileOutputStream(file).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            }
            Uri.fromFile(file) // Retorna a URI do arquivo salvo
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    // Função para carregar a imagem salva
    private fun loadImageFromInternalStorage(context: Context): Uri? {
        val file = File(context.filesDir, "profile_picture.jpg")
        return if (file.exists()) {
            Uri.fromFile(file) // Retorna a URI do arquivo existente
        } else {
            null // Retorna null se o arquivo não existir
        }
    }
}