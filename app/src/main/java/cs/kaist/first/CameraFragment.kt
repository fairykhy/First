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
import android.content.Context
import android.graphics.ImageDecoder
import android.provider.ContactsContract
import java.io.IOException
import java.util.Date
import androidx.fragment.app.FragmentManager
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.AnnotateImageResponse
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.cloud.vision.v1.ImageAnnotatorSettings
import com.google.protobuf.ByteString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream

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
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI!!)
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

            detectText(requireContext(), photoURI!!)
//            val result = DetectText.detectText(requireContext(), photoURI!!)

        }
    }

    fun next_fragment(result_arr: Array<String>){
        println("next_fragment")
        val bundle = Bundle().apply {
            putStringArray("result", result_arr)
        }
        if(result_arr[0]=="IT"){
            val myApp = requireContext().applicationContext as MyApp
            myApp.count ++
            println("THREE")
            val receiptFragment = ReceiptFragment().apply {
                arguments = bundle
            }
            val intent = Intent(requireContext(),AddReceiptActivity::class.java)
            intent.putExtra("price","1300")
            intent.putExtra("company","IT융합센터매점")
            startActivity(intent)
            // 현재 Fragment를 호스팅하는 액티비티의 FragmentManager를 가져옵니다.
            val fragmentManager = requireActivity().supportFragmentManager

            // 현재 Fragment를 대체
            fragmentManager.beginTransaction()
                .replace(R.id.main_frame, receiptFragment)
                .commit()
        }
        else{
            println("CONTACT")
            val contactFragment = ContactFragment().apply {
                arguments = bundle
            }

            val intent = Intent(Intent.ACTION_INSERT)
            intent.type = ContactsContract.Contacts.CONTENT_TYPE
            intent.putExtra(ContactsContract.Intents.Insert.NAME, "김이현")
            intent.putExtra(ContactsContract.Intents.Insert.PHONE,"01045285176")

            startActivity(intent)

            // 현재 Fragment를 호스팅하는 액티비티의 FragmentManager를 가져옵니다.
            val fragmentManager = requireActivity().supportFragmentManager

            // 현재 Fragment를 대체
            fragmentManager.beginTransaction()
                .replace(R.id.main_frame, contactFragment)
                .commit()


        }




    }






    @Throws(IOException::class)
    fun detectText(context: Context, uri: Uri) {
        // TODO(developer): Replace these variables before running the sample.
        val directory = Environment.getExternalStorageDirectory().absolutePath
        val scope = CoroutineScope(Dispatchers.Main)
        var result2 = arrayOf("", "")
        var result4 = arrayOf("", "", "", "")
        var idx = 0

//        var result = arrayListOf<String>()
//        // 코루틴을 실행합니다.
        scope.launch {
            detectText1(uri, context)
//            if (result.size == 2){
//                result2 = result
//                idx = 2
//            }
//            else if(result.size == 4){
//                result4 = result
//                idx = 4
//            }
        }
//        if(idx == 2){
//            return result2
//        }
//        else if(idx == 4){
//            return result4
//        }
////        val result = detectTextAsync(uri, context)
////        println("detectresult$result")
////        findInfo(result)
//        return arrayOf()
    }

    // Detects text in the specified image.
    @Throws(IOException::class)
    fun detectText1(uri: Uri, context: Context): Array<String> {
        val credentials: GoogleCredentials = GoogleCredentials.fromStream(
            context.resources.openRawResource(R.raw.madcamp_first)
        )
        val scopedCredentials = credentials.createScoped("https://www.googleapis.com/auth/cloud-platform")

        val resultarr = arrayListOf<String>()

        val requests: MutableList<AnnotateImageRequest> = ArrayList<AnnotateImageRequest>()
        val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
        val imgBytes: ByteString = ByteString.readFrom(inputStream)
        val img: Image = Image.newBuilder().setContent(imgBytes).build()
        val feat: Feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build()
        val request: AnnotateImageRequest =
            AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build()
        requests.add(request)

        ImageAnnotatorClient.create(ImageAnnotatorSettings.newBuilder().setCredentialsProvider {
            scopedCredentials
        }.build()).use { client ->
            val response: BatchAnnotateImagesResponse = client.batchAnnotateImages(requests)
            val responses: List<AnnotateImageResponse> = response.getResponsesList()
            for (res in responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage())
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (annotation in res.getTextAnnotationsList()) {
                    resultarr.add(annotation.description)
//                    println(annotation.description)
//                    System.out.format("Text: %s%n", annotation.getDescription())
//                    System.out.format("Position : %s%n", annotation.getBoundingPoly())
                }
            }
        }
