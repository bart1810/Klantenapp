package android.compose.presentation.views.screens.account

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import android.compose.R
import android.compose.data.local.AuthPreferences
import android.compose.data.repository.damageForm.DamageFormRepositoryImplementation
import android.compose.presentation.viewmodels.DamageFormViewModel
import android.compose.util.RetrofitInstance
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.CircularProgressIndicator
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DamageFormScreen(navController: NavController) {
    val context = LocalContext.current
    val authPreferences = AuthPreferences(context)
    val viewModel: DamageFormViewModel = viewModel(factory = DamageFormViewModelFactory(authPreferences))

    var kmStand by rememberSaveable { mutableStateOf("") }
    val options = listOf("Frontal damage", "Side damage (right)", "Side damage (left)", "Back damage", "Under damage")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var sendingForm by remember { mutableStateOf(false) }
    var triggerSendForm by remember { mutableStateOf(false) }

    val isLoading = viewModel.isLoading.value
    val toastMessage = viewModel.toastMessage.collectAsState(initial = null)

    LaunchedEffect(triggerSendForm) {
        if (triggerSendForm) {
            viewModel.processImageAndSendForm(context, capturedImageUri, kmStand, selectedOptionText)
            sendingForm = false
        }
    }

    LaunchedEffect(toastMessage.value) {
        toastMessage.value?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            navController.popBackStack()
        }
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
               capturedImageUri = imageUri
            }
        }
    )

    val onTakePhotoClicked = {
        try {
            // Create an image file and get the URI for that file
            val photoFile = createImageFile(context)
            val authority = context.getString(R.string.file_provider_authority)
            imageUri = FileProvider.getUriForFile(
                context,
                authority,
                photoFile
            )

            takePictureLauncher.launch(imageUri).also {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.resolveActivity(context.packageManager)?.also {
                    val resInfoList: List<ResolveInfo> = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                    for (resolveInfo in resInfoList) {
                        val packageName: String = resolveInfo.activityInfo.packageName
                        context.grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    }
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                onTakePhotoClicked()
            } else {
                TODO()
            }
        }
    )
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                Alignment.TopStart
            ) {
                Text(
                    text = "Report Damage",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            TextField(
                value = kmStand,
                onValueChange = { kmStand = it },
                label = { Text("Kilometer Stand") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) // Show number keyboard
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = {},
                    label = { Text("Damage type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            capturedImageUri?.let { uri ->
                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp))
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to take a photo
            Button(onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) -> {
                        onTakePhotoClicked()
                    }

                    else -> {
                        requestPermissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                }
            }) {
                Text("Take a photo of the damage")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    Log.d("DamageFormScreen", "Button clicked")
                    if (!sendingForm) {
                        sendingForm = true
                        triggerSendForm = true
                    }
                },
                enabled = !sendingForm && !isLoading
            ) {
                Text(if (!sendingForm) "Send Damage Form" else "Sending...")
            }
        }
    }
}

@Throws(IOException::class)
fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
    Log.d("ImageFile", "File path: ${imageFile.absolutePath}")
    return imageFile
}

class DamageFormViewModelFactory(
    private val authPreferences: AuthPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DamageFormViewModel::class.java)) {
            return DamageFormViewModel(
                DamageFormRepositoryImplementation(RetrofitInstance.autoMaatApi),
                authPreferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
