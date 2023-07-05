package cs.kaist.first

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream

object DetectText {
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

                    CameraFragment().next_fragment(result_arr)
                    return result_arr
                }
//


            } else if (arr.contains("010")) {
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
                if(result_arr.all { it.isEmpty() }){
                    CameraFragment().next_fragment(result_arr)
                    return result_arr
                }
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
