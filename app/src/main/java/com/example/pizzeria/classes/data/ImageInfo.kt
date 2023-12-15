package com.example.pizzeria.classes.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

data class ImageItem(val name: String, val image: String)

private class DownloadImageTask(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
    override fun doInBackground(vararg urls: String): Bitmap? {
        val url = URL(urls[0])
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        return BitmapFactory.decodeStream(input)
    }

    override fun onPostExecute(result: Bitmap?) {
        if (result != null) {
            imageView.setImageBitmap(result)
        }
    }
}




    @RequiresApi(Build.VERSION_CODES.O)
    fun descargarImagenDesdeStorage(nombreArchivo: String, destinoLocal: String) {
        val storage = FirebaseStorage.getInstance()

        // Obtiene la referencia al bucket
        val storageReference: StorageReference = storage.getReferenceFromUrl("gs://pizzeria-app-f6984.appspot.com/pizzas")

        // Obtiene la referencia al archivo dentro del bucket
        val archivoReference = storageReference.child(nombreArchivo)

        // Crea un archivo local para escribir el contenido
        val archivoLocal = File(destinoLocal)

        // Descarga el archivo y escribe los bytes en el archivo local
        archivoReference.getFile(archivoLocal).addOnSuccessListener {
            println("Imagen descargada exitosamente a: $destinoLocal")
        }.addOnFailureListener {
            println("Error al descargar la imagen: $it")
        }
    }




