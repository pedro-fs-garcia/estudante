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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
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
                .background(color = Color.White)

        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ){

                val painter = rememberAsyncImagePainter(studentCard.primaryLogo?.let { File(it) })
                Image(
                    painter = painter,
                    contentDescription = "primary logo",
                    modifier = modifier
                        .height(128.dp)
                        .background(color = Color.White)
                )

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ){
                //ProfilePictureSelection()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    AsyncImage(
                        model = studentCard.profileImage,
                        contentDescription = "profile picture",
                        modifier = modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                        contentScale = ContentScale.Crop
                    )
                }
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
                AsyncImage(
                    model = studentCard.secondaryLogo ?: R.drawable.ic_launcher_background,
                    contentDescription = "secondary logo",
                    modifier = modifier
                        .height(75.dp)
                )
            }
        }
    }
}





