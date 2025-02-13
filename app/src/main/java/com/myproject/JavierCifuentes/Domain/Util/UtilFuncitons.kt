package com.myproject.JavierCifuentes.Domain.Util

import android.content.Context
import android.net.Uri
import java.io.File

fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val fileName = "receta_${System.currentTimeMillis()}.png"
    val file = File(context.filesDir, fileName)

    file.outputStream().use { output ->
        inputStream.copyTo(output)
    }

    return file.absolutePath
}