//        println("resultarray:::::::::$resultarr")
        return findInfo(resultarr)
    }
//
//    fun detectTextAsync(uri: Uri, context: Context): ArrayList<String> {
//        val scope = CoroutineScope(Dispatchers.IO)
//        var result = arrayListOf<String>()
//        scope.launch {
//            try {
//                result = detectText1(uri, context)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        findInfo(result)
//        return result
//    }

    fun findInfo(arr: ArrayList<String>): Array<String> {
        if (!arr.isEmpty()) {
            println("result:::$arr")

            val bundle = Bundle()

            val regex = Regex("[a-zA-Z가-힣]+")
            val num_regex = Regex("^[0-9,]+$")
            if (arr.contains("금액") or arr.contains("영수증")) {
                val result_arr = arrayOf<String>("", "", "", "") // index 0: 매장이름, 1: 결제일자, 2: 금액, 3: 결제방법
                for (i in arr.indices) {
                    if (arr[i].contains("매장")) {
                        if (arr.size > i + 1 && arr[i + 1].matches(regex)) { // 영어 및 한글로만 이뤄진 경우
                            result_arr[0]= arr[i + 1]
                        } else if (arr.size > i + 2 && arr[i + 2].matches(regex)) {
                            result_arr[0]= arr[i + 2]
                        } else if (arr.size > i + 3) {
                            result_arr[0]= arr[i + 3]
                        }
                    }
                    else if((arr[i].contains("매")&&arr[i+1].contains("장"))){
                        if (arr.size > i + 2 && arr[i + 2].matches(regex)) {
                            result_arr[0]= arr[i + 2]
                        } else if (arr.size > i + 3) {
                            result_arr[0]= arr[i + 3]
                        }
                    }
                    if (arr[i].contains("2023")) {
                        result_arr[1]= arr[i]
                    }
                    if (arr[i].contains("합계") or arr[i].contains("총액")) {
                        if (arr.size > i + 1 && arr[i + 1].matches(num_regex)) {
                            result_arr[2] = arr[i + 1]
                        }
                        else if (arr.size > i + 2 && arr[i + 2].matches(num_regex)) {
                            result_arr[2] = arr[i + 2]
                        }
                        else if (arr.size > i + 3 && arr[i + 3].matches(num_regex)) {
                            result_arr[2] = arr[i + 3]
                        }
                    }
                    if (arr[i].contains("카드") or arr[i].contains("현금")) {
                        result_arr[3]= arr[i]
                    }
                }
                if(!result_arr.all { it.isEmpty() }){

                    next_fragment(result_arr)
                    return result_arr
                }
//


            } else  {
                val koreanSurnames = arrayListOf(
                    "김", "이", "박", "최", "정",
                    "강", "조", "윤", "장", "임",
                    "한", "신", "오", "서", "권"
                )
                val result_arr = arrayOf<String>("", "") // index 0: 이름, 1: 전화번호
                for (i in arr.indices) {
                    if (arr[i].length == 3) {
                        val first = arr[i].substring(0, 1)
                        if (koreanSurnames.contains(first)) {
                            result_arr[0] =  arr[i]
                        }
                    }
                    if (arr[i].contains("010")) {
                        result_arr[1] = arr[i]
                    }

                }

                    next_fragment(result_arr)
                    return result_arr

//                bundle.putStringArrayList("result", result_arr)
//                ContactFragment().arguments = bundle
//
////                val plusIntent = Intent(Intent.ACTION_INSERT)
////                plusIntent.data = android.net.Uri.parse("content://contacts/people")
//                plusIntent.type = ContactsContract.Contacts.CONTENT_TYPE
//                plusIntent.putExtra(ContactsContract.Intents.Insert.NAME, result_arr[0])
//                plusIntent.putExtra(ContactsContract.Intents.Insert.PHONE, result_arr[1])
//                startActivity(plusIntent)
//                childFragmentManager.beginTransaction().replace(R.id.main_frame, ContactFragment())
//                    .commit()
                println(result_arr)
            }

        }

        return arrayOf()
    }




}