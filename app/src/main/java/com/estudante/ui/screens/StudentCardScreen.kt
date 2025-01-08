package com.estudante.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.estudante.R
import com.estudante.models.StudentCard
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class StudentCardScreen () {

    @Composable
    fun BuildScreen(navController:NavController, studentCard:StudentCard, modifier: Modifier = Modifier){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()

        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ){
                Image(
                    painter = painterResource(R.drawable.logo_fatec_ajustado),
                    contentDescription = "school logo",
                    modifier = modifier
                        .height(128.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ){
                ProfilePictureSelection()
            }
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
                        Text(
                            text = studentCard.studentName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(modifier = modifier.padding(top = 4.dp, bottom = 4.dp)){
                        Text(
                            text = "Matrícula: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = studentCard.studentId.toString()
                        )
                    }
                    Row(modifier = modifier.padding(top = 4.dp, bottom = 4.dp)){
                        Text(
                            text = "Curso: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = studentCard.studentCourse
                        )
                    }
                    Row(modifier = modifier.padding(top = 4.dp, bottom = 4.dp)){
                        Text(
                            text = "Ciclo: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = studentCard.studentCicle
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp)
                    ){
                        Text(
                            text = studentCard.year,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
            Spacer(modifier = modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxWidth()
            ){
                Image(
                    painter = painterResource(R.drawable.cps_ajustado),
                    contentDescription = "profile picture",
                    modifier = modifier
                        .height(75.dp)

                )
            }
        }
    }
}

@Composable
fun ProfilePictureSelection(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Estado para armazenar a imagem do perfil
    val imageUri = remember {
        mutableStateOf<Uri?>(loadImageFromInternalStorage(context)) // Carrega a imagem salva inicialmente
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
        }
    }

    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            // Salva a imagem capturada no armazenamento interno
            val savedUri = saveImageToInternalStorage(context, bitmap)
            imageUri.value = savedUri // Atualiza o estado imediatamente
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
                        "Câmera" -> launcherCamera.launch(null)
                    }
                },
            contentScale = ContentScale.Crop
        )
    }
}


// Função para salvar a imagem no armazenamento interno
private fun saveImageToInternalStorage(context: Context, bitmap: Bitmap): Uri? {
    val filename = "profile_picture.jpg" // Nome do arquivo salvo
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





