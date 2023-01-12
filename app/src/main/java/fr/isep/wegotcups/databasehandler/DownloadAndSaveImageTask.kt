package fr.isep.wegotcups.databasehandler

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import fr.isep.wegotcups.MainActivity
import fr.isep.wegotcups.home.EventItemViewModel
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class DownloadAndSaveImageTask(val context: Context) : AsyncTask<Pair<String, ImageView>, Unit, ImageView?>() {
    private var mContext: WeakReference<Context> = WeakReference(context)
    private lateinit var file: File

    override fun doInBackground(vararg pairArray: Pair<String, ImageView>): ImageView? {
        var result = false
        val (url, imageView) = pairArray[0]
        val names = url?.split('/')?.last()?.split('?')?.first()?.split("%2F")
        val requestOptions = RequestOptions().override(100)
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        file = File("/data/data/fr.isep.wegotcups/files/${names?.get(0) as String}/${names?.get(1) as String}")
        if (!file.exists())
        mContext.get()?.let {
            val bitmap = Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .submit()
                .get()

            try {
                var file = File(it.filesDir, names?.get(0))
                if (!file.exists()) {
                    file.mkdir()
                }
                file = File(file, names?.get(1))
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
                out.flush()
                out.close()
                Log.i("Glide", "Image saved.")
                result = true
            } catch (e: Exception) {
                Log.i("Glide", "Failed to save image.")
            }
        }
        if (result){
            return imageView
        }
        return null
    }

    override fun onPostExecute(result: ImageView?) {
        result?.setImageURI(file.toUri())
    }

}