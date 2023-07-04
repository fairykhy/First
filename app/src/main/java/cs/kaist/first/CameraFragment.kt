package cs.kaist.first

import android.app.Activity.RESULT_OK
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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File
import java.text.SimpleDateFormat
import android.Manifest
import android.Manifest.permission.CAMERA
import android.graphics.ImageDecoder
import android.provider.ContactsContract
import java.io.IOException
import java.util.Date
import androidx.fragment.app.FragmentManager

class CameraFragment : Fragment() {

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

        val view = inflater.inflate(R.layout.fragment_camera, container, false)

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

//            DetectText.detectText(requireContext(), photoURI!!)
            val result = DetectText.detectText(requireContext(), photoURI!!)
            findInfo(result)
        }
    }

    fun findInfo(arr: ArrayList<String>){

        val bundle = Bundle()

        val regex = Regex("[a-zA-Z가-힣]+")
        val num_regex = Regex("^[0-9,]+$")
        if(arr.contains("금액")){
            val result_arr = arrayListOf<String>() // index 0: 매장이름, 1: 결제일자, 2: 금액, 3: 결제방법
            for(i in arr.indices){
                if(arr[i].contains("매장")){
                    if(arr[i+1].matches(regex)){ //영어 및 한글로만 이뤄진 경우
                        result_arr.add(0, arr[i+1])
                    }
                    else if(arr[i+2].matches(regex)){
                        result_arr.add(0, arr[i+2])
                    }
                    else{
                        result_arr.add(0, arr[i+3])
                    }
                }
                if(arr[i].contains("2023")){
                    result_arr.add(1, arr[i])
                }
                if(arr[i].contains("합계금액") or arr[i].contains("총액")){
                    if(arr[i+1].matches(num_regex))
                        result_arr.add(2, arr[i+1])
                }
                if(arr[i].contains("카드") or arr[i].contains("현금")){
                    result_arr.add(3, arr[i])
                }
            }
            bundle.putStringArrayList("result", result_arr)
            ReceiptFragment().arguments = bundle
            childFragmentManager.beginTransaction().replace(R.id.main_frame, ReceiptFragment()).commit()


        }
        else if(arr.contains("010")){
            val koreanSurnames = arrayListOf(
                "김", "이", "박", "최", "정",
                "강", "조", "윤", "장", "임",
                "한", "신", "오", "서", "권"
            )
            val result_arr = arrayListOf<String>() // index 0: 이름, 1: 전화번호
            for(i in arr.indices){
                if(arr[i].length == 3){
                    val first = arr[i].substring(0, 1)
                    if(koreanSurnames.contains(first)){
                        result_arr.add(0, arr[i])
                    }
                }
                if(arr[i].contains("010")){
                    result_arr.add(1, arr[i])
                }

            }
            bundle.putStringArrayList("result", result_arr)
            ContactFragment().arguments = bundle

            val plusIntent = Intent(Intent.ACTION_INSERT)
            plusIntent.data = android.net.Uri.parse("content://contacts/people")
            plusIntent.type = ContactsContract.Contacts.CONTENT_TYPE
            plusIntent.putExtra(ContactsContract.Intents.Insert.NAME, result_arr[0])
            plusIntent.putExtra(ContactsContract.Intents.Insert.PHONE, result_arr[1])
            startActivity(plusIntent)
            childFragmentManager.beginTransaction().replace(R.id.main_frame, ContactFragment()).commit()
        }
    }



}