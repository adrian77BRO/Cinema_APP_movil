package com.example.apptransaccional.home.presentation.views

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.apptransaccional.home.presentation.viewmodel.ProfileViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun ProfileScreen(username: String, profileViewModel: ProfileViewModel) {
    val context = LocalContext.current
    val imageUri by profileViewModel.profileImageUri.observeAsState()

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val uri = saveImageToInternalStorage(context, it)
            profileViewModel.setProfileImage(uri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { profileViewModel.setProfileImage(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mi perfil: $username",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            } ?: Icon(
                Icons.Default.AccountCircle,
                contentDescription = "Foto de perfil",
                modifier = Modifier.size(150.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { cameraLauncher.launch() },
                colors = ButtonDefaults.buttonColors(Color.Blue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tomar foto", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { galleryLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(Color.Blue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seleccionar de galería", color = Color.White)
            }
        }
    }
}

fun saveImageToInternalStorage(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.filesDir, "profile_picture.jpg")
    FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
    return Uri.fromFile(file)
}

/*@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel) {
    val context = LocalContext.current
    val imageUri by profileViewModel.profileImageUri.observeAsState()

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val uri = saveImageToInternalStorage(context, it)
            profileViewModel.setProfileImage(uri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { profileViewModel.setProfileImage(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        imageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Foto de perfil",
                modifier = Modifier.size(150.dp).clip(CircleShape).border(2.dp, Color.White, CircleShape)
            )
        } ?: Icon(Icons.Default.AccountCircle, contentDescription = "Foto de perfil", modifier = Modifier.size(150.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { cameraLauncher.launch() }) {
                Text("Tomar foto")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Seleccionar de galería")
            }
        }
    }
}*/