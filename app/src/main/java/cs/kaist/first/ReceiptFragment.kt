package cs.kaist.first

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class ReceiptFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_receipt, container, false)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
        launcher.launch(intent)
//        startActivity(intent)

        return view
    }

    val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
//            val intent: Intent? = result.data
//            val uri: Uri? = intent?.data
            val data: Intent? = result.data
//            val imagePath: String? = data?.getStringExtra(MediaStore.EXTRA_OUTPUT)
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val path = bitmapToUri(imageBitmap)

            println(path)
            if (path != null) {
                DetectText.detectText(requireContext(), path)
            }

        }
    }

    private fun bitmapToUri(bitmap: Bitmap): String {
        val resolver: ContentResolver = requireContext().contentResolver

//        val imagesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imagesDirectory =  requireContext().filesDir
        val fileName = "receipt2.jpg"
        val imageFile = File(imagesDirectory, fileName)

        // 비트맵을 JPEG 파일로 저장
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }

        // 저장한 이미지 파일의 Uri를 가져오기
//        val contentValues = ContentValues().apply {
//            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
//            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//            put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
//        }
        return imageFile.absolutePath
    }



}