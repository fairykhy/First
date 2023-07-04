package cs.kaist.first

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import android.Manifest
import android.Manifest.permission.CAMERA
import android.graphics.ImageDecoder
import java.io.IOException
import java.util.Date
import android.content.Context

class ReceiptFragment : Fragment() {

    private val GALLERY = 1
    private val REQUEST_IMAGE_CAPTURE = 2
    private val REQUEST_CREATE_EX = 3

        private fun requestPermission(){
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,CAMERA),1)

    }
    private fun checkPermission():Boolean{

        return (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)

    }
    @Override
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if( requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(requireContext(), "권한 설정 OK", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(requireContext(), "권한 허용 안됨", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_receipt, container, false)

        if(checkPermission()){
            dispatchTakePictureIntentEx(view)
        }
        else{
            requestPermission()
        }
        return view
    }

    fun createImageUri(filename: String, mimeType: String): Uri? {
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(storageDir, filename)

        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        values.put(MediaStore.Images.Media.DATA, file.absolutePath) // 파일 경로 추가

        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    private var photoURI : Uri? = null

    private fun dispatchTakePictureIntentEx(view: View): String {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val takePictureIntent : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val fileName = "JPEG_${timeStamp}_"
        val uri : Uri? =   createImageUri(fileName, "image/jpeg")
        photoURI = uri
        val photo = view.findViewById<ImageView>(R.id.imageView)
        photo.setImageURI(uri)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(takePictureIntent, REQUEST_CREATE_EX)
        return fileName
    }
    fun loadBitmapFromMediaStoreBy(photoUri: Uri) : Bitmap?{
        var image: Bitmap? = null
        try{
            image = if(Build.VERSION.SDK_INT > 27){
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(requireContext().contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)

            }else{
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, photoUri)
            }
        }catch(e: IOException){
            e.printStackTrace()
        }
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CREATE_EX && resultCode == RESULT_OK) {
//            println(data)
            val bitmap = loadBitmapFromMediaStoreBy(photoURI!!)
            val image = requireView().findViewById<ImageView>(R.id.imageView)
            image.setImageBitmap(bitmap)

            DetectText.detectText(requireContext(), photoURI!!)
        }
    }



